package com.socialcrap.api.model.request.dto;

import com.socialcrap.api.model.pojo.PostPojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostRequest extends PostPojo {
	private Long userId;
}
