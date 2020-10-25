package com.socialcrap.api.common.service;

import java.util.List;

import com.socialcrap.api.model.entity.CommentEntity;
import com.socialcrap.api.model.request.dto.CommentLikeRequest;
import com.socialcrap.api.model.request.dto.CommentRequest;
import com.socialcrap.api.model.response.dto.CommentResponse;

public interface CommentService extends AbstractService<CommentResponse, CommentRequest> {
	public Boolean like(CommentLikeRequest request);

	public Boolean disLike(CommentLikeRequest request);

	public CommentResponse getResponse(CommentEntity request);

	public List<CommentResponse> getResponseList(List<CommentEntity> requestList);
}
