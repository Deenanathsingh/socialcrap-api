package com.socialcrap.api.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Roles {
	
	ADMIN("ADMIN"),
	USER("USER"),
	DEVELOPER("DEVELOPER"),
	EMPLOYEE("EMPLOYEE");
	
	private String role;
	public static final String COL_DEF = "ENUM('ADMIN','USER','DEVELOPER','EMPLOYEE') DEFAULT NULL";
}
