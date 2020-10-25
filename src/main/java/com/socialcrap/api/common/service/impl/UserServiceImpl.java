package com.socialcrap.api.common.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.socialcrap.api.common.converter.FollowerConverter;
import com.socialcrap.api.common.converter.NotificationConverter;
import com.socialcrap.api.common.converter.RoleConverter;
import com.socialcrap.api.common.converter.UserConverter;
import com.socialcrap.api.common.enums.Roles;
import com.socialcrap.api.common.enums.SocialCrapResponseCode;
import com.socialcrap.api.common.enums.Sorting;
import com.socialcrap.api.common.service.LoginTokenService;
import com.socialcrap.api.common.service.PostService;
import com.socialcrap.api.common.service.UserService;
import com.socialcrap.api.exception.SocialCrapException;
import com.socialcrap.api.logs.SocialCrapLogger;
import com.socialcrap.api.model.dao.FollowerDao;
import com.socialcrap.api.model.dao.FriendDao;
import com.socialcrap.api.model.dao.NotificationDao;
import com.socialcrap.api.model.dao.RoleDao;
import com.socialcrap.api.model.dao.UserDao;
import com.socialcrap.api.model.entity.Follower;
import com.socialcrap.api.model.entity.Friend;
import com.socialcrap.api.model.entity.LoginToken;
import com.socialcrap.api.model.entity.Notification;
import com.socialcrap.api.model.entity.Role;
import com.socialcrap.api.model.entity.User;
import com.socialcrap.api.model.pojo.RecoveryPojo;
import com.socialcrap.api.model.request.dto.AboutRequest;
import com.socialcrap.api.model.request.dto.AddressRequest;
import com.socialcrap.api.model.request.dto.AnswerRequest;
import com.socialcrap.api.model.request.dto.ChangePasswordRequest;
import com.socialcrap.api.model.request.dto.FriendShipRequest;
import com.socialcrap.api.model.request.dto.LoginRequest;
import com.socialcrap.api.model.request.dto.RecoveryRequest;
import com.socialcrap.api.model.request.dto.UserRequest;
import com.socialcrap.api.model.response.dto.AboutResponse;
import com.socialcrap.api.model.response.dto.PaginationResponse;
import com.socialcrap.api.model.response.dto.RecoveryResponse;
import com.socialcrap.api.model.response.dto.UserLoginResponse;
import com.socialcrap.api.model.response.dto.UserResponse;
import com.socialcrap.api.model.response.dto.UserStatsResponse;
import com.socialcrap.api.model.wrap.pojo.UserWrap;
import com.socialcrap.api.util.DateTimeUtil;
import com.socialcrap.api.util.JsonUtil;
import com.socialcrap.api.util.PaginationUtil;

@Service
public class UserServiceImpl implements UserService {

//	@Autowired
//	private BCryptPasswordEncoder bCryptPasswordEncoder;

	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private SocialCrapLogger log;

	@Autowired
	private UserDao userDao;

	@Autowired
	private FriendDao friendDao;

	@Autowired
	private PostService postService;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private LoginTokenService loginTokenService;

	@Autowired
	private FollowerDao followerDao;

	@Autowired
	private NotificationDao notificationDao;

	@Override
	public List<UserResponse> getAll() {
		return getResponseList(userDao.getAllSorted(Sorting.DESC, "created"));
	}

	@Override
	public UserResponse getById(Long id) {
		return getResponse(userDao.getActiveTById(id));
	}

	@Override
	public Long add(UserRequest request) throws SocialCrapException {
		if (StringUtils.isEmpty(request)) {
			throw new SocialCrapException(SocialCrapResponseCode.EMPTY_REQUEST.getMessage(),
					SocialCrapResponseCode.EMPTY_REQUEST.getCode(), HttpStatus.BAD_REQUEST);
		}
		validateRegistration(request);
		User entity = UserConverter.getEntityFromRequest(request);
		if (StringUtils.isEmpty(entity)) {
			throw new SocialCrapException(SocialCrapResponseCode.EMPTY_REQUEST.getMessage(),
					SocialCrapResponseCode.EMPTY_REQUEST.getCode(), HttpStatus.BAD_REQUEST);
		}
		Role role = roleDao.getTByField(Role.Table.ROLE, Roles.USER.getRole());
		if (role == null) {
			throw new SocialCrapException(SocialCrapResponseCode.ROLE_NOT_FOUND.getMessage(),
					SocialCrapResponseCode.ROLE_NOT_FOUND.getCode(), HttpStatus.BAD_REQUEST);
		}
		entity.setRole(role);
		if (request.getPassword() != null) {
			entity.setPassword(request.getPassword());
		}
		userDao.saveT(entity);
		return entity.getId();
	}

