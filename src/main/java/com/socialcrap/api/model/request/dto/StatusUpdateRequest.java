package com.socialcrap.api.model.request.dto;

import com.socialcrap.api.model.pojo.BasePojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StatusUpdateRequest extends BasePojo{
	String remark;
	String status;
	Long userId;
	Long referenceId;

}
