package com.carbonchain.server.mapper;

import com.carbonchain.server.model.CarbonDict;

public interface CarbonDictMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CarbonDict record);

    int insertSelective(CarbonDict record);

    CarbonDict selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarbonDict record);

    int updateByPrimaryKey(CarbonDict record);
}