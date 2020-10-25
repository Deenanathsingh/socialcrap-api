package com.socialcrap.api.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IssueType {

	PROFILE("PROFILE"), 
	POST("POST"), 
	USER("USER"), 
	PRIVACY("PRIVACY"), 
	POLICY("POLICY");

	private String issue;

}
