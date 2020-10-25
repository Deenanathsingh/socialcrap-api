package com.socialcrap.api.common.service;

import java.util.List;

import com.socialcrap.api.model.entity.StatusHistory;
import com.socialcrap.api.model.response.dto.StatusHistoryResponse;

public interface StatusHistoryService {

	public void save(StatusHistory request);

	public List<StatusHistoryResponse> getAllByReferTo(String referenceId);

}
