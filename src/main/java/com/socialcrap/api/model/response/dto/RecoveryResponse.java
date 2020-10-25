package com.socialcrap.api.model.response.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecoveryResponse {
	private String recoveryEmail;
	private String firstQuestion;
	private String secondQuestion;
}
