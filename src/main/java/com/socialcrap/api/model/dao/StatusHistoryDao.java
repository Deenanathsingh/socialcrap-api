package com.socialcrap.api.model.dao;

import java.util.List;

import com.socialcrap.api.model.entity.StatusHistory;

public interface StatusHistoryDao extends AbstractDao<StatusHistory> {

	public List<StatusHistory> getAllByReferTo(String referenceId);

}
