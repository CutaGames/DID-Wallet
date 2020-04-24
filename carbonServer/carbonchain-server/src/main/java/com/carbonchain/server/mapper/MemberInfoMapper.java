package com.carbonchain.server.mapper;

import com.carbonchain.server.model.MemberInfo;
import com.carbonchain.server.model.MemberInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MemberInfoMapper {
    int countByExample(MemberInfoExample example);

    int deleteByExample(MemberInfoExample example);

    int deleteByPrimaryKey(String memberId);

    int insert(MemberInfo record);

    int insertSelective(MemberInfo record);

    MemberInfo selectOneByExample(MemberInfoExample example);

    List<MemberInfo> selectByExample(MemberInfoExample example);

    MemberInfo selectByPrimaryKey(String memberId);

    int updateByExampleSelective(@Param("record") MemberInfo record, @Param("example") MemberInfoExample example);

    int updateByExample(@Param("record") MemberInfo record, @Param("example") MemberInfoExample example);

    int updateByPrimaryKeySelective(MemberInfo record);

    int updateByPrimaryKey(MemberInfo record);

    int insertBatch(List<MemberInfo> records);
}