package com.socialcrap.api.model.response.dto;

import com.socialcrap.api.model.pojo.PropertyPojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PropertyResponse extends PropertyPojo {
	private Long id;
}
