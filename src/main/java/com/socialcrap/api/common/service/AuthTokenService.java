package com.socialcrap.api.common.service;

import com.socialcrap.api.exception.SocialCrapException;
import com.socialcrap.api.model.pojo.AuthTokenPojo;

public interface AuthTokenService {

	String add(Long appUserId, String loginType);

	AuthTokenPojo getByToken(String token);

	AuthTokenPojo getByUserId(Long appUserId);

	AuthTokenPojo authenticate(String token) throws SocialCrapException;

}
