package com.socialcrap.api.model.response.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.socialcrap.api.common.enums.SocialCrapResponseCode;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(Include.NON_NULL)
public class PaginationResponse<T> extends BaseApiResponse<T> {

	private Long totalCount;
	private String previousPage;
	private String currentPage;
	private String nextPage;
	private String lastPage;
	
	public PaginationResponse(boolean error, String code, T data, String message) {
		super(error, code, data, message);
	}

	public PaginationResponse(boolean error, String code, String message) {
		this(error, code, null, message);
	}

	public PaginationResponse(T data) {
		this(false, null, data, SocialCrapResponseCode.SUCCESS.getMessage());
	}

}
