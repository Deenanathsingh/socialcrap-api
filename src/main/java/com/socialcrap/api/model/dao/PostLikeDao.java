package com.socialcrap.api.model.dao;

import java.util.List;

import com.socialcrap.api.model.entity.PostLike;

public interface PostLikeDao extends AbstractDao<PostLike> {

	public List<PostLike> getAllByCommentId(Long postId);

	public List<PostLike> getAllLiked(Long postId);

	public List<PostLike> getAllDisLiked(Long postId);

	public Boolean isExistByUserIdAndPostId(Long userId, Long postId);

	public PostLike getByUserIdAndPostId(Long userId, Long postId);

}
