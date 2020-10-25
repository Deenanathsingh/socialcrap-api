package com.socialcrap.api.model.pojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BasePojo {
	private String created;
	private String updated;
	private Boolean active;
}
