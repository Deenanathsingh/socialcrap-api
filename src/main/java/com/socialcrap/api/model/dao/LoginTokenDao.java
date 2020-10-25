package com.socialcrap.api.model.dao;

import com.socialcrap.api.model.entity.LoginToken;

public interface LoginTokenDao extends AbstractDao<LoginToken> {

	public LoginToken getByUserId(Long userId);
	public LoginToken getByToken(String token);
}
