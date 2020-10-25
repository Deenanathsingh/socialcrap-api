package com.socialcrap.api.common.converter;

import com.socialcrap.api.model.entity.BaseEntity;
import com.socialcrap.api.model.pojo.BasePojo;
import com.socialcrap.api.util.DateTimeUtil;

public class BaseConverter {

	public static void setResponse(BaseEntity request, BasePojo response) {
		response.setCreated(DateTimeUtil.formatDate(request.getCreated(), DateTimeUtil.TIMESTAMP_PATTERN));
		response.setUpdated(DateTimeUtil.formatDate(request.getUpdated(), DateTimeUtil.TIMESTAMP_PATTERN));
		response.setActive(request.isActive());
	}

	public static void setRequest(BaseEntity response, BasePojo request) {
		response.setActive(true);
	}

}
