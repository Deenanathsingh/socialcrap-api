package com.socialcrap.api.model.request.dto;

import com.socialcrap.api.model.pojo.ChatPojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatRequest extends ChatPojo{
	private Long sentBy;
	private Long sentTo;
}
