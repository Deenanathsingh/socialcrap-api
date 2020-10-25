package com.socialcrap.api.model.response.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserStatsResponse {

	private Long followers;
	private Long postLikes;
	private Long postcomments;
	private Long commentLikes;
	private Long postReach;
	private Double rating;
	
}
