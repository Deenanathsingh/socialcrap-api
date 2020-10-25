package com.socialcrap.api.model.dao;

import java.util.List;

import com.socialcrap.api.model.entity.Friend;

public interface FriendDao extends AbstractDao<Friend> {

	public List<Friend> getAllByUserId(Long userId, Boolean isActive);

	public List<Friend> getAllByFriendId(Long friendId, Boolean isActive);

	public Friend getFriendByUserAndFriend(Long userId, Long friendId);

	public Boolean validateFriendShip(Long userId, Long friendId);

	public List<Friend> getAllInActiveByUserId(Long userId);

	public List<Friend> getAllInActiveByFriendId(Long friendId);

}
