package com.socialcrap.api.model.request.dto;

import com.socialcrap.api.model.pojo.CommentPojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentRequest extends CommentPojo{
	private Long userId;
	private Long postId;
}
