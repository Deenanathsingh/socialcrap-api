package com.socialcrap.api.model.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.socialcrap.api.common.constants.EntityDetails;
import com.socialcrap.api.model.dao.FollowerDao;
import com.socialcrap.api.model.entity.BaseEntity;
import com.socialcrap.api.model.entity.Follower;

@Repository
public class FollowerDaoImpl extends AbstractDaoImpl<Follower> implements FollowerDao {

	public FollowerDaoImpl() {
		super(Follower.class);
	}

	@Override
	public List<Follower> getByUserId(Long userId) {
		String hql = String.format(FROM_ENTITY + " where %s=%s and %s=%s", EntityDetails.Follower.USER_ID, userId,
				BaseEntity.Table.IS_ACTIVE, true);
		return getAllT(hql);
	}

	@Override
	public Follower getByUserIdAndFollowerId(Long userId, Long followerId) {
		String hql = String.format(FROM_ENTITY + " where %s=%s and %s=%s", EntityDetails.Follower.USER_ID, userId,
				EntityDetails.Follower.FOLLOWER_ID, followerId);
		return getT(hql);
	}

}
