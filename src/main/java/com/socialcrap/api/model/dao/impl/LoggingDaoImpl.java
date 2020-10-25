package com.socialcrap.api.model.dao.impl;

import org.springframework.stereotype.Repository;

import com.socialcrap.api.model.dao.LoggingDao;
import com.socialcrap.api.model.entity.LoggingEntity;

@Repository
public class LoggingDaoImpl extends AbstractDaoImpl<LoggingEntity> implements LoggingDao {

	public LoggingDaoImpl() {
		super(LoggingEntity.class);
	}

}