	@Override
	public Long addAdmin(UserRequest request) throws SocialCrapException {
		if (StringUtils.isEmpty(request)) {
			throw new SocialCrapException(SocialCrapResponseCode.EMPTY_REQUEST.getMessage(),
					SocialCrapResponseCode.EMPTY_REQUEST.getCode(), HttpStatus.BAD_REQUEST);
		}
		validateRegistration(request);
		User entity = UserConverter.getEntityFromRequest(request);
		if (StringUtils.isEmpty(entity)) {
			throw new SocialCrapException(SocialCrapResponseCode.EMPTY_REQUEST.getMessage(),
					SocialCrapResponseCode.EMPTY_REQUEST.getCode(), HttpStatus.BAD_REQUEST);
		}
		Role role = roleDao.getTByField(Role.Table.ROLE, Roles.ADMIN.getRole());
		if (role == null) {
			throw new SocialCrapException(SocialCrapResponseCode.ROLE_NOT_FOUND.getMessage(),
					SocialCrapResponseCode.ROLE_NOT_FOUND.getCode(), HttpStatus.BAD_REQUEST);
		}
		entity.setRole(role);
		if (request.getPassword() != null) {
			entity.setPassword(request.getPassword());
		}
		userDao.saveT(entity);
		return entity.getId();
	}

	@Override
	public Boolean update(UserRequest request, Long id) {
		if (request == null) {
			return false;
		}
		if (userDao.isExist(id)) {
			User entity = userDao.getTById(id);
			UserConverter.updateData(request, entity);
			userDao.updateT(entity);
			return true;
		}
		return false;
	}

	@Override
	public Boolean delete(Long id) throws SocialCrapException {
		validateId(id);
		userDao.deleteT(id);
		return true;
	}

	private List<UserResponse> getResponseList(List<User> requestList) {
		List<UserResponse> responseList = requestList.stream().map(r -> getResponse(r)).collect(Collectors.toList());
		return responseList;
	}

	private UserResponse getResponse(User request) {
		if (request == null) {
			return null;
		}
		UserResponse response = UserConverter.getResponseFromEntity(request);
		if (response == null) {
			return null;
		}
		List<Friend> listByUserId = friendDao.getAllByUserId(response.getId(), true);
		List<Long> userIdListFromFriends = listByUserId.stream().map(Friend::getFriendId).collect(Collectors.toList());
		List<Friend> listByFriendId = friendDao.getAllByFriendId(response.getId(), true);
		List<Long> userIdListFromUsers = listByFriendId.stream().map(Friend::getUserId).collect(Collectors.toList());
		List<User> friendList = new ArrayList<>();

		if (!CollectionUtils.isEmpty(userIdListFromFriends)) {
			friendList.addAll(userDao.getAllByIdList(userIdListFromFriends));
		}

		if (!CollectionUtils.isEmpty(userIdListFromUsers)) {
			friendList.addAll(userDao.getAllByIdList(userIdListFromUsers));
		}

		if (!StringUtils.isEmpty(request.getRole())) {
			response.setRole(RoleConverter.getResponseFromEntity(request.getRole()));
		}

		if (!CollectionUtils.isEmpty(request.getNotifications())) {

			List<Notification> activeNotifications = new ArrayList<>();
			for (Notification n : request.getNotifications()) {
				if (n.isActive()) {
					activeNotifications.add(n);
				}
			}
			response.setNotifications(NotificationConverter.getResponseListFromEntityList(activeNotifications));
		}

		List<Follower> followerList = followerDao.getByUserId(request.getId());
		if (!CollectionUtils.isEmpty(followerList)) {
			List<Long> followerIdList = followerList.stream().map(Follower::getFollowerId).collect(Collectors.toList());
			if (!CollectionUtils.isEmpty(followerIdList)) {
				List<User> followers = userDao.getAllByIdList(followerIdList);
				if (!CollectionUtils.isEmpty(followers)) {
					response.setFollowers(UserConverter.getWrappedEntityList(followers));
				}
			}
		}
		response.setFriends(UserConverter.getWrappedEntityList(friendList));
		response.setPosts(postService.getResponseList(request.getPost()));
		response.setFriendRequests(getFriendRequest(response.getId()));
		return response;
	}

	@Override
	public Long sendFriendRequest(FriendShipRequest request) {
		if (request == null) {
			return 0l;
		}
		if (!userDao.isExist(request.getUserId())) {
			return 0l;
		}
		if (!userDao.isExist(request.getFriendId())) {
			return 0l;
		}
		Friend check = null;
		check = friendDao.getFriendByUserAndFriend(request.getUserId(), request.getFriendId());
		if (check == null) {
			check = friendDao.getFriendByUserAndFriend(request.getFriendId(), request.getUserId());
		}
		if (check != null) {
			return 0l;
		}
		follow(request.getUserId(), request.getFriendId());
		Friend entity = new Friend();
		entity.setUserId(request.getUserId());
		entity.setFriendId(request.getFriendId());
		friendDao.saveT(entity);
		if (entity.getId() != null) {
			User friend = userDao.getTById(request.getUserId());
			User user = userDao.getTById(request.getFriendId());
			String message = String.format(
					com.socialcrap.api.common.enums.Notification.NOTIFICATION_FRIEND_REQUEST.getMessage(),
					friend.getFirstName());
			Notification notification = NotificationConverter.getEntityFromRequest(message, user);
			notificationDao.saveT(notification);
		}
		return entity.getId();
	}

