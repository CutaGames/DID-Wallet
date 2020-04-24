package com.carbonchain.server.dto;

import com.carbonchain.server.util.JsonUtil;

public class DtoBase {

	@Override
	public String toString() {
		return JsonUtil.obj2Json(this);
	}
	
}
