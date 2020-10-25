package com.socialcrap.api.model.response.dto;

import com.socialcrap.api.model.pojo.ChatPojo;
import com.socialcrap.api.model.wrap.pojo.UserWrap;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatResponse extends ChatPojo{
	Long id;
	UserWrap sentBy;
	UserWrap sentTo;
	
}
