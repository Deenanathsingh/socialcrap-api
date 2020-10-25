package com.socialcrap.api.common.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.socialcrap.api.common.converter.AuthTokenConverter;
import com.socialcrap.api.common.enums.SocialCrapResponseCode;
import com.socialcrap.api.common.service.AuthTokenService;
import com.socialcrap.api.exception.SocialCrapException;
import com.socialcrap.api.model.dao.AuthTokenDao;
import com.socialcrap.api.model.entity.AuthTokenEntity;
import com.socialcrap.api.model.pojo.AuthTokenPojo;

@Service
public class AuthTokenServiceImpl implements AuthTokenService {

	@Autowired
	private AuthTokenDao authTokenDao;

	@Override
	public String add(Long appUserId, String loginType) {
		AuthTokenPojo tokenPojo = getByUserId(appUserId);
		if (tokenPojo != null) {
			authTokenDao.deleteT(tokenPojo.getId());
		}
		AuthTokenEntity entity = new AuthTokenEntity();
		entity.setToken(UUID.randomUUID().toString().replaceAll("-", ""));
		entity.setUserId(appUserId);
		entity.setLoginType(loginType);
		authTokenDao.saveT(entity);
		return entity.getToken();
	}

	@Override
	public AuthTokenPojo getByToken(String token) {
		if (token == null) {
			return null;
		}
		AuthTokenEntity authToken = authTokenDao.getByToken(token);
		return AuthTokenConverter.getResponseFromEntity(authToken);
	}

	@Override
	public AuthTokenPojo getByUserId(Long appUserId) {
		if (appUserId == null) {
			return null;
		}
		AuthTokenEntity authToken = authTokenDao.getByUserId(appUserId);
		return AuthTokenConverter.getResponseFromEntity(authToken);
	}

	@Override
	public AuthTokenPojo authenticate(String token) throws SocialCrapException {
		AuthTokenPojo tokenPojo = getByToken(token);
		if (tokenPojo == null) {
			throw new SocialCrapException(SocialCrapResponseCode.INVALID_LOGIN.getMessage(),
					SocialCrapResponseCode.INVALID_LOGIN.getCode(), HttpStatus.BAD_REQUEST);
		}
		return tokenPojo;
	}

}
