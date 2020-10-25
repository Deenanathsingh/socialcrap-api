package com.socialcrap.api.model.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.socialcrap.api.common.enums.LikeOrDisLike;
import com.socialcrap.api.model.dao.PostLikeDao;
import com.socialcrap.api.model.entity.PostLike;

@Repository
public class PostLikeDaoImpl extends AbstractDaoImpl<PostLike> implements PostLikeDao {

	public PostLikeDaoImpl() {
		super(PostLike.class);
	}

	@Override
	public List<PostLike> getAllByCommentId(Long postId) {
		String hql = FROM_ENTITY + " where postId = '" + postId + "'";
		return getAllT(hql);
	}

	@Override
	public List<PostLike> getAllLiked(Long postId) {
		String hql = FROM_ENTITY + " where postId = '" + postId + "' and likeOrDisLike = '" + LikeOrDisLike.LIKE + "'";
		return getAllT(hql);
	}

	@Override
	public List<PostLike> getAllDisLiked(Long postId) {
		String hql = FROM_ENTITY + " where postId = '" + postId + "' and likeOrDisLike = '" + LikeOrDisLike.DISLIKE
				+ "'";
		return getAllT(hql);
	}

	@Override
	public Boolean isExistByUserIdAndPostId(Long userId, Long postId) {
		String hql = FROM_ENTITY + " where userId = '" + userId + "' and postId = '" + postId + "'";
		return (getT(hql) == null) ? false : true;
	}

	@Override
	public PostLike getByUserIdAndPostId(Long userId, Long postId) {
		String hql = FROM_ENTITY + " where userId = '" + userId + "' and postId = '" + postId + "'";
		return getT(hql);
	}

}
