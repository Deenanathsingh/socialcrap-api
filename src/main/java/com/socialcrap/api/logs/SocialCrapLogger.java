package com.socialcrap.api.logs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socialcrap.api.model.dao.LoggingDao;
import com.socialcrap.api.model.entity.LoggingEntity;

@Service
public class SocialCrapLogger {

	@Autowired
	private LoggingDao loggingDao;

	protected String entityName;

	public void info(String message, String requestId, Class<?> cls) {
		LoggingEntity entity = new LoggingEntity();
		entity.setMessage(message);
		entity.setType("INFO");
		entity.setEntity(cls.getName());
		saveLog(entity);
	}

	public void error(String message, String requestId, Class<?> cls) {
		LoggingEntity entity = new LoggingEntity();
		entity.setMessage(message);
		entity.setType("ERROR");
		entity.setEntity(cls.getName());
		saveLog(entity);
	}

	private void saveLog(LoggingEntity entity) {
		if (entity != null) {
			loggingDao.saveT(entity);
		}
	}

}
