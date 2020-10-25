package com.socialcrap.api.model.dao;

import java.util.List;

import com.socialcrap.api.model.entity.CommentLike;

public interface CommentLikeDao extends AbstractDao<CommentLike> {

	public List<CommentLike> getAllByCommentId(Long commentId);

	public List<CommentLike> getAllLiked(Long commentId);

	public List<CommentLike> getAllDisLiked(Long commentId);

	public Boolean isExistByUserIdAndCommentId(Long userId, Long commentId);

	public CommentLike getByUserIdAndCommentId(Long userId, Long commenttId);
}
