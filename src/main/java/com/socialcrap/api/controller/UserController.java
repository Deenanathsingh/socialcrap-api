package com.socialcrap.api.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.socialcrap.api.common.constants.RestMappingConstants;
import com.socialcrap.api.common.enums.SocialCrapResponseCode;
import com.socialcrap.api.common.service.UserService;
import com.socialcrap.api.exception.SocialCrapException;
import com.socialcrap.api.model.request.dto.AboutRequest;
import com.socialcrap.api.model.request.dto.AddressRequest;
import com.socialcrap.api.model.request.dto.AnswerRequest;
import com.socialcrap.api.model.request.dto.ChangePasswordRequest;
import com.socialcrap.api.model.request.dto.FriendShipRequest;
import com.socialcrap.api.model.request.dto.LoginRequest;
import com.socialcrap.api.model.request.dto.RecoveryRequest;
import com.socialcrap.api.model.request.dto.UserRequest;
import com.socialcrap.api.model.response.dto.BaseApiResponse;
import com.socialcrap.api.model.response.dto.PaginationResponse;
import com.socialcrap.api.model.response.dto.RecoveryResponse;
import com.socialcrap.api.model.response.dto.UserLoginResponse;
import com.socialcrap.api.model.response.dto.UserResponse;
import com.socialcrap.api.model.wrap.pojo.UserWrap;
import com.socialcrap.api.util.PaginationUtil;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = RestMappingConstants.UserConstants.BASE)
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public PaginationResponse<List<UserResponse>> getAll(@RequestHeader(RestMappingConstants.TOKEN) String token,
			HttpServletRequest request, @RequestParam(required = false, defaultValue = "10") Integer limit,
			@RequestParam(required = false, defaultValue = "1") Integer offset,
			@RequestParam(required = false,value="request_id") String requestId) throws SocialCrapException {
		PaginationResponse<List<UserResponse>> response = userService.getAll(token, limit, offset,requestId);
		Long totalCount = userService.getTotalCount();
		PaginationUtil.setPageData(limit, offset, response, totalCount, request);
		return response;
	}

	@GetMapping(path = RestMappingConstants.ID_PARAM)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<UserResponse> getById(@PathVariable(RestMappingConstants.ID) Long id)
			throws SocialCrapException {
		UserResponse response = userService.getById(id);
		if (response == null) {
			return new BaseApiResponse<>(true, SocialCrapResponseCode.USER_ID_NOT_FOUND.getCode(), response,
					String.format(SocialCrapResponseCode.USER_ID_NOT_FOUND.getMessage(), id));
		}
		return new BaseApiResponse<>(response);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<Long> add(@Valid @RequestBody(required = true) UserRequest request)
			throws SocialCrapException {
		Long userId = userService.add(request);
		return new BaseApiResponse<Long>(userId);
	}

	@PatchMapping(path = RestMappingConstants.ID_PARAM)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<Boolean> update(@Valid @RequestBody(required = true) UserRequest request,
			@PathVariable(RestMappingConstants.ID) Long id) throws SocialCrapException {
		Boolean response = userService.update(request, id);
		return new BaseApiResponse<>(response);
	}

	@DeleteMapping(path = RestMappingConstants.ID_PARAM)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<Boolean> delete(@PathVariable(RestMappingConstants.ID) Long id) throws SocialCrapException {
		userService.validateId(id);
		Boolean response = userService.delete(id);
		return new BaseApiResponse<>(response);
	}

	@PostMapping(path = RestMappingConstants.UserConstants.SEND_FRIEND_REQUEST)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<Long> sendFriendRequest(@Valid @RequestBody(required = true) FriendShipRequest request) {
		Long response = userService.sendFriendRequest(request);
		return new BaseApiResponse<>(response);
	}

	@GetMapping(path = RestMappingConstants.UserConstants.CONFIRM_FRIEND_REQUEST)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<Boolean> confirmFriendRequest(@PathVariable(RestMappingConstants.ID) Long id) {
		Boolean response = userService.confirmFriendRequest(id);
		return new BaseApiResponse<>(response);
	}

	@PostMapping(path = RestMappingConstants.UserConstants.UN_FRIEND)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<Boolean> unFriend(@Valid @RequestBody(required = true) FriendShipRequest request) {
		Boolean response = userService.unFriend(request);
		return new BaseApiResponse<>(response);
	}

	@PostMapping(path = RestMappingConstants.UserConstants.LOGIN)
	public BaseApiResponse<UserLoginResponse> login(@Valid @RequestBody(required = true) LoginRequest request)
			throws SocialCrapException {
		UserLoginResponse response = userService.loginUser(request);
		return new BaseApiResponse<UserLoginResponse>(response);
	}

	@PatchMapping(path = RestMappingConstants.ACTIVATE + RestMappingConstants.ID_PARAM)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<Boolean> activate(@PathVariable(RestMappingConstants.ID) Long id)
			throws SocialCrapException {
		Boolean response = userService.activateUser(id);
		return new BaseApiResponse<>(response);
	}

	@PatchMapping(path = RestMappingConstants.ACTIVATE_AND_DEACTIVATE + RestMappingConstants.ID_PARAM)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<Boolean> activeAndDeactivate(@PathVariable(RestMappingConstants.ID) Long id)
			throws SocialCrapException {
		Boolean response = userService.activateAndDeactivate(id);
		return new BaseApiResponse<>(response);
	}

	@PatchMapping(path = RestMappingConstants.BLOCK_AND_UNBLOCK + RestMappingConstants.ID_PARAM)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<Boolean> blockAndUnblock(@PathVariable(RestMappingConstants.ID) Long id)
			throws SocialCrapException {
		Boolean response = userService.blockAndUnblock(id);
		return new BaseApiResponse<>(response);
	}

	@PatchMapping(path = RestMappingConstants.UserConstants.UN_BLOCK + RestMappingConstants.ID_PARAM)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<Boolean> unBlock(@PathVariable(RestMappingConstants.ID) Long id) throws SocialCrapException {
		Boolean response = userService.unBlockUser(id);
		return new BaseApiResponse<>(response);
	}

	@GetMapping(path = RestMappingConstants.UserConstants.BY_TOKEN)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<UserLoginResponse> getByToken(@RequestHeader(RestMappingConstants.TOKEN) String token) {
		UserLoginResponse response = userService.getByToken(token);
		return new BaseApiResponse<>(response);
	}

	@GetMapping(path = RestMappingConstants.UserConstants.COMPLETE_USER_BY_TOKEN)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<UserResponse> getCompleteByToken(@RequestHeader(RestMappingConstants.TOKEN) String token) {
		UserResponse response = userService.getCompleteResponseByToken(token);
		return new BaseApiResponse<>(response);
	}

	@GetMapping(path = RestMappingConstants.UserConstants.SEARCH_USER)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<List<UserWrap>> search(@PathVariable(RestMappingConstants.SEARCH_TERM) String searchTerm,
			@PathVariable(RestMappingConstants.ID) Long userId) {
		List<UserWrap> responseList = userService.searchUser(searchTerm, userId);
		if (CollectionUtils.isEmpty(responseList)) {
			return new BaseApiResponse<>(false, SocialCrapResponseCode.USERS_NOT_FOUND.getCode(), new ArrayList<>(),
					SocialCrapResponseCode.USERS_NOT_FOUND.getMessage());
		}
		return new BaseApiResponse<>(responseList);
	}

	@GetMapping(path = RestMappingConstants.UserConstants.SEARCH_FRIENDS)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<List<UserWrap>> searchFriend(
			@PathVariable(RestMappingConstants.SEARCH_TERM) String searchTerm,
			@PathVariable(RestMappingConstants.ID) Long userId) {
		List<UserWrap> responseList = userService.searchFriend(searchTerm, userId);
		if (CollectionUtils.isEmpty(responseList)) {
			return new BaseApiResponse<>(false, SocialCrapResponseCode.FRIENDS_NOT_FOUND.getCode(), new ArrayList<>(),
					SocialCrapResponseCode.FRIENDS_NOT_FOUND.getMessage());
		}
		return new BaseApiResponse<>(responseList);
	}

	@GetMapping(path = RestMappingConstants.UserConstants.PENDING_FRIENDS)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<List<UserWrap>> getInactiveFriend(@PathVariable(RestMappingConstants.USER_ID) Long userId) {
		List<UserWrap> responseList = userService.getInactiveFriend(userId);
		if (CollectionUtils.isEmpty(responseList)) {
			return new BaseApiResponse<>(false, SocialCrapResponseCode.FRIENDS_NOT_FOUND.getCode(), new ArrayList<>(),
					SocialCrapResponseCode.FRIENDS_NOT_FOUND.getMessage());
		}
		return new BaseApiResponse<>(responseList);
	}

	@GetMapping(path = RestMappingConstants.UserConstants.UPCOMING_BIRTHDAYS)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<List<UserResponse>> getUpcomingBirthdays(
			@PathVariable(RestMappingConstants.USER_ID) Long userId) {
		List<UserResponse> responseList = userService.getUpcomingBirthdays(userId);
		if (CollectionUtils.isEmpty(responseList)) {
			return new BaseApiResponse<>(false, SocialCrapResponseCode.FRIENDS_NOT_FOUND.getCode(), new ArrayList<>(),
					SocialCrapResponseCode.FRIENDS_NOT_FOUND.getMessage());
		}
		return new BaseApiResponse<>(responseList);
	}

	@GetMapping(path = RestMappingConstants.UserConstants.FRIEND_SUGGESTIONS)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<List<UserWrap>> getFriendSuggestions(
			@PathVariable(RestMappingConstants.USER_ID) Long userId) {
		List<UserWrap> responseList = userService.getFriendSuggestions(userId);
		if (CollectionUtils.isEmpty(responseList)) {
			return new BaseApiResponse<>(false, SocialCrapResponseCode.USERS_NOT_FOUND.getCode(), new ArrayList<>(),
					SocialCrapResponseCode.USERS_NOT_FOUND.getMessage());
		}
		return new BaseApiResponse<>(responseList);
	}

	@GetMapping(path = RestMappingConstants.UserConstants.RECOVER)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<RecoveryResponse> recover(@PathVariable(RestMappingConstants.USER) String user)
			throws SocialCrapException {
		RecoveryResponse response = userService.validateUser(user);
		if (StringUtils.isEmpty(response)) {
			return new BaseApiResponse<>(false, SocialCrapResponseCode.RECOVERY_NOT_EXIST.getCode(), null,
					SocialCrapResponseCode.RECOVERY_NOT_EXIST.getMessage());
		}
		return new BaseApiResponse<>(response);
	}

	@PostMapping(path = RestMappingConstants.UserConstants.ANSWER)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<Long> answerSecurityQuestion(@Valid @RequestBody(required = true) AnswerRequest request,
			@PathVariable(RestMappingConstants.USER) String user) throws SocialCrapException {
		Long response = userService.answerSecurityQuestion(user, request);
		return new BaseApiResponse<>(response);
	}

	@PatchMapping(path = RestMappingConstants.UserConstants.RESET_PASSWORD)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<Boolean> resetPassword(@Valid @RequestBody(required = true) ChangePasswordRequest request,
			@PathVariable(RestMappingConstants.USER_ID) Long userId) throws SocialCrapException {
		Boolean response = userService.resetPassword(userId, request);
		return new BaseApiResponse<>(response);
	}

	@PatchMapping(path = RestMappingConstants.UserConstants.CHANGE_PASSWORD)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<Boolean> changePassword(@Valid @RequestBody(required = true) ChangePasswordRequest request,
			@PathVariable(RestMappingConstants.USER_ID) Long userId) throws SocialCrapException {
		Boolean response = userService.changePassword(userId, request);
		return new BaseApiResponse<>(response);
	}

	@PatchMapping(path = RestMappingConstants.UserConstants.UPDATE_ABOUT)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<Boolean> updateAbout(@Valid @RequestBody(required = true) AboutRequest request,
			@PathVariable(RestMappingConstants.USER_ID) Long userId) throws SocialCrapException {
		Boolean response = userService.updateAbout(userId, request);
		return new BaseApiResponse<>(response);
	}

	@PatchMapping(path = RestMappingConstants.UserConstants.UPDATE_ADDRESS)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<Boolean> updateAddress(@Valid @RequestBody(required = true) AddressRequest request,
			@PathVariable(RestMappingConstants.USER_ID) Long userId) throws SocialCrapException {
		Boolean response = userService.updateAddress(userId, request);
		return new BaseApiResponse<>(response);
	}

	@PatchMapping(path = RestMappingConstants.UserConstants.UPDATE_RECOVERY_OPTIONS)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<Boolean> updateRecoveryOptions(@Valid @RequestBody(required = true) RecoveryRequest request,
			@PathVariable(RestMappingConstants.USER_ID) Long userId) throws SocialCrapException {
		Boolean response = userService.updateRecoveryOptions(userId, request);
		return new BaseApiResponse<>(response);
	}

	@GetMapping(path = RestMappingConstants.UserConstants.ALL_WRAPPED)
	@ResponseStatus(HttpStatus.OK)
	public BaseApiResponse<List<UserWrap>> getAllByAdminToken(@RequestHeader(RestMappingConstants.TOKEN) String token)
			throws SocialCrapException {
		List<UserWrap> responseList = userService.getAllByAdminToken(token);
		if (CollectionUtils.isEmpty(responseList)) {
			return new BaseApiResponse<>(false, SocialCrapResponseCode.USERS_NOT_FOUND.getCode(), new ArrayList<>(),
					SocialCrapResponseCode.USERS_NOT_FOUND.getMessage());
		}
		return new BaseApiResponse<>(responseList);
	}

}
