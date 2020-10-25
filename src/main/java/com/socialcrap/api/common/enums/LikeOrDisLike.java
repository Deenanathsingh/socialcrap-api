package com.socialcrap.api.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LikeOrDisLike {

	LIKE("LIKE"),
	DISLIKE("DISLIKE");
	
	private String label;
	public static final String COL_DEF = "ENUM('LIKE','DISLIKE') DEFAULT NULL";
}
