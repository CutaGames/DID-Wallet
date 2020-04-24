package com.carbonchain.server.filter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.commons.beanutils.BeanUtils;
import com.carbonchain.server.constant.Constants;
import com.carbonchain.server.dto.RequestHeadParameter;
import com.carbonchain.server.model.MemberInfo;
import com.carbonchain.server.util.JsonUtil;
import com.carbonchain.server.util.LogUtil;
import com.carbonchain.server.util.ParameterUtili;
import com.carbonchain.server.util.SecurityUtil;

/**
 * 验权过滤器
 * @author panzi
 *
 */
@PreMatching
public class AuthorizationFilter extends FilterBase implements ContainerRequestFilter {
	
	@Override
	public void filter(ContainerRequestContext requestContext)
			throws IOException {
		try{
			MultivaluedMap<String, String> headers = requestContext.getHeaders();
			RequestHeadParameter parameter = new RequestHeadParameter();
			try {
				BeanUtils.populate(parameter, headers);
			} catch (Exception e) {
				LogUtil.businessLog.error(JsonUtil.obj2Json(headers), e);
			}
			ParameterUtili.notNullAndEmpty(new Object[]{parameter,parameter.getAuth(),parameter.getPlatform(),parameter.getPuk()});
			
			String signPuk = parameter.getPuk();
			MemberInfo memberInfo = memberService.getMemberforCache(signPuk);
			if(memberInfo == null){
				unAuthorized(requestContext,null);
				return;
			}
			String decodeSecretKey = memberService.decodeSecretKey(memberInfo.getDecodeSecretKey());
			//解密报文体
			String ciphertext = streamToString(requestContext.getEntityStream());
			String requestBody = SecurityUtil.decode3Des(decodeSecretKey, ciphertext);
			
			//Stream重用，明文
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(requestBody.getBytes(Constants.CHARSET_NAME));
	        requestContext.setEntityStream(byteArrayInputStream);
			
			//验签
			/*String signature = parameter.getAuth();
			boolean verifyResult = Web3jUtil.verifyMsg(signature, requestBody.getBytes(Constants.CHARSET_NAME), signPuk);
			if(!verifyResult){
				unAuthorized(requestContext,"signature verification failed");
				return;
			}*/
	        requestContext.setProperty("memberId", memberInfo.getMemberId());
	        requestContext.setProperty("signPuk", memberInfo.getAuthSignPuk());
		}catch(Exception e){
			unAuthorized(requestContext,null);
			return;
		}
	}
}