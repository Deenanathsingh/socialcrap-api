package com.socialcrap.api.common.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.socialcrap.api.common.enums.Roles;
import com.socialcrap.api.common.enums.SocialCrapResponseCode;
import com.socialcrap.api.common.service.LoginTokenService;
import com.socialcrap.api.exception.SocialCrapException;
import com.socialcrap.api.model.dao.LoginTokenDao;
import com.socialcrap.api.model.entity.LoginToken;
import com.socialcrap.api.model.entity.User;

@Service
public class LoginTokenServiceImpl implements LoginTokenService {

	@Autowired
	private LoginTokenDao loginTokenDao;

	@Override
	public LoginToken getByUserId(Long userId) {
		return loginTokenDao.getByUserId(userId);
	}

	@Override
	public LoginToken getByToken(String token) {
		return loginTokenDao.getByToken(token);
	}

	@Override
	public String add(User request) throws SocialCrapException {
		if (StringUtils.isEmpty(request)) {
			throw new SocialCrapException(SocialCrapResponseCode.USER_RE_LOGIN.getMessage(),
					SocialCrapResponseCode.USER_RE_LOGIN.getCode(), HttpStatus.REQUEST_TIMEOUT);
		}
		if (request.getId() != null) {
			LoginToken entity = null;
			entity = getByUserId(request.getId());
			if (entity != null) {
				entity.setToken(tokenGenerator(request.getId()));
				entity.setExpiry(System.currentTimeMillis() + 180000);
				entity.setUser(request);
				loginTokenDao.updateT(entity);
				return entity.getToken();
			} else {
				entity = new LoginToken();
				entity.setToken(tokenGenerator(request.getId()));
				entity.setExpiry(System.currentTimeMillis() + 180000);
				entity.setUser(request);
				loginTokenDao.saveT(entity);
				return entity.getToken();
			}
		}
		return null;
	}

	@Override
	public Boolean update(User request, Long id) throws SocialCrapException {
		return null;
	}

	@Override
	public Boolean delete(Long id) throws SocialCrapException {
		return null;
	}

	@Override
	public Boolean validateId(Long token) throws SocialCrapException {
		return null;
	}

	private String tokenGenerator(Long userId) {
		if (userId != null) {
			StringBuilder token = new StringBuilder("SC");
			token.append(System.currentTimeMillis());
			token.append(UUID.randomUUID().toString().replaceAll("-", ""));
			return token.toString();
		}
		return null;
	}

	@Override
	public Long validateAdminToken(String token) throws SocialCrapException {
		if (token == null) {
			throw new SocialCrapException(SocialCrapResponseCode.TOKEN_UNAVAILABLE.getMessage(),
					SocialCrapResponseCode.TOKEN_UNAVAILABLE.getCode(), HttpStatus.BAD_REQUEST);
		}
		LoginToken tokenEntity = getByToken(token);
		if (tokenEntity == null) {
			throw new SocialCrapException(SocialCrapResponseCode.UN_AUTHORIZED.getMessage(),
					SocialCrapResponseCode.UN_AUTHORIZED.getCode(), HttpStatus.BAD_REQUEST);
		}
		if (tokenEntity.getUser() == null) {
			throw new SocialCrapException(SocialCrapResponseCode.UN_AUTHORIZED.getMessage(),
					SocialCrapResponseCode.UN_AUTHORIZED.getCode(), HttpStatus.BAD_REQUEST);
		}
		User user = tokenEntity.getUser();
		if (!user.getRole().getRole().equals(Roles.ADMIN.getRole())) {
			throw new SocialCrapException(SocialCrapResponseCode.UN_AUTHORIZED.getMessage(),
					SocialCrapResponseCode.UN_AUTHORIZED.getCode(), HttpStatus.BAD_REQUEST);
		}
		return user.getId();
	}

	@Override
	public Long validateUserToken(String token) throws SocialCrapException {
		if (token == null) {
			throw new SocialCrapException(SocialCrapResponseCode.TOKEN_UNAVAILABLE.getMessage(),
					SocialCrapResponseCode.TOKEN_UNAVAILABLE.getCode(), HttpStatus.BAD_REQUEST);
		}
		LoginToken tokenEntity = getByToken(token);
		if (tokenEntity == null) {
			throw new SocialCrapException(SocialCrapResponseCode.UN_AUTHORIZED.getMessage(),
					SocialCrapResponseCode.UN_AUTHORIZED.getCode(), HttpStatus.BAD_REQUEST);
		}
		if (tokenEntity.getUser() == null) {
			throw new SocialCrapException(SocialCrapResponseCode.UN_AUTHORIZED.getMessage(),
					SocialCrapResponseCode.UN_AUTHORIZED.getCode(), HttpStatus.BAD_REQUEST);
		}
		User user = tokenEntity.getUser();
		if (!user.getRole().getRole().equals(Roles.USER.getRole())) {
			throw new SocialCrapException(SocialCrapResponseCode.UN_AUTHORIZED.getMessage(),
					SocialCrapResponseCode.UN_AUTHORIZED.getCode(), HttpStatus.BAD_REQUEST);
		}
		return user.getId();
	}

}
