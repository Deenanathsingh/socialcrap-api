package com.socialcrap.api.common.converter;

import com.socialcrap.api.model.entity.CommentLike;
import com.socialcrap.api.model.request.dto.CommentLikeRequest;

public class CommentLikeConverter extends BaseConverter {
	
	public static CommentLike getEntityFromRequest(CommentLikeRequest request) {
		CommentLike response = new CommentLike();
		response.setUserId(request.getUserId());
		response.setCommentId(request.getCommentId());
		response.setActive(true);
		return response;
	}
}
