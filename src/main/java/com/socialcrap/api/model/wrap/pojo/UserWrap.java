package com.socialcrap.api.model.wrap.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserWrap {
	@JsonInclude(Include.NON_NULL)
	private Long requestId;
	private Long userId;
	private String name;
	private String profilePic;
}
