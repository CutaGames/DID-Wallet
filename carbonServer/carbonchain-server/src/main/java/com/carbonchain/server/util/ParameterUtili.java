package com.carbonchain.server.util;

import java.math.BigDecimal;
import java.util.Collection;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import com.carbonchain.server.constant.ErrorCode;
import com.carbonchain.server.exception.ApplicationException;

public class ParameterUtili {
	private static final String EMPTY_ERRORMSG = "parameter cannot be empty";
	
	public static void notNullAndEmpty(Object obj, String errorMsg) throws ApplicationException {
		if (StringUtils.isBlank(errorMsg)) {
			errorMsg = EMPTY_ERRORMSG;
		}
		if (obj == null) {
			throw new ApplicationException(ErrorCode.PARAM_ERROR, errorMsg);
		}
		if (obj instanceof BigDecimal && ((BigDecimal) obj).compareTo(BigDecimal.ZERO) <= 0) {
			throw new ApplicationException(ErrorCode.PARAM_ERROR, errorMsg);
		}
		if (obj instanceof String && StringUtils.isEmpty(obj.toString())) {
			throw new ApplicationException(ErrorCode.PARAM_ERROR, errorMsg);
		}
		if (obj instanceof Collection && CollectionUtils.isEmpty((Collection<?>) obj)) {
			throw new ApplicationException(ErrorCode.PARAM_ERROR, errorMsg);
		}
	}
	
	public static void notNullAndEmpty(Object[] obj) throws ApplicationException {
		notNullAndEmpty(obj, "");
	}

	public static void notNullAndEmpty(Object obj) throws ApplicationException {
		notNullAndEmpty(obj, "");
	}
	
	private ParameterUtili(){}
}
