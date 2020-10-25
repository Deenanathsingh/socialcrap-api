package com.socialcrap.api.model.request.dto;

import java.util.List;

import com.socialcrap.api.model.pojo.NotificationPojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NotificationRequest extends NotificationPojo{
	private List<Long> sentTo;
}
