package com.socialcrap.api.common.converter;

import com.socialcrap.api.model.entity.PostLike;
import com.socialcrap.api.model.request.dto.PostLikeRequest;

public class PostLikeConverter extends BaseConverter {

	public static PostLike getEntityFromRequest(PostLikeRequest request) {
		PostLike response = new PostLike();
		response.setUserId(request.getUserId());
		response.setPostId(request.getPostId());
		response.setActive(true);
		return response;
	}

}
