package com.socialcrap.api.model.pojo;

import java.util.ArrayList;
import java.util.List;

import com.socialcrap.api.model.response.dto.NotificationResponse;
import com.socialcrap.api.model.wrap.pojo.UserWrap;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserPojo extends BasePojo {
	private String firstName;
	private String lastName;
	private String email;
	private String mobileNumber;
	private String profilePic;
	private String coverPhoto;
	private List<UserWrap> followers = new ArrayList<>();
	private List<NotificationResponse> notifications = new ArrayList<>();
}
