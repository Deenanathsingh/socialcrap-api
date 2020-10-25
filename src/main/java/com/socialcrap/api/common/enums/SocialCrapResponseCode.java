package com.socialcrap.api.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SocialCrapResponseCode {

	SUCCESS("Success", "0"), 
	INTERNAL_SERVER_ERROR("Internal server error", "501"),
	INVALID_ARGUEMENTS_PASSED("Please check your request!", "INV_ARGS"),
	EMPTY_REQUEST("request cannot be empty!", "EMP_REQ"), 
	UN_AUTHORIZED("Not authorized to %s","UN_AUTH"),
	TOKEN_EXPRIED("Token Expired","TOKEN_EXP"),
	COMMON("", ""),
	
	INVALID_ACCESS("Invalid access!", "403"),

	// file
	FILE_UPLOAD_FAILED("File upload failed!", "FAILED"),
	FILE_UPLOAD_ERROR("File can not upload !", "FILE_UPLOAD_ERROR"),
	FILE_PROCESSIG_ERROR("File upload error !", "FILE_UPLOAD_ERROR"), 
	FILE_NOT_FOUND("File not found!", "NF_FILE"),

	// user
	USERS_NOT_FOUND("Users not found!", "NF_USRS"), 
	USER_ID_NOT_FOUND("User not found with id: %s", "NF_USR_ID"),
	USER_NOT_EXIST("User does not exist!","NF_USR"),
	ABOUT_NOT_EXIST("About does not exist!","NF_ABOUT"),
	RECOVERY_NOT_EXIST("Recovery Option does not exist! Please contact Admin","NF_RECOVERY"),
	USER_EMAIL_BLANK("",""),
	USER_EMAIL_NOT_FOUND("Email can not be blank", "BLANK_EML"),
	EXIST_MOBILE_NUMBER("mobileNumber already exist!", "EXIST_MOB_NUM"), 
	EXIST_EMAIL("email already exist!", "EXIST_EML"),
	INVALID_SECURITY_PIN("The security code you entered is wrong!", "INV_SEP"),
	INVALID_LOGIN("Invalid Login!", "INV_LOGIN"),
	TOKEN_UNAVAILABLE("Request Token Unavailable", "UNV_TOKEN"),
	EMPTY_PASSWORD("Password can not be empty!","EMP_PWD"),
	INCORRECT_PASSWORD("Incorrect Password!", "INC_PWD"),
	UNABLE_TO_VERIFY_ANSWER("Unable to verify your identity", "UNB_VERIFY"),
	USER_RE_LOGIN("Login failed! Try to re-login","TKG_FAILD"),
	USER_IS_BLOCKED("This User is permanently blocked! Contact Administrator","BLOCKED_USER"),

	// Comment
	COMMENTS_NOT_FOUND("Comments not found!", "NF_COMMENTS"),
	COMMENT_ID_NOT_FOUND("Comment not found with id: %s", "NF_COMMENT_ID"),

	// Post
	POSTS_NOT_FOUND("Posts not found!", "NF_POSTS"), 
	POST_ID_NOT_FOUND("Post not found with id: %s", "NF_POST_ID"),
	
	// Notification
	NOTIFICTION_NOT_FOUND("Notifications not found!","NF_NOTIFICATION"),
	NOTIFICATION_ID_NOT_FOUND("Notification not found with id: %s","NF_NOTIFICATION_ID"),

	//Email
	UNABLE_TO_SENT_EMAIL("UNABLE TO SENT EMAIL","UN_SENT"),
	EMAIL_SENT_FAILED("EMAIL SENT FAILED","SENT_FAILED"),
	EMAIL_NOT_FOUND("Emails not found!","NF_EMAIL"),
	
	//Role
	ROLE_NOT_FOUND("Role not found!", "NF_ROLE"), 
	ROLE_ID_NOT_FOUND("Role not found with id: %s", "NF_ROLE_ID"),
	
	//Friend
	FRIENDS_NOT_FOUND("Friends not found!", "NF_FRNDS"), 
	FRIEND_ID_NOT_FOUND("Friend not found with id: %s", "NF_FRND_ID"),
	
	//Chat
	CHATS_NOT_FOUND("Chats not found!", "NF_CHATS"), 
	CHAT_ID_NOT_FOUND("Chat not found with id: %s", "NF_CHAT_ID"),
	
	//Support Ticket
	SUPPORT_TICKET_NOT_FOUND("Support Ticket not found!", "NF_TICKET"), 
	SUPPORT_TICKET_ID_NOT_FOUND("Support not found with id: %s", "NF_TICKET_ID"),
		
	//Upload
	UPLOAD_NOT_FOUND("Data not found!", "NF_UPLOAD"), 
	UPLOAD_ID_NOT_FOUND("Data not found with id: %s", "NF_UPLOAD_ID");
	
	
	
	private String message;
	private String code;
}
