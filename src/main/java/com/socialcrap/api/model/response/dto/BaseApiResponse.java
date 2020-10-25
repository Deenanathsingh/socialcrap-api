package com.socialcrap.api.model.response.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.socialcrap.api.common.enums.SocialCrapResponseCode;

import lombok.Getter;
import lombok.Setter;

@JsonInclude(Include.NON_NULL)
@Setter
@Getter
public class BaseApiResponse<T> {

	protected boolean error;
	protected String code;
	protected T data;
	protected String message;

	public BaseApiResponse(boolean error, String code, T data, String message) {
		this.error = error;
		this.code = code;
		this.data = data;
		this.message = message;
	}

	public BaseApiResponse(T data) {
		this(false, null, data, SocialCrapResponseCode.SUCCESS.getMessage());
	}

	public BaseApiResponse(boolean error, SocialCrapResponseCode ex) {
		this(error, ex.getCode(), null, ex.getMessage());
	}

	public BaseApiResponse(boolean error, String code, T data) {
		this(error, code, data, SocialCrapResponseCode.SUCCESS.getMessage());
	}

	public BaseApiResponse(boolean error, String code, String message) {
		this(error, code, null, message);
	}
}
