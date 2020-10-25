package com.socialcrap.api.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Notification {

	NOTIFICATION_FRIEND_REQUEST("You have a new friend request from %s"), 
	NOTIFICATION_TAG("%s tagged you in a post"),
	NOTIFICATION_POST_COMMENT("%s commented on your post %s"), 
	NOTIFICATION_POST_LIKE("%s likes your post %s"),
	NOTIFICATION_POST_DISLIKED("%s dislikes your post %s"), 
	NOTIFICATION_COMMENT_LIKE("%s likes you comment %s"),
	NOTIFICATION_COMMENT_DISLIKED("%s dislikes your comment %s");

	private String message;

}
