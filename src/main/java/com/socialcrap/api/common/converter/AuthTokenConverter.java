package com.socialcrap.api.common.converter;

import com.socialcrap.api.model.entity.AuthTokenEntity;
import com.socialcrap.api.model.pojo.AuthTokenPojo;

public class AuthTokenConverter {

	public static AuthTokenEntity getEntityFromRequest(AuthTokenPojo request) {
		if (request == null) {
			return null;
		}
		AuthTokenEntity response = new AuthTokenEntity();
		response.setToken(request.getToken());
		response.setUserId(request.getUserId());
		return response;
	}

	public static AuthTokenPojo getResponseFromEntity(AuthTokenEntity request) {
		if (request == null) {
			return null;
		}
		AuthTokenPojo response = new AuthTokenPojo();
		response.setId(request.getId());
		response.setToken(request.getToken());
		response.setUserId(request.getUserId());
		return response;
	}

}
