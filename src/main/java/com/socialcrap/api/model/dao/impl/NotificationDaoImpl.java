package com.socialcrap.api.model.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.socialcrap.api.model.dao.NotificationDao;
import com.socialcrap.api.model.entity.BaseEntity;
import com.socialcrap.api.model.entity.Notification;

@Repository
public class NotificationDaoImpl extends AbstractDaoImpl<Notification> implements NotificationDao {

	public NotificationDaoImpl() {
		super(Notification.class);
	}

	@Override
	public List<Notification> getAllByUserId(Long userId) {
		String hql = FROM_ENTITY + String.format(" where %s=%s", Notification.Table.SENT_TO, userId);
		return getAllT(hql);
	}

	@Override
	public List<Notification> getAllActiveByUserId(Long userId) {
		String hql = FROM_ENTITY + String.format(" where %s=%s and %s=%s", Notification.Table.SENT_TO, userId,
				BaseEntity.Table.IS_ACTIVE, true);
		return getAllT(hql);
	}

	@Override
	public List<Notification> getAllByAdminId(Long adminId, int limit, int offset) {
		String hql = FROM_ENTITY + String.format(" where %s=%s", Notification.Table.SENT_BY, adminId);
		return getAllT(hql, limit, offset);
	}

	@Override
	public Long getCountOfAllByAdminId(Long adminId) {
		String hql = "SELECT COUNT(id) " + String.format(FROM_ENTITY + " where %s=%s", Notification.Table.SENT_BY, adminId);
		return (Long) getSession().createQuery(hql).uniqueResult();
	}

}
