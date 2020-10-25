package com.socialcrap.api.model.response.dto;

import com.socialcrap.api.model.pojo.BasePojo;
import com.socialcrap.api.model.wrap.pojo.UserWrap;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StatusHistoryResponse extends BasePojo {
	private Long id;
	private String remark;
	private String status;
	private String type;
	private UserWrap user;
}
