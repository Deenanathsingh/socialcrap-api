package com.socialcrap.api.common.service;

import java.util.List;

import com.socialcrap.api.exception.SocialCrapException;
import com.socialcrap.api.model.entity.Post;
import com.socialcrap.api.model.request.dto.PostLikeRequest;
import com.socialcrap.api.model.request.dto.PostRequest;
import com.socialcrap.api.model.response.dto.PaginationResponse;
import com.socialcrap.api.model.response.dto.PostResponse;

public interface PostService extends AbstractService<PostResponse, PostRequest> {

	public List<PostResponse> getResponseList(List<Post> requestList);

	public PostResponse getResponse(Post request);

	public Boolean like(PostLikeRequest request) throws SocialCrapException;

	public Boolean disLike(PostLikeRequest request);

	public PaginationResponse<List<PostResponse>> getAll(String token, Integer limit, Integer offset) throws SocialCrapException;

	public Long getTotalCount();

	public List<PostResponse> getCustomPostForUser(Long userId);
	
	public Boolean activateAndDeactivate(Long postId);

}
