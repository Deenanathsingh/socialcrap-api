package com.socialcrap.api.model.pojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FollowerPojo extends BasePojo{

	private Long userId;
	private Long followerId;
	private Boolean notificationEnabled;
	private Boolean postEnabled;

}
