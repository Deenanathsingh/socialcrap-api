package com.socialcrap.api.model.response.dto;

import java.util.List;

import com.socialcrap.api.model.pojo.SupportTicketPojo;
import com.socialcrap.api.model.wrap.pojo.UserWrap;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SupportTicketResponse extends SupportTicketPojo {
	private Long id;
	private UserWrap sentBy;
	private StatusHistoryResponse currentStatus;
	private List<StatusHistoryResponse> statusHistory;
}
