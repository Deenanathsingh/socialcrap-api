package com.socialcrap.api.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UploadTag {
	
	VIDEO("VIDEO"),
	PHOTO("PHOTO"),
	UNKNOWN("UNKNOWN");
	
	private String label;
    public static final String COL_DEF = "ENUM('VIDEO','PHOTO','UNKNOWN') DEFAULT UNKNOWN";

}
