package com.socialcrap.api.common.constants;

public interface EntityDetails {
	public interface User {
		public String TABLE_NAME = "user";
		public String ID = "id";
		public String USER = "user";
		public String SENT_TO = "sentTo";
		public String POST_ID = "postId";
		public String FRIENDS = "friends";
		public String USER_ID = "userId";
		public String FRIEND_ID = "friendId";
		public String EMAIL = "email";
		public String MOBILE_NUMBER = "mobileNumber";
		public String FIRST_NAME = "firstName";
		public String ACTIVE = "active";
		public String BLOCKED = "blocked";
	}

	public interface Role {
		public String TABLE_NAME = "role";
		public String ID = "id";
		public String CREATED_DATE = "created";
	}

	public interface Friend {
		public String TABLE_NAME = "friend";
		public String ID = "id";
		public String USER = "user";
		public String FRIEND = "friend";
		public String ACTIVE = "active";
		public String USER_ID = "userId";
		public String FRIEND_ID = "friendId";
	}

	public interface Post {
		public String TABLE_NAME = "post";
		public String ID = "id";
		public String POST = "post";
		public String USER = "user";
		public String STATUS = "status";
	}

	public interface Upload {
		public String TABLE_NAME = "upload";
		public String ID = "id";
	}

	public interface Comment {
		public String TABLE_NAME = "comment";
		public String ID = "id";
	}

	public interface PostLike {
		public String TABLE_NAME = "postLike";
		public String ID = "id";
	}

	public interface CommentLike {
		public String TABLE_NAME = "commentLike";
		public String ID = "id";
	}

	public interface Email {
		public String TABLE_NAME = "email";
		public String ID = "id";
		public String CREATED_DATE = "created";
	}

	public interface LoginToken {
		public String TABLE_NAME = "loginToken";
		public String ID = "id";
		public String USER = "user";
		public String TOKEN = "token";
	}

	public interface Notification {
		public String TABLE_NAME = "notification";
		public String ID = "id";
		public String CREATED_DATE = "created";
	}

	public interface Follower {
		public String TABLE_NAME = "follower";
		public String ID = "id";
		public String CREATED_DATE = "created";
		public String USER_ID = "userId";
		public String FOLLOWER_ID = "followerId";
		public String ACTIVE = "active";
	}

	public interface Property {
		public String TABLE_NAME = "property";
		public String NAME = "name";
		public String CREATED_DATE = "created";
		public String UPDATED_DATE = "updated";
	}

}
