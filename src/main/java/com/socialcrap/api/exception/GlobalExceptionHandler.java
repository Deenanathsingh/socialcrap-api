package com.socialcrap.api.exception;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.socialcrap.api.model.response.dto.BaseApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(SocialCrapException.class)
	public <T> BaseApiResponse<T> handleException(SocialCrapException ex, HttpServletResponse response) {
		response.setStatus(ex.getStatus().value());
		response.setHeader("status", ex.getStatus().value() + " " + ex.getStatus().getReasonPhrase());
		return new BaseApiResponse<>(true, ex.getCode(), null, ex.getMessage());
	}

}
