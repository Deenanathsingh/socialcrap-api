package com.socialcrap.api.model.request.dto;

import com.socialcrap.api.model.pojo.UserPojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequest extends UserPojo {
	private AboutRequest about;
	private String password;
}
