package com.socialcrap.api.model.dao.impl;

import org.springframework.stereotype.Repository;

import com.socialcrap.api.model.dao.AuthTokenDao;
import com.socialcrap.api.model.entity.AuthTokenEntity;

@Repository
public class AuthTokenDaoImpl extends AbstractDaoImpl<AuthTokenEntity> implements AuthTokenDao, AuthTokenEntity.Table {

	public AuthTokenDaoImpl() {
		super(AuthTokenEntity.class);
	}

	@Override
	public AuthTokenEntity getByToken(String token) {
		return getT(FROM_ENTITY + " where " + TOKEN + "='" + token + "'");
	}

	@Override
	public AuthTokenEntity getByUserId(Long userId) {
		return getT(FROM_ENTITY + " where " + USER_ID + "=" + userId);
	}

}
