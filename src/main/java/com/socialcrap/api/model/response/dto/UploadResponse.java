package com.socialcrap.api.model.response.dto;

import com.socialcrap.api.model.pojo.UploadPojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UploadResponse extends UploadPojo {
	private Long id;
}
