package com.socialcrap.api.model.pojo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SupportTicketPojo extends BasePojo{
	private String title;
	private String issue;
	private List<String> attachments;
	private String type;
}
