package com.socialcrap.api.common.service;

import java.util.List;

import com.socialcrap.api.exception.SocialCrapException;
import com.socialcrap.api.model.request.dto.EmailRequest;
import com.socialcrap.api.model.response.dto.EmailResponse;
import com.socialcrap.api.model.response.dto.PaginationResponse;

public interface EmailService extends AbstractService<EmailResponse, EmailRequest> {
	public Boolean sendEmail(String token, EmailRequest request) throws SocialCrapException;

	public Boolean sendSMS(EmailRequest request) throws SocialCrapException;
	
	public PaginationResponse<List<EmailResponse>> getAll(String token, Integer limit, Integer offset) throws SocialCrapException;

	public Long getTotalCountOfAllByAdminId(String token) throws SocialCrapException;
}
