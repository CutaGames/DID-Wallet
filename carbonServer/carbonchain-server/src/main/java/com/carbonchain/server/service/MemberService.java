package com.carbonchain.server.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.carbonchain.server.dto.AuthorizationKeyPair;
import com.carbonchain.server.dto.MemberInfoDto;
import com.carbonchain.server.exception.ApplicationException;
import com.carbonchain.server.mapper.MemberInfoMapper;
import com.carbonchain.server.model.MemberInfo;
import com.carbonchain.server.model.MemberInfoExample;
import com.carbonchain.server.util.IdGenerator;
import com.carbonchain.server.util.ParameterUtili;
import com.carbonchain.server.util.SecurityUtil;
import com.carbonchain.server.util.Web3jUtil;
import com.google.common.collect.Maps;

@Service
public class MemberService extends ServiceBase {
	@Autowired
	private MemberInfoMapper memberMapper;
	private static Map<String,MemberInfo> memberCache = Maps.newHashMap();
	private final String DECODE_SECRET_KEY = "tanchengshuju2020";
	
	/**
	 * 新增成员
	 * @param member
	 * @return
	 * @throws ApplicationException
	 */
	public synchronized MemberInfoDto addMember(MemberInfo member) throws ApplicationException{
		ParameterUtili.notNullAndEmpty(new Object[]{member,member.getPassword()});
		
		String cipherPassword = DigestUtils.md5Hex(member.getPassword());
		int memberCount = memberMapper.countByExample(new MemberInfoExample());
		if(memberCount <= 0){
			member.setRole(MEMBER_ROLE_ADMIN); 		//第一个成员默认管理员
			if(StringUtils.isBlank(member.getSecurityQuestions())){
				throw new ApplicationException(SECURITY_QUESTIONS_NOT_EMPTY,"密保不能为空");
			}
			//管理员密码MD5加密存储
			member.setPassword(cipherPassword);
		}else{
			//验证管理员密码
			MemberInfoExample example = new MemberInfoExample();
			example.createCriteria().andRoleEqualTo(MEMBER_ROLE_ADMIN);
			MemberInfo admin = memberMapper.selectOneByExample(example);
			if(admin == null){
				throw new ApplicationException(API_PARAM_ERROR,"管理员账户出现异常");
			}
			if(!admin.getPassword().equals(cipherPassword)){
				throw new ApplicationException(PASSWORD_ERROR,"管理员密码错误");
			}
			member.setPassword(null);
			member.setRole(MEMBER_ROLE_GENERAL);
		}
		member.setState(MEMBER_STATE_ACTIVE);
		
		//成员ID
		String memberId = IdGenerator.nextId("m_");
		member.setMemberId(memberId);
		//生成授权秘钥
		AuthorizationKeyPair keyPair = Web3jUtil.createEcKeyPair();
		String authPuk = keyPair.getPublicKey();
		member.setAuthSignPuk(authPuk);
		
		//通讯数据秘钥>>3Des加密保存数据库
		String decodeSecretKey = DigestUtils.md5Hex(UUID.randomUUID().toString());
		try {
			member.setDecodeSecretKey(SecurityUtil.encode3Des(DECODE_SECRET_KEY,decodeSecretKey));
		} catch (Exception e) {
			throw new ApplicationException(API_PARAM_ERROR,"");
		}
		if(StringUtils.isBlank(member.getNickName())){
			member.setNickName("user_"+ memberId.substring(memberId.length()-4,memberId.length()));
		}
		
		//芯片公钥
		member.setPublicKey(keyPair.getPublicKey());
		member.setCreateTime(new Date());
		
		memberMapper.insert(member);
		
		MemberInfoDto result = new MemberInfoDto();
		copyProperties(result, member);
		result.setPrivateKey(keyPair.getPrivateKey());
		//授权公钥MD5
		String authSignPukMD5 = DigestUtils.md5Hex(member.getAuthSignPuk());
		result.setAuthSignPukMD5(authSignPukMD5);
		result.setDecodeSecretKey(decodeSecretKey);
		
		memberCache.put(authSignPukMD5, member);//缓存
		return result;
	}
	
	/**
	 * 完成设备绑定
	 * @param memberId
	 * @param deviceInfo
	 */
	public void bindFinish(String memberId,String deviceInfo) {
		MemberInfo memberInfo = memberMapper.selectByPrimaryKey(memberId);
		if(memberInfo != null) {
			memberInfo.setBindedDevice(deviceInfo);
			memberMapper.updateByPrimaryKey(memberInfo);
			this.updateMemberCache(memberId);
		}
	}
	
	/**
	 * 获取所有用户
	 * @return
	 */
	public List<MemberInfo> getAll(){
		return memberMapper.selectByExample(new MemberInfoExample());
	}
	
	/**
	 * 是否为管理员
	 * @param memberId
	 * @return
	 */
	public boolean isAdmin(String memberId){
		try{
			MemberInfo memberInfo = memberMapper.selectByPrimaryKey(memberId);
			if(memberInfo != null){
				return memberInfo.getRole().equals(MEMBER_ROLE_ADMIN);
			}
		}catch(Exception e){}
		return false;
	}
	
	/**
	 * 从缓存中获取成员信息
	 * @param pukMD5
	 * @return
	 */
	public MemberInfo getMemberforCache(String authSignPukMD5){
		if(StringUtils.isBlank(authSignPukMD5)){
			return null;
		}
		MemberInfo memberInfo = memberCache.get(authSignPukMD5);
		if(memberInfo == null){
			MemberInfoExample example = new MemberInfoExample();
			List<MemberInfo> all = memberMapper.selectByExample(example);
			if(CollectionUtils.isNotEmpty(all)){
				for(MemberInfo member : all) {
					String signPukMD5 = DigestUtils.md5Hex(member.getAuthSignPuk());
					memberCache.put(signPukMD5, member);
				}
			}
			memberInfo = memberCache.get(authSignPukMD5);
		}
		return memberInfo;
	}
	
	private void updateMemberCache(String memberId) {
		if(StringUtils.isBlank(memberId)) {
			return;
		}
		MemberInfo memberInfo = memberMapper.selectByPrimaryKey(memberId);
		if(memberInfo != null){
			memberCache.put(DigestUtils.md5Hex(memberInfo.getAuthSignPuk()), memberInfo);
		}
	}
	
	public String decodeSecretKey(String ciphertext) throws Exception{
		return SecurityUtil.decode3Des(DECODE_SECRET_KEY, ciphertext);
	}
}