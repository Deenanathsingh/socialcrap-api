package com.socialcrap.api.common.constants;

public interface RestMappingConstants {

	public String APP_BASE = "/socialCrap/api";
	public String ID = "id";
	public String ID_PARAM = "/{" + ID + "}";
	public String USER_ID = "userId";
	public String USER = "user";
	public String USER_ID_PARAM = "/{" + USER_ID + "}";
	public String ALL = "/**";
	public String ACTIVATE = "/activate";
	public String DE_ACTIVATE = "/deactivate";
	public String TOKEN = "token";
	public String SEARCH_TERM = "searchTerm";
	public String BLOCK_AND_UNBLOCK = "blockAndUnblock";
	public String ACTIVATE_AND_DEACTIVATE = "/activateAndDeactivate";

	public interface UserConstants {
		public String BASE = APP_BASE + "/user";
		public String ID = "id";
		public String LOGIN = "/login";
		public String SEND_FRIEND_REQUEST = "/sendFriendRequest";
		public String CONFIRM_FRIEND_REQUEST = "/confirmFriendRequest/{" + ID + "}";
		public String UN_FRIEND = "/unFriend";
		public String FOLLOW = "/follow";
		public String BLOCK = "/block";
		public String UN_BLOCK = "/unBlock";
		public String BY_TOKEN = "/byToken";
		public String COMPLETE_USER_BY_TOKEN = "/CompleteUserbyToken";
		public String SEARCH_USER = "/search/{" + ID + "}/{" + SEARCH_TERM + "}";
		public String SEARCH_FRIENDS = "/search/friends/{" + ID + "}/{" + SEARCH_TERM + "}";
		public String UPCOMING_BIRTHDAYS = "/birthdays/{" + USER_ID + "}";
		public String FRIEND_SUGGESTIONS = "/suggestions/{" + USER_ID + "}";
		public String PENDING_FRIENDS = "/pendingFriends/{" + USER_ID + "}";
		public String RECOVER = "/recover/{" + USER + "}";
		public String ANSWER = "/answer/{" + USER + "}";
		public String RESET_PASSWORD = "/resetPassword/{" + USER_ID + "}";
		public String CHANGE_PASSWORD = "/changePassword/{" + USER_ID + "}";
		public String UPDATE_RECOVERY_OPTIONS = "/updateRecoveryOptions/{" + USER_ID + "}";
		public String UPDATE_ABOUT = "/updateAbout/{" + USER_ID + "}";
		public String UPDATE_ADDRESS = "/updateAddress/{" + USER_ID + "}";
		public String ALL_WRAPPED = "/wrapped";
		public String ADD = "/add";
	}

	public interface AdminConstants {
		public String BASE = APP_BASE + "/admin";
		public String LOGIN = "/login";
	}

	public interface PostConstants {
		public String BASE = APP_BASE + "/post";
		public String LIKE = "like";
		public String DIS_LIKE = "dislike";
		public String CUSTOM = "custom";
	}

	public interface CommentConstants {
		public String BASE = APP_BASE + "/comment";
		public String LIKE = "like";
		public String DIS_LIKE = "dislike";
	}

	public interface FileConstants {
		public String BASE = APP_BASE + "/file";
		public String ATTACH = "/attach";
		public String UPLOAD = "/upload";
	}

	public interface NotificationConstants {
		public String BASE = APP_BASE + "/notification";
		public String CLEAR_ALL = "/clearAllByUserId";
		public String ACTIVE = "/active";
		public String BY_ADMIN ="/byAdmin";
	}

	public interface EmailConstants {
		public String BASE = APP_BASE + "/email";
	}

	public interface Role {
		public String BASE = APP_BASE + "/role";
		public String SAVE_ALL = "/saveAll";
	}

	public interface Follower {
		public String BASE = APP_BASE + "/follower";
	}

	public interface Upload {
		public String BASE = APP_BASE + "/upload";
		public String VIDEO = "/video";
		public String PHOTO = "/photo";
		public String UPDATE_PROFILE_PIC = "/updateProfilePic";
		public String UPDATE_COVER_PHOTO = "/updateCoverPhoto";
	}

	public interface ChatConstants {
		public String BASE = APP_BASE + "/chat";
		public String SENT_TO = "sentTo";
		public String SENT_BY = "sentBy";
	}

	public interface SupportTicketConstants {
		public String BASE = APP_BASE + "/ticket";
		public String BY_USER = "/byUser/{" + USER_ID + "}";
		public String UPDATE_STATUS = "/updateStatus";
	}
}
