package com.socialcrap.api.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Sorting {

	ASC("ASC"),
	DESC("DESC");
	
	private String order;
}
