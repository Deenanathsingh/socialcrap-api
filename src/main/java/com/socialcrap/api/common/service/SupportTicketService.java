package com.socialcrap.api.common.service;

import java.util.List;

import com.socialcrap.api.exception.SocialCrapException;
import com.socialcrap.api.model.request.dto.StatusUpdateRequest;
import com.socialcrap.api.model.request.dto.SupportTicketRequest;
import com.socialcrap.api.model.response.dto.PaginationResponse;
import com.socialcrap.api.model.response.dto.SupportTicketResponse;

public interface SupportTicketService extends AbstractService<SupportTicketResponse, SupportTicketRequest> {

	public List<SupportTicketResponse> getBySentById(Long sentById);

	public void updateStatus(StatusUpdateRequest request) throws SocialCrapException;

	public PaginationResponse<List<SupportTicketResponse>> getAll(String token, Integer limit, Integer offset,
			String status) throws SocialCrapException;
	
	public Long getTotalCount();

}
