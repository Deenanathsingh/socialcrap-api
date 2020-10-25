package com.socialcrap.api.model.response.dto;

import com.socialcrap.api.model.pojo.BasePojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginResponse extends BasePojo{
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String mobileNumber;
	private String profilePic;
	private String coverPhoto;
	private String token;
}
