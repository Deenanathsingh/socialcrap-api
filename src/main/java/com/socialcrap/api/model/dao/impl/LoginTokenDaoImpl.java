package com.socialcrap.api.model.dao.impl;

import org.springframework.stereotype.Repository;

import com.socialcrap.api.common.constants.EntityDetails;
import com.socialcrap.api.model.dao.LoginTokenDao;
import com.socialcrap.api.model.entity.LoginToken;

@Repository
public class LoginTokenDaoImpl extends AbstractDaoImpl<LoginToken> implements LoginTokenDao {

	public LoginTokenDaoImpl() {
		super(LoginToken.class);
	}

	@Override
	public LoginToken getByUserId(Long userId) {
		String hql = String.format(FROM_ENTITY + " where user_id=%s", userId);
		return getT(hql);
	}

	@Override
	public LoginToken getByToken(String token) {
		String hql = String.format(FROM_ENTITY + " where %s='%s'", EntityDetails.LoginToken.TOKEN, token);
		return getT(hql);
	}

}
