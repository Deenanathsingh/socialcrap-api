package com.socialcrap.api.model.response.dto;

import com.socialcrap.api.model.pojo.FriendShipPojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FriendShipResponse extends FriendShipPojo {
	private String status;
}
