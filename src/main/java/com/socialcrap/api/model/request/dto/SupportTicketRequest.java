package com.socialcrap.api.model.request.dto;

import com.socialcrap.api.model.pojo.SupportTicketPojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SupportTicketRequest extends SupportTicketPojo{
	private Long sentBy;
}
