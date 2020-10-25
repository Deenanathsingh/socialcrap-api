package com.socialcrap.api.model.response.dto;

import java.util.List;

import com.socialcrap.api.model.pojo.UserPojo;
import com.socialcrap.api.model.wrap.pojo.UserWrap;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse extends UserPojo {
	private Long id;
	private RoleResponse role;
	private List<UserWrap> friends;
	private List<PostResponse> posts;
	private List<UserWrap> friendRequests;
	private AboutResponse about;
	private AddressResponse address;
	private RecoveryResponse recoveryDetails;
	private String status;
	private Boolean blocked;
	private String token;
}
