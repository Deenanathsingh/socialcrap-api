package com.socialcrap.api.common.service;

import com.socialcrap.api.exception.SocialCrapException;
import com.socialcrap.api.model.entity.LoginToken;
import com.socialcrap.api.model.entity.User;

public interface LoginTokenService {

	public LoginToken getByUserId(Long userId);

	public String add(User request) throws SocialCrapException;

	public Boolean update(User request, Long id) throws SocialCrapException;

	public Boolean delete(Long id) throws SocialCrapException;

	public Boolean validateId(Long token) throws SocialCrapException;

	public LoginToken getByToken(String token);
	
	public Long validateAdminToken(String token) throws SocialCrapException;
	
	public Long validateUserToken(String token) throws SocialCrapException;
}