	@Override
	public Boolean confirmFriendRequest(Long friendShipId) {
		if (friendDao.isExist(friendShipId)) {
			Friend friend = friendDao.getTById(friendShipId);
			follow(friend.getFriendId(), friend.getUserId());
			friend.setActive(true);
			friendDao.updateT(friend);
			return true;
		}
		return false;
	}

	@Override
	public Boolean unFriend(FriendShipRequest request) {
		Friend friend = friendDao.getFriendByUserAndFriend(request.getUserId(), request.getFriendId());
		if (friend == null) {
			friend = friendDao.getFriendByUserAndFriend(request.getFriendId(), request.getUserId());
		}
		if (friend != null) {
			friendDao.deleteT(friend.getId());
			return true;
		}
		return false;
	}

	@Override
	public Boolean validateFriendShip(FriendShipRequest request) {
		if (request == null) {
			return false;
		}
		if (friendDao.validateFriendShip(request.getUserId(), request.getFriendId())) {
			return true;
		}
		if (friendDao.validateFriendShip(request.getFriendId(), request.getUserId())) {
			return true;
		}
		return false;
	}

	private List<UserWrap> getFriendRequest(Long userId) {

		if (!userDao.isExist(userId)) {
			return null;
		}

		List<Friend> listByFriendId = friendDao.getAllInActiveByFriendId(userId);

//		 List<Long> userIdListFromUsers =
//		 listByFriendId.stream().map(Friend::getUserId).collect(Collectors.toList());
//		 List<User> friendList = new ArrayList<>();
//		 if (!CollectionUtils.isEmpty(userIdListFromUsers)) {
//		 friendList.addAll(userDao.getAllByIdList(userIdListFromUsers));
//		 }
		Collections.sort(listByFriendId, (f1, f2) -> f2.getCreated().compareTo(f1.getCreated()));
		List<UserWrap> userWrapList = new ArrayList<>();
		for (Friend friend : listByFriendId) {
			User user = userDao.getTById(friend.getUserId());
			UserWrap userWrap = UserConverter.getWrappedEntity(user);
			userWrap.setRequestId(friend.getId());
			userWrapList.add(userWrap);
		}
		if (CollectionUtils.isEmpty(userWrapList)) {
			return new ArrayList<>();
		}
		return userWrapList;
	}

