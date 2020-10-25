package com.socialcrap.api.common.service;

import java.util.List;

import com.socialcrap.api.exception.SocialCrapException;
import com.socialcrap.api.model.entity.User;
import com.socialcrap.api.model.request.dto.AboutRequest;
import com.socialcrap.api.model.request.dto.AddressRequest;
import com.socialcrap.api.model.request.dto.AnswerRequest;
import com.socialcrap.api.model.request.dto.ChangePasswordRequest;
import com.socialcrap.api.model.request.dto.FriendShipRequest;
import com.socialcrap.api.model.request.dto.LoginRequest;
import com.socialcrap.api.model.request.dto.RecoveryRequest;
import com.socialcrap.api.model.request.dto.UserRequest;
import com.socialcrap.api.model.response.dto.PaginationResponse;
import com.socialcrap.api.model.response.dto.RecoveryResponse;
import com.socialcrap.api.model.response.dto.UserLoginResponse;
import com.socialcrap.api.model.response.dto.UserResponse;
import com.socialcrap.api.model.response.dto.UserStatsResponse;
import com.socialcrap.api.model.wrap.pojo.UserWrap;

public interface UserService extends AbstractService<UserResponse, UserRequest> {

	public PaginationResponse<List<UserResponse>> getAll(String token, Integer limit, Integer offset)
			throws SocialCrapException;

	public PaginationResponse<List<UserResponse>> getAll(String token, Integer limit, Integer offset, String requestId)
			throws SocialCrapException;

	public Long sendFriendRequest(FriendShipRequest request);

	public Boolean confirmFriendRequest(Long friendShipId);

	public Boolean unFriend(FriendShipRequest request);

	public Boolean validateFriendShip(FriendShipRequest request);

	public void validateRegistration(UserRequest request) throws SocialCrapException;

	public UserLoginResponse loginUser(LoginRequest request) throws SocialCrapException;

	public UserLoginResponse loginAdmin(LoginRequest request) throws SocialCrapException;

	public Boolean activateUser(Long id) throws SocialCrapException;

	public Boolean deActivateUser(Long id) throws SocialCrapException;

	public Boolean blockUser(Long id);

	public Boolean unBlockUser(Long id);

	public UserLoginResponse getByToken(String token);

	public UserResponse getCompleteResponseByToken(String token);

	public List<UserWrap> searchUser(String searchTerm, Long userId);

	public List<UserWrap> searchFriend(String searchTerm, Long userId);

	public List<UserResponse> getUpcomingBirthdays(Long userId);

	public List<UserWrap> getFriendSuggestions(Long userId);

	public List<User> getFriends(Long userId);

	public List<UserWrap> getInactiveFriend(Long userId);

	public List<User> getInactiveFriendShips(Long userId);

	public RecoveryResponse validateUser(String email) throws SocialCrapException;

	public Long answerSecurityQuestion(String email, AnswerRequest request) throws SocialCrapException;

	public Boolean resetPassword(Long userId, ChangePasswordRequest request) throws SocialCrapException;

	public Boolean changePassword(Long userId, ChangePasswordRequest request) throws SocialCrapException;

	public Boolean updateAbout(Long userId, AboutRequest request) throws SocialCrapException;

	public Boolean updateAddress(Long userId, AddressRequest request) throws SocialCrapException;

	public Boolean updateRecoveryOptions(Long userId, RecoveryRequest request) throws SocialCrapException;

	public UserStatsResponse getUserStats(Long userId);

	public Long addAdmin(UserRequest request) throws SocialCrapException;

	public Long getTotalCount();

	public List<UserWrap> getAllByAdminToken(String token) throws SocialCrapException;

	public Boolean activateAndDeactivate(Long userId);

	public Boolean blockAndUnblock(Long userId);

}
