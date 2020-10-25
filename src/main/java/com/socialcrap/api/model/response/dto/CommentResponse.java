package com.socialcrap.api.model.response.dto;

import com.socialcrap.api.model.pojo.CommentPojo;
import com.socialcrap.api.model.wrap.pojo.UserWrap;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentResponse extends CommentPojo{
	private Long id;
	private UserWrap user;
	private LikeAndDisLikeResponse likeAndDisLikes;
}
