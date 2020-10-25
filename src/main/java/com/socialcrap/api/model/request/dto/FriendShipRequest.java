package com.socialcrap.api.model.request.dto;

import com.socialcrap.api.model.pojo.FriendShipPojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FriendShipRequest extends FriendShipPojo{
	private Long userId;
}
