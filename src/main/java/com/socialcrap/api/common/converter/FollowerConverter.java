package com.socialcrap.api.common.converter;

import com.socialcrap.api.model.entity.Follower;

public class FollowerConverter {

	public static Follower getEntityFromFriendShipRequest(Long userId, Long followerId) {
		if (userId == null || followerId == null) {
			return null;
		}
		Follower response = new Follower();
		response.setUserId(userId);
		response.setFollowerId(followerId);
		response.setActive(true);
		return response;
	}

}
