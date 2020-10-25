package com.socialcrap.api.model.response.dto;

import com.socialcrap.api.model.pojo.EmailPojo;
import com.socialcrap.api.model.wrap.pojo.UserWrap;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmailResponse extends EmailPojo{
	private UserWrap user;
}
