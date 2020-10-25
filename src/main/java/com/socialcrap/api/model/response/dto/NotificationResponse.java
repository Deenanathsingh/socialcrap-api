package com.socialcrap.api.model.response.dto;

import com.socialcrap.api.model.pojo.NotificationPojo;
import com.socialcrap.api.model.wrap.pojo.UserWrap;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NotificationResponse extends NotificationPojo{
	Long id;
	UserWrap sentTo;
	UserWrap sentBy;
}
