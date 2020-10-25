package com.socialcrap.api.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HistoryStatus {

	OPEN("OPEN"),
	CLOSED("CLOSED");
	
	private String status;
}
