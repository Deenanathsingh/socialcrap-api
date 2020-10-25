package com.socialcrap.api.model.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthTokenPojo extends BasePojo {
	private Long id;
	private String token;
	private Long userId;
}
