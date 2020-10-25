package com.socialcrap.api.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socialcrap.api.common.converter.StatusHistoryConverter;
import com.socialcrap.api.common.converter.UserConverter;
import com.socialcrap.api.common.service.StatusHistoryService;
import com.socialcrap.api.model.dao.StatusHistoryDao;
import com.socialcrap.api.model.entity.StatusHistory;
import com.socialcrap.api.model.response.dto.StatusHistoryResponse;

@Service
public class StatusHistoryServiceImpl implements StatusHistoryService {

	@Autowired
	private StatusHistoryDao statusHistoryDao;

	@Override
	public void save(StatusHistory request) {
		if (request != null) {
			statusHistoryDao.saveT(request);
		}
	}

	@Override
	public List<StatusHistoryResponse> getAllByReferTo(String referenceId) {
		if (referenceId == null) {
			return null;
		}
		List<StatusHistory> entityList = statusHistoryDao.getAllByReferTo(referenceId);
		List<StatusHistoryResponse> responseList = new ArrayList<>();
		entityList.forEach(e -> {
			StatusHistoryResponse response = StatusHistoryConverter.getResponseFromEntity(e);
			response.setUser(UserConverter.getWrappedEntity(e.getUpdatedBy()));
			responseList.add(response);
		});
		return responseList;
	}

}
