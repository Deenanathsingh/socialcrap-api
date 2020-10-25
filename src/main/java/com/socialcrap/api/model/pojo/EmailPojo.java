package com.socialcrap.api.model.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmailPojo extends BasePojo {

	private List<String> to;
	@JsonInclude(Include.NON_NULL)
	private List<String> cc;
	@JsonInclude(Include.NON_NULL)
	private List<String> bcc;
	@JsonInclude(Include.NON_NULL)
	private List<String> attachments;
	private String subject;
	private String message;
}
