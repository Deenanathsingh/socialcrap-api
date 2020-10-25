package com.socialcrap.api.model.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.socialcrap.api.model.dao.FriendDao;
import com.socialcrap.api.model.entity.BaseEntity;
import com.socialcrap.api.model.entity.Friend;

@Repository
public class FriendDaoImpl extends AbstractDaoImpl<Friend> implements FriendDao {

	public FriendDaoImpl() {
		super(Friend.class);
	}

	@Override
	public List<Friend> getAllByUserId(Long userId, Boolean isActive) {
		String hql = FROM_ENTITY + " where user_id=" + userId + " and " + BaseEntity.Table.IS_ACTIVE + "=" + isActive;
		return getAllT(hql);
	}

	@Override
	public List<Friend> getAllByFriendId(Long friendId, Boolean isActive) {
		String hql = FROM_ENTITY + " where friend_id=" + friendId + " and " + BaseEntity.Table.IS_ACTIVE + "="
				+ isActive;
		return getAllT(hql);
	}

	@Override
	public Friend getFriendByUserAndFriend(Long userId, Long friendId) {
		String hql = FROM_ENTITY + " where user_id=" + userId + " and friend_id=" + friendId;
		return getT(hql);
	}

	@Override
	public Boolean validateFriendShip(Long userId, Long friendId) {
		String hql = FROM_ENTITY + " where user_id=" + userId + " and friend_id=" + friendId;
		return (getT(hql) == null) ? false : true;
	}

	@Override
	public List<Friend> getAllInActiveByUserId(Long userId) {
		String hql = FROM_ENTITY + " where user_id=" + userId + " and " + BaseEntity.Table.IS_ACTIVE + "=" + true;
		return getAllT(hql);
	}

	@Override
	public List<Friend> getAllInActiveByFriendId(Long friendId) {
		String hql = FROM_ENTITY + " where friendId=" + friendId + " and " + BaseEntity.Table.IS_ACTIVE + "=" + false;
		return getAllT(hql);
	}

}
