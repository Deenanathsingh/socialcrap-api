package com.socialcrap.api.model.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.socialcrap.api.model.dao.StatusHistoryDao;
import com.socialcrap.api.model.entity.StatusHistory;

@Repository
public class StatusHistoryDaoImpl extends AbstractDaoImpl<StatusHistory>
		implements StatusHistoryDao, StatusHistory.Table {

	public StatusHistoryDaoImpl() {
		super(StatusHistory.class);
	}

	@Override
	public List<StatusHistory> getAllByReferTo(String referenceId) {
		String hql = String.format(FROM_ENTITY + " where %s='%s'", REFER_TO, referenceId);
		return getAllT(hql);
	}

}
