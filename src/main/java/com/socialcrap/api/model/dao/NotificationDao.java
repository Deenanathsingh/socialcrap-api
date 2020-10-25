package com.socialcrap.api.model.dao;

import java.util.List;

import com.socialcrap.api.model.entity.Notification;

public interface NotificationDao extends AbstractDao<Notification> {

	public List<Notification> getAllByUserId(Long userId);

	public List<Notification> getAllActiveByUserId(Long userId);

	public List<Notification> getAllByAdminId(Long adminId, int limit, int offset);
	
	public Long getCountOfAllByAdminId(Long adminId);

}
