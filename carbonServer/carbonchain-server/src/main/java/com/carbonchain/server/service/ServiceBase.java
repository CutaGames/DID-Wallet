package com.carbonchain.server.service;

import org.apache.commons.beanutils.BeanUtils;
import com.carbonchain.server.constant.Constants;
import com.carbonchain.server.constant.ErrorCode;
import com.carbonchain.server.util.LogUtil;

public class ServiceBase implements ErrorCode,Constants{
	
	protected void copyProperties(Object dest, Object orig){
		try {
			BeanUtils.copyProperties(dest, orig);
		} catch (Exception e) {
			LogUtil.businessLog.error("",e);
		}
	}
}