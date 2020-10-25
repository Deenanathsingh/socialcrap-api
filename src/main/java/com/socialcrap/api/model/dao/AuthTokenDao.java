package com.socialcrap.api.model.dao;

import com.socialcrap.api.model.entity.AuthTokenEntity;

public interface AuthTokenDao extends AbstractDao<AuthTokenEntity> {

	AuthTokenEntity getByToken(String token);

	AuthTokenEntity getByUserId(Long userId);

}
