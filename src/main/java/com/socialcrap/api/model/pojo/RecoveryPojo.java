package com.socialcrap.api.model.pojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RecoveryPojo {
	private String firstQuestion;
	private String firstAnswer;
	private String secondQuestion;
	private String secondAnswer;
	private String recoveryEmail;
}
