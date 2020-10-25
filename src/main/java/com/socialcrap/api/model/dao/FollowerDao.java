package com.socialcrap.api.model.dao;

import java.util.List;

import com.socialcrap.api.model.entity.Follower;

public interface FollowerDao extends AbstractDao<Follower> {

	public List<Follower> getByUserId(Long userId);

	public Follower getByUserIdAndFollowerId(Long userId, Long followerId);

}
