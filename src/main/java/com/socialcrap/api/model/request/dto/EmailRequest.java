package com.socialcrap.api.model.request.dto;

import com.socialcrap.api.model.pojo.EmailPojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmailRequest extends EmailPojo{
	private Long userId;
}
