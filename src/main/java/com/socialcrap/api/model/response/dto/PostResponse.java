package com.socialcrap.api.model.response.dto;

import java.util.ArrayList;
import java.util.List;

import com.socialcrap.api.model.pojo.PostPojo;
import com.socialcrap.api.model.wrap.pojo.UserWrap;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostResponse extends PostPojo{
	private Long id;
	private UserWrap user;
	private LikeAndDisLikeResponse likesAndDisLikes;
	private List<CommentResponse> comments = new ArrayList<>();
}
