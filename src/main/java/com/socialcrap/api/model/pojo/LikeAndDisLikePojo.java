package com.socialcrap.api.model.pojo;

import java.util.ArrayList;
import java.util.List;

import com.socialcrap.api.model.wrap.pojo.UserWrap;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeAndDisLikePojo {

	private Long numberOfLikes = 0L;
	private Long numberOfDisLikes = 0L;
	private List<UserWrap> likedBy = new ArrayList<>();
	private List<UserWrap> disLikedBy= new ArrayList<>();

}
