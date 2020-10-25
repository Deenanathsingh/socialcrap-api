package com.socialcrap.api.model.pojo;

import java.util.List;

import com.socialcrap.api.model.wrap.pojo.UserWrap;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostPojo extends BasePojo{
	private String title;
	private String post;
	private UploadPojo upload;
	private List<UserWrap> tags;
}