	@Override
	public void validateId(Long id) throws SocialCrapException {
		if (!userDao.isExist(id)) {
			String message = String.format(SocialCrapResponseCode.USER_ID_NOT_FOUND.getMessage(), id);
			throw new SocialCrapException(message, SocialCrapResponseCode.USER_ID_NOT_FOUND.getCode(),
					HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public void validateRegistration(UserRequest request) throws SocialCrapException {
		if (StringUtils.isEmpty(request)) {
			throw new SocialCrapException(SocialCrapResponseCode.EMPTY_REQUEST.getMessage(),
					SocialCrapResponseCode.EMPTY_REQUEST.getCode(), HttpStatus.BAD_REQUEST);
		}
		if (request.getEmail() == null) {
			throw new SocialCrapException(SocialCrapResponseCode.USER_EMAIL_BLANK.getMessage(),
					SocialCrapResponseCode.USER_EMAIL_BLANK.getCode(), HttpStatus.BAD_REQUEST);
		}
		if (userDao.emailExist(request.getEmail())) {
			throw new SocialCrapException(SocialCrapResponseCode.EXIST_EMAIL.getMessage(),
					SocialCrapResponseCode.EXIST_EMAIL.getCode(), HttpStatus.BAD_REQUEST);
		}
		if (request.getMobileNumber() != null) {
			if (userDao.mobileNumberExist(request.getMobileNumber())) {
				throw new SocialCrapException(SocialCrapResponseCode.EXIST_MOBILE_NUMBER.getMessage(),
						SocialCrapResponseCode.EXIST_MOBILE_NUMBER.getCode(), HttpStatus.BAD_REQUEST);
			}
		}
	}

	@Override
	public UserLoginResponse loginUser(LoginRequest request) throws SocialCrapException {
//		SocialCrapLogger log = new SocialCrapLogger(UserServiceImpl.class);
		logger.error("User request for login");
		log.info("User request for login", null, UserServiceImpl.class);
		if (StringUtils.isEmpty(request)) {
			return null;
		}
		UserLoginResponse response = null;
		if (request.getUser() != null) {
			if (request.getPassword() == null) {
				throw new SocialCrapException(SocialCrapResponseCode.EMPTY_PASSWORD.getMessage(),
						SocialCrapResponseCode.EMPTY_PASSWORD.getCode(), HttpStatus.BAD_REQUEST);
			}
			User entity = userDao.getLoginDetails(request.getUser());
			if (StringUtils.isEmpty(entity)) {
				throw new SocialCrapException(SocialCrapResponseCode.USER_NOT_EXIST.getMessage(),
						SocialCrapResponseCode.USER_NOT_EXIST.getCode(), HttpStatus.BAD_REQUEST);
			}
			if (!entity.getRole().getRole().equals(Roles.USER.getRole())) {
				throw new SocialCrapException(SocialCrapResponseCode.INVALID_LOGIN.getMessage(),
						SocialCrapResponseCode.INVALID_LOGIN.getCode(), HttpStatus.BAD_REQUEST);
			}
			if (entity.isBlocked()) {
				throw new SocialCrapException(SocialCrapResponseCode.USER_IS_BLOCKED.getMessage(),
						SocialCrapResponseCode.USER_IS_BLOCKED.getCode(), HttpStatus.BAD_REQUEST);
			}
			if (request.getPassword().equals(entity.getPassword())) {
				response = UserConverter.getUserLoginResponseFromEntity(entity);
				if (!StringUtils.isEmpty(response)) {
					String token = loginTokenService.add(entity);
					if (token == null) {
						throw new SocialCrapException(SocialCrapResponseCode.USER_RE_LOGIN.getMessage(),
								SocialCrapResponseCode.USER_RE_LOGIN.getCode(), HttpStatus.REQUEST_TIMEOUT);
					}
					response.setToken(token);
				}
				return response;
			}
			throw new SocialCrapException(SocialCrapResponseCode.INCORRECT_PASSWORD.getMessage(),
					SocialCrapResponseCode.INCORRECT_PASSWORD.getCode(), HttpStatus.BAD_REQUEST);
		}
		return null;
	}

	@Override
	public UserLoginResponse loginAdmin(LoginRequest request) throws SocialCrapException {
		if (StringUtils.isEmpty(request)) {
			return null;
		}
		UserLoginResponse response = null;
		if (request.getUser() != null) {
			if (request.getPassword() == null) {
				throw new SocialCrapException(SocialCrapResponseCode.EMPTY_PASSWORD.getMessage(),
						SocialCrapResponseCode.EMPTY_PASSWORD.getCode(), HttpStatus.BAD_REQUEST);
			}
			User entity = userDao.getLoginDetails(request.getUser());
			if (StringUtils.isEmpty(entity)) {
				throw new SocialCrapException(SocialCrapResponseCode.USER_NOT_EXIST.getMessage(),
						SocialCrapResponseCode.USER_NOT_EXIST.getCode(), HttpStatus.BAD_REQUEST);
			}
			if (!entity.getRole().getRole().equals(Roles.ADMIN.getRole())) {
				throw new SocialCrapException(SocialCrapResponseCode.INVALID_LOGIN.getMessage(),
						SocialCrapResponseCode.INVALID_LOGIN.getCode(), HttpStatus.BAD_REQUEST);
			}
			if (entity.isBlocked()) {
				throw new SocialCrapException(SocialCrapResponseCode.USER_IS_BLOCKED.getMessage(),
						SocialCrapResponseCode.USER_IS_BLOCKED.getCode(), HttpStatus.BAD_REQUEST);
			}
			if (request.getPassword().equals(entity.getPassword())) {
				response = UserConverter.getUserLoginResponseFromEntity(entity);
				if (!StringUtils.isEmpty(response)) {
					String token = loginTokenService.add(entity);
					if (token == null) {
						throw new SocialCrapException(SocialCrapResponseCode.USER_RE_LOGIN.getMessage(),
								SocialCrapResponseCode.USER_RE_LOGIN.getCode(), HttpStatus.REQUEST_TIMEOUT);
					}
					response.setToken(token);
				}
				return response;
			}
			throw new SocialCrapException(SocialCrapResponseCode.INCORRECT_PASSWORD.getMessage(),
					SocialCrapResponseCode.INCORRECT_PASSWORD.getCode(), HttpStatus.BAD_REQUEST);
		}
		return null;
	}

	private void follow(Long followingId, Long followerId) {
		if (followingId != null && followerId != null) {
			User following = userDao.getTById(followingId);
			User follower = userDao.getTById(followerId);
			if (following != null && follower != null) {
				Follower entity = FollowerConverter.getEntityFromFriendShipRequest(following.getId(), follower.getId());
				if (entity != null) {
					followerDao.saveT(entity);
				}
			}
		}
	}

	private void blockOrUnblockUser(Long id, boolean status) throws SocialCrapException {
		User entity = userDao.getTById(id);
		if (entity == null) {
			String message = String.format(SocialCrapResponseCode.USER_ID_NOT_FOUND.getMessage(), id);
			throw new SocialCrapException(message, SocialCrapResponseCode.USER_ID_NOT_FOUND.getCode(),
					HttpStatus.NOT_FOUND);
		}
		userDao.blockAndUnBlock(entity.getId(), status);
	}

	private void activateOrDeactivate(Long id, boolean status) throws SocialCrapException {
		User entity = userDao.getTById(id);
		if (entity == null) {
			String message = String.format(SocialCrapResponseCode.USER_ID_NOT_FOUND.getMessage(), id);
			throw new SocialCrapException(message, SocialCrapResponseCode.USER_ID_NOT_FOUND.getCode(),
					HttpStatus.NOT_FOUND);
		}
		userDao.activateAndDeActivate(entity.getId(), status);
	}

	@Override
	public Boolean activateUser(Long id) throws SocialCrapException {
		activateOrDeactivate(id, true);
		return true;
	}

	@Override
	public Boolean deActivateUser(Long id) throws SocialCrapException {
		activateOrDeactivate(id, false);
		return true;
	}

	@Override
	public Boolean blockUser(Long id) {
		try {
			blockOrUnblockUser(id, true);
			return true;
		} catch (SocialCrapException e) {
			return false;
		}
	}

	@Override
	public Boolean unBlockUser(Long id) {
		try {
			blockOrUnblockUser(id, false);
			return true;
		} catch (SocialCrapException e) {
			return false;
		}
	}

	@Override
	public void activate(Long id) {

	}

	@Override
	public void deactivate(Long id) {

	}

	@Override
	public void addAll(List<UserRequest> requestList) {

	}

	@Override
	public void updateAll(List<UserRequest> requestList) {

	}

	@Override
	public UserLoginResponse getByToken(String token) {
		LoginToken loginToken = loginTokenService.getByToken(token);
		if (loginToken.getUser() == null) {
			return null;
		}
		User user = userDao.getTById(loginToken.getUser().getId());
		UserLoginResponse response = UserConverter.getUserLoginResponseFromEntity(user);
		return response;
	}

	@Override
	public UserResponse getCompleteResponseByToken(String token) {
		LoginToken loginToken = loginTokenService.getByToken(token);
		if (loginToken.getUser() == null) {
			return null;
		}
		User user = userDao.getTById(loginToken.getUser().getId());
		return getResponse(user);
	}

	@Override
	public List<UserWrap> searchUser(String searchTerm, Long userId) {
		if (searchTerm == null) {
			return null;
		}
		List<User> users = new ArrayList<>();
		String cols[] = { User.Table.FIRST_NAME, User.Table.LAST_NAME };
		users = userDao.search(searchTerm, User.Table.FIRST_NAME, Sorting.ASC, cols);
		if (CollectionUtils.isEmpty(users)) {
			return null;
		}
		User checked = null;
		for (User u : users) {
			if (u.getId() == userId) {
				checked = u;
			}
		}
		if (checked != null) {
			users.remove(users.indexOf(checked));
		}
		return UserConverter.getWrappedEntityList(users);
	}

	@Override
	public List<UserWrap> searchFriend(String searchTerm, Long userId) {
		if (searchTerm == null) {
			return null;
		}
		List<User> users = new ArrayList<>();
		String cols[] = { User.Table.FIRST_NAME, User.Table.LAST_NAME };
		users = userDao.search(searchTerm, User.Table.FIRST_NAME, Sorting.ASC, cols);
		if (CollectionUtils.isEmpty(users)) {
			return null;
		}
		User checked = null;
		for (User u : users) {
			if (u.getId() == userId) {
				checked = u;
			}
		}
		if (checked != null) {
			users.remove(users.indexOf(checked));
		}
		List<UserWrap> friendRequests = getFriendRequest(userId);
		Map<Long, Long> map = new HashMap<>();
		if (!CollectionUtils.isEmpty(friendRequests)) {
			friendRequests.forEach(f -> {
				if (!map.containsKey(f.getUserId())) {
					map.put(f.getUserId(), f.getRequestId());
				}
			});
		}
		List<UserWrap> responseList = new ArrayList<>();
		for (User u : users) {
			UserWrap wrap = UserConverter.getWrappedEntity(u);
			if (map.containsKey(u.getId())) {
				wrap.setRequestId(map.get(u.getId()));
			}
			responseList.add(wrap);
		}
		return responseList;
	}

	@Override
	public List<User> getFriends(Long userId) {
		List<Friend> listByUserId = friendDao.getAllByUserId(userId, true);
		List<Long> userIdListFromFriends = listByUserId.stream().map(Friend::getFriendId).collect(Collectors.toList());
		List<Friend> listByFriendId = friendDao.getAllByFriendId(userId, true);
		List<Long> userIdListFromUsers = listByFriendId.stream().map(Friend::getUserId).collect(Collectors.toList());
		List<User> friendList = new ArrayList<>();
		if (!CollectionUtils.isEmpty(userIdListFromFriends)) {
			friendList.addAll(userDao.getAllByIdList(userIdListFromFriends));
		}
		if (!CollectionUtils.isEmpty(userIdListFromUsers)) {
			friendList.addAll(userDao.getAllByIdList(userIdListFromUsers));
		}
		return friendList;
	}

	@Override
	public List<UserResponse> getUpcomingBirthdays(Long userId) {
		List<User> friendList = getFriends(userId);
		if (CollectionUtils.isEmpty(friendList)) {
			return null;
		}
		List<UserResponse> usersWithDob = new LinkedList<>();
		friendList.forEach(u -> {
			if (u.getAbout() != null) {
				AboutResponse about = JsonUtil.convertToPojo(u.getAbout(), AboutResponse.class);
				if (about.getDob() != null) {
					Date dob = DateTimeUtil.parseDate(about.getDob(), DateTimeUtil.DATE_PATTERN);
					Date afterWeek = DateTimeUtil.modifyDate(new Date(), 0, 0, 10);
					Date currentDate = new Date();
					int years = (int) ChronoUnit.YEARS.between(LocalDateTime.now(),
							LocalDateTime.ofInstant(dob.toInstant(), ZoneId.systemDefault()));
					dob = DateTimeUtil.modifyDate(dob, Math.abs(years) + 1, 0, 0);
					if (dob.after(currentDate) && dob.before(afterWeek)) {
						UserResponse user = getResponse(u);
						usersWithDob.add(user);
					}
				}
			}
		});
		if (CollectionUtils.isEmpty(usersWithDob)) {
			return null;
		}
		return usersWithDob;
	}

	@Override
	public List<UserWrap> getFriendSuggestions(Long userId) {

		User entity = userDao.getTById(userId);

		List<User> friends = getFriends(userId);

		List<User> inactiveFriends = getInactiveFriendShips(userId);

		if (!CollectionUtils.isEmpty(inactiveFriends)) {
			friends.addAll(inactiveFriends);
		}

		Map<Long, User> map = new HashMap<>();
		if (!CollectionUtils.isEmpty(friends)) {
			for (User u : friends) {
				if (!map.containsKey(u.getId())) {
					map.put(u.getId(), u);
				}
			}
		}

		List<User> allUsers = userDao.getAllT();
		allUsers.remove(allUsers.indexOf(entity));
		if (CollectionUtils.isEmpty(allUsers)) {
			return null;
		}
		List<User> removalableUsers = new ArrayList<>();
		for (User u : allUsers) {
			if (map.containsKey(u.getId())) {
				removalableUsers.add(u);
			}
		}
		if (!CollectionUtils.isEmpty(removalableUsers)) {
			allUsers.removeAll(removalableUsers);
		}
		if (CollectionUtils.isEmpty(allUsers)) {
			return null;
		}
		return UserConverter.getWrappedEntityList(getRandom(allUsers, 10));
	}

	private <T> List<T> getRandom(List<T> list, int numberOfElements) {
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		Random rand = new Random();
		List<T> newList = new ArrayList<>();

		int size = 0;

		if (list.size() > numberOfElements) {
			size = numberOfElements;
		} else {
			size = list.size();
		}
		for (int i = 0; i < size; i++) {
			int randomIndex = rand.nextInt(list.size());
			newList.add(list.get(randomIndex));
			list.remove(randomIndex);
		}
		return newList;
	}

	@Override
	public List<UserWrap> getInactiveFriend(Long userId) {
		List<Friend> listByUserId = friendDao.getAllByUserId(userId, false);
		List<Long> userIdListFromFriends = listByUserId.stream().map(Friend::getFriendId).collect(Collectors.toList());
		List<User> friendList = new ArrayList<>();
		if (!CollectionUtils.isEmpty(userIdListFromFriends)) {
			friendList.addAll(userDao.getAllByIdList(userIdListFromFriends));
		}
		return UserConverter.getWrappedEntityList(friendList);
	}

	@Override
	public List<User> getInactiveFriendShips(Long userId) {
		List<Friend> listByUserId = friendDao.getAllByUserId(userId, false);
		List<Long> userIdListFromFriends = listByUserId.stream().map(Friend::getFriendId).collect(Collectors.toList());
		List<Friend> listByFriendId = friendDao.getAllByFriendId(userId, false);
		List<Long> userIdListFromUsers = listByFriendId.stream().map(Friend::getUserId).collect(Collectors.toList());
		List<User> friendList = new ArrayList<>();
		if (!CollectionUtils.isEmpty(userIdListFromFriends)) {
			friendList.addAll(userDao.getAllByIdList(userIdListFromFriends));
		}
		if (!CollectionUtils.isEmpty(userIdListFromUsers)) {
			friendList.addAll(userDao.getAllByIdList(userIdListFromUsers));
		}
		return friendList;
	}

	@Override
	public RecoveryResponse validateUser(String user) throws SocialCrapException {
		User entity = userDao.getLoginDetails(user);
		if (entity == null) {
			throw new SocialCrapException(SocialCrapResponseCode.USER_NOT_EXIST.getMessage(),
					SocialCrapResponseCode.USER_NOT_EXIST.getCode(), HttpStatus.FOUND);
		}
		RecoveryPojo recovery = null;
		if (!StringUtils.isEmpty(entity.getAbout())) {
			recovery = JsonUtil.convertToPojo(entity.getRecoveryDetails(), RecoveryPojo.class);
		}
		if (recovery == null) {
			throw new SocialCrapException(SocialCrapResponseCode.RECOVERY_NOT_EXIST.getMessage(),
					SocialCrapResponseCode.RECOVERY_NOT_EXIST.getCode(), HttpStatus.FOUND);
		}
		if (recovery.getFirstQuestion() == null) {
			throw new SocialCrapException(SocialCrapResponseCode.RECOVERY_NOT_EXIST.getMessage(),
					SocialCrapResponseCode.RECOVERY_NOT_EXIST.getCode(), HttpStatus.FOUND);
		}
		if (recovery.getFirstQuestion() == null) {
			throw new SocialCrapException(SocialCrapResponseCode.RECOVERY_NOT_EXIST.getMessage(),
					SocialCrapResponseCode.RECOVERY_NOT_EXIST.getCode(), HttpStatus.FOUND);
		}
		RecoveryResponse response = new RecoveryResponse();
		response.setFirstQuestion(recovery.getFirstQuestion());
		response.setSecondQuestion(recovery.getSecondQuestion());
		return response;
	}

	@Override
	public Long answerSecurityQuestion(String email, AnswerRequest request) throws SocialCrapException {
		if (request == null) {
			throw new SocialCrapException(SocialCrapResponseCode.EMPTY_REQUEST.getMessage(),
					SocialCrapResponseCode.EMPTY_REQUEST.getCode(), HttpStatus.BAD_REQUEST);
		}
		User entity = userDao.getLoginDetails(email);
		if (entity == null) {
			throw new SocialCrapException(SocialCrapResponseCode.USER_NOT_EXIST.getMessage(),
					SocialCrapResponseCode.USER_NOT_EXIST.getCode(), HttpStatus.FOUND);
		}
		RecoveryPojo recovery = null;
		if (!StringUtils.isEmpty(entity.getAbout())) {
			recovery = JsonUtil.convertToPojo(entity.getRecoveryDetails(), RecoveryPojo.class);
		}
		if (recovery == null) {
			throw new SocialCrapException(SocialCrapResponseCode.RECOVERY_NOT_EXIST.getMessage(),
					SocialCrapResponseCode.RECOVERY_NOT_EXIST.getCode(), HttpStatus.FOUND);
		}
		if (recovery.getFirstQuestion() == null) {
			throw new SocialCrapException(SocialCrapResponseCode.RECOVERY_NOT_EXIST.getMessage(),
					SocialCrapResponseCode.RECOVERY_NOT_EXIST.getCode(), HttpStatus.FOUND);
		}
		if (recovery.getFirstQuestion() == null) {
			throw new SocialCrapException(SocialCrapResponseCode.RECOVERY_NOT_EXIST.getMessage(),
					SocialCrapResponseCode.RECOVERY_NOT_EXIST.getCode(), HttpStatus.FOUND);
		}
		if (!recovery.getFirstAnswer().equalsIgnoreCase(request.getFirstAnswer())) {
			throw new SocialCrapException(SocialCrapResponseCode.UNABLE_TO_VERIFY_ANSWER.getMessage(),
					SocialCrapResponseCode.UNABLE_TO_VERIFY_ANSWER.getCode(), HttpStatus.FOUND);
		}
		if (!recovery.getSecondAnswer().equalsIgnoreCase(request.getSecondAnswer())) {
			throw new SocialCrapException(SocialCrapResponseCode.UNABLE_TO_VERIFY_ANSWER.getMessage(),
					SocialCrapResponseCode.UNABLE_TO_VERIFY_ANSWER.getCode(), HttpStatus.FOUND);
		}
		return entity.getId();
	}

	@Override
	public Boolean resetPassword(Long userId, ChangePasswordRequest request) throws SocialCrapException {
		if (request == null || userId == null) {
			throw new SocialCrapException(SocialCrapResponseCode.EMPTY_REQUEST.getMessage(),
					SocialCrapResponseCode.EMPTY_REQUEST.getCode(), HttpStatus.BAD_REQUEST);
		}
		User entity = userDao.getTById(userId);
		if (entity == null) {
			throw new SocialCrapException(SocialCrapResponseCode.USER_NOT_EXIST.getMessage(),
					SocialCrapResponseCode.USER_NOT_EXIST.getCode(), HttpStatus.FOUND);
		}
		entity.setPassword(request.getNewPassword());
		userDao.updateT(entity);
		return true;
	}

	@Override
	public Boolean changePassword(Long userId, ChangePasswordRequest request) throws SocialCrapException {
		if (userId == null || request == null) {
			throw new SocialCrapException(SocialCrapResponseCode.EMPTY_REQUEST.getMessage(),
					SocialCrapResponseCode.EMPTY_REQUEST.getCode(), HttpStatus.BAD_REQUEST);
		}
		User user = userDao.getTById(userId);
		if (user == null) {
			throw new SocialCrapException(SocialCrapResponseCode.USER_ID_NOT_FOUND.getMessage(),
					SocialCrapResponseCode.USER_ID_NOT_FOUND.getCode(), HttpStatus.NOT_FOUND);
		}
		if (!request.getOldPassword().equals(user.getPassword())) {
			throw new SocialCrapException(SocialCrapResponseCode.INCORRECT_PASSWORD.getMessage(),
					SocialCrapResponseCode.INCORRECT_PASSWORD.getCode(), HttpStatus.BAD_REQUEST);
		}
		user.setPassword(request.getNewPassword());
		userDao.updateT(user);
		return true;
	}

	@Override
	public Boolean updateAbout(Long userId, AboutRequest request) throws SocialCrapException {
		if (userId == null || request == null) {
			throw new SocialCrapException(SocialCrapResponseCode.EMPTY_REQUEST.getMessage(),
					SocialCrapResponseCode.EMPTY_REQUEST.getCode(), HttpStatus.BAD_REQUEST);
		}
		User user = userDao.getTById(userId);
		if (user == null) {
			throw new SocialCrapException(SocialCrapResponseCode.USER_ID_NOT_FOUND.getMessage(),
					SocialCrapResponseCode.USER_ID_NOT_FOUND.getCode(), HttpStatus.NOT_FOUND);
		}
		user.setAbout(JsonUtil.getJson(request));
		userDao.updateT(user);
		return true;
	}

	@Override
	public Boolean updateAddress(Long userId, AddressRequest request) throws SocialCrapException {
		if (userId == null || request == null) {
			throw new SocialCrapException(SocialCrapResponseCode.EMPTY_REQUEST.getMessage(),
					SocialCrapResponseCode.EMPTY_REQUEST.getCode(), HttpStatus.BAD_REQUEST);
		}
		User user = userDao.getTById(userId);
		if (user == null) {
			throw new SocialCrapException(SocialCrapResponseCode.USER_ID_NOT_FOUND.getMessage(),
					SocialCrapResponseCode.USER_ID_NOT_FOUND.getCode(), HttpStatus.NOT_FOUND);
		}
		user.setAddress(JsonUtil.getJson(request));
		userDao.updateT(user);
		return true;
	}

	@Override
	public Boolean updateRecoveryOptions(Long userId, RecoveryRequest request) throws SocialCrapException {
		if (userId == null || request == null) {
			throw new SocialCrapException(SocialCrapResponseCode.EMPTY_REQUEST.getMessage(),
					SocialCrapResponseCode.EMPTY_REQUEST.getCode(), HttpStatus.BAD_REQUEST);
		}
		User user = userDao.getTById(userId);
		if (user == null) {
			throw new SocialCrapException(SocialCrapResponseCode.USER_ID_NOT_FOUND.getMessage(),
					SocialCrapResponseCode.USER_ID_NOT_FOUND.getCode(), HttpStatus.NOT_FOUND);
		}
		user.setRecoveryDetails(JsonUtil.getJson(request));
		userDao.updateT(user);
		return true;
	}

	@Override
	public UserStatsResponse getUserStats(Long userId) {
		return null;
	}

	@Override
	public PaginationResponse<List<UserResponse>> getAll(String token, Integer limit, Integer offset)
			throws SocialCrapException {

		loginTokenService.validateAdminToken(token);

		List<User> entityList = userDao.getAllT(limit, PaginationUtil.getOffset(limit, offset));
		PaginationResponse<List<UserResponse>> responseList = null;
		if (!CollectionUtils.isEmpty(entityList)) {
			responseList = new PaginationResponse<>(getResponseList(entityList));
			return responseList;
		}
		return new PaginationResponse<>(new ArrayList<>());
	}

	@Override
	public PaginationResponse<List<UserResponse>> getAll(String token, Integer limit, Integer offset, String requestId)
			throws SocialCrapException {
		loginTokenService.validateAdminToken(token);

		List<User> entityList = userDao.getAllT(limit, PaginationUtil.getOffset(limit, offset));
		PaginationResponse<List<UserResponse>> responseList = null;
		if (!CollectionUtils.isEmpty(entityList)) {
			responseList = new PaginationResponse<>(getResponseList(entityList));
			return responseList;
		}
		return new PaginationResponse<>(new ArrayList<>());
	}

	@Override
	public Long getTotalCount() {
		return userDao.getTotalCount();
	}

	@Override
	public List<UserWrap> getAllByAdminToken(String token) throws SocialCrapException {

		loginTokenService.validateAdminToken(token);

		List<User> entityList = userDao.getAllSorted(Sorting.ASC, User.Table.FIRST_NAME);
		return UserConverter.getWrappedEntityList(entityList);
	}

	@Override
	public Boolean activateAndDeactivate(Long userId) {
		User user = userDao.getTById(userId);
		if (user != null)
			if (user.isActive()) {
				user.setActive(false);
			} else {
				user.setActive(true);
			}
		userDao.updateT(user);
		return true;
	}

	@Override
	public Boolean blockAndUnblock(Long userId) {
		User user = userDao.getTById(userId);
		if (user != null)
			if (user.isBlocked()) {
				user.setBlocked(false);
			} else {
				user.setBlocked(true);
			}
		userDao.updateT(user);
		return true;
	}

}
