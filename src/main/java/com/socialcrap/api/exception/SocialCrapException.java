package com.socialcrap.api.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class SocialCrapException extends Exception {

	private static final long serialVersionUID = 1L;
	private String code;
	private HttpStatus status;

	SocialCrapException() {
		super();
		status = HttpStatus.INTERNAL_SERVER_ERROR;
	}

	public SocialCrapException(String message, String code, HttpStatus status) {
		super(message);
		this.code = code;
		this.status = status;
	}

}
