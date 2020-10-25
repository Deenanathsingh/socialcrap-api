package com.socialcrap.api.model.pojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostLikePojo {
	private Long postId;
	private Long userId;
}
