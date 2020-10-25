package com.socialcrap.api.common.constants;

public interface SecurityConstants {
	public String TOKEN_PREFIX = "Bearer ";
	public String TOKEN_SECRET = "secret";
	public String HEADER_NAME = "Authorization";
	public Long EXPIRATION_TIME = 864_000_000L;
}
