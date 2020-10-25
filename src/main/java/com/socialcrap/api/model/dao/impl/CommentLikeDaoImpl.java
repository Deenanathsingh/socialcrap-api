package com.socialcrap.api.model.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.socialcrap.api.common.enums.LikeOrDisLike;
import com.socialcrap.api.model.dao.CommentLikeDao;
import com.socialcrap.api.model.entity.CommentLike;

@Repository
public class CommentLikeDaoImpl extends AbstractDaoImpl<CommentLike> implements CommentLikeDao {

	public CommentLikeDaoImpl() {
		super(CommentLike.class);
	}

	@Override
	public List<CommentLike> getAllByCommentId(Long commentId) {
		String hql = FROM_ENTITY + " where commentId = '" + commentId + "'";
		return getAllT(hql);
	}

	@Override
	public List<CommentLike> getAllLiked(Long commentId) {
		String hql = FROM_ENTITY + " where commentId = '" + commentId + "' and likeOrDisLike = '" + LikeOrDisLike.LIKE
				+ "'";
		return getAllT(hql);
	}

	@Override
	public List<CommentLike> getAllDisLiked(Long commentId) {
		String hql = FROM_ENTITY + " where commentId = '" + commentId + "' and likeOrDisLike = '"
				+ LikeOrDisLike.DISLIKE + "'";
		return getAllT(hql);
	}

	@Override
	public Boolean isExistByUserIdAndCommentId(Long userId, Long commentId) {
		String hql = FROM_ENTITY + " where userId = '" + userId + "' and commentId = '" + commentId + "'";
		return (getT(hql) == null) ? false : true;
	}

	@Override
	public CommentLike getByUserIdAndCommentId(Long userId, Long commentId) {
		String hql = FROM_ENTITY + " where userId = '" + userId + "' and commentId = '" + commentId + "'";
		return getT(hql);
	}

}
