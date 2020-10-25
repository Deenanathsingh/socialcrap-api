package com.socialcrap.api.common.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.socialcrap.api.common.converter.NotificationConverter;
import com.socialcrap.api.common.converter.PostConverter;
import com.socialcrap.api.common.converter.PostLikeConverter;
import com.socialcrap.api.common.converter.UploadConverter;
import com.socialcrap.api.common.converter.UserConverter;
import com.socialcrap.api.common.enums.LikeOrDisLike;
import com.socialcrap.api.common.enums.SocialCrapResponseCode;
import com.socialcrap.api.common.enums.Sorting;
import com.socialcrap.api.common.service.CommentService;
import com.socialcrap.api.common.service.LoginTokenService;
import com.socialcrap.api.common.service.PostService;
import com.socialcrap.api.common.service.UserService;
import com.socialcrap.api.exception.SocialCrapException;
import com.socialcrap.api.model.dao.NotificationDao;
import com.socialcrap.api.model.dao.PostDao;
import com.socialcrap.api.model.dao.PostLikeDao;
import com.socialcrap.api.model.dao.UploadDao;
import com.socialcrap.api.model.dao.UserDao;
import com.socialcrap.api.model.entity.BaseEntity;
import com.socialcrap.api.model.entity.Notification;
import com.socialcrap.api.model.entity.Post;
import com.socialcrap.api.model.entity.PostLike;
import com.socialcrap.api.model.entity.Upload;
import com.socialcrap.api.model.entity.User;
import com.socialcrap.api.model.request.dto.PostLikeRequest;
import com.socialcrap.api.model.request.dto.PostRequest;
import com.socialcrap.api.model.response.dto.LikeAndDisLikeResponse;
import com.socialcrap.api.model.response.dto.PaginationResponse;
import com.socialcrap.api.model.response.dto.PostResponse;
import com.socialcrap.api.model.wrap.pojo.UserWrap;
import com.socialcrap.api.util.PaginationUtil;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostDao postDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private PostLikeDao postLikeDao;
	@Autowired
	private CommentService commentService;
	@Autowired
	private NotificationDao notificationDao;
	@Autowired
	private UploadDao uploadDao;
	@Autowired
	private UserService userService;
	@Autowired
	private LoginTokenService loginTokenService;

	@Override
	public List<PostResponse> getAll() {
		return getResponseList(postDao.getAllT());
	}

	@Override
	public PostResponse getById(Long id) {
		return getResponse(postDao.getTById(id));
	}

	@Override
	public Long add(PostRequest request) {
		if (request == null) {
			return null;
		}
		Post entity = PostConverter.getEntityFromRequest(request);
		if (entity == null) {
			return null;
		}
		User user = null;
		if (userDao.isExist(request.getUserId())) {
			user = userDao.getTById(request.getUserId());
			entity.setPostedBy(user);
		}
		if (!CollectionUtils.isEmpty(request.getTags())) {
			List<User> tags = new ArrayList<>();
			for (UserWrap tag : request.getTags()) {
				User userEntity = userDao.getTById(tag.getUserId());
				if (userEntity != null) {
					tags.add(userEntity);
				}
			}
			entity.setTags(tags);
		}
		postDao.saveT(entity);
		if (request.getUpload() != null) {
			Upload upload = UploadConverter.getEntityFromRequest(request.getUpload());
			upload.setUploadedBy(user);
			uploadDao.saveT(upload);
			entity.setUpload(upload);
			postDao.updateT(entity);
		}
		return entity.getId();
	}

	@Override
	public Boolean update(PostRequest request, Long id) throws SocialCrapException {
		if (request == null) {
			return false;
		}
		Post entity = postDao.getTById(id);
		if (entity == null) {
			return false;
		}
		if (request.getUserId() != null) {
			if ((entity.getPostedBy().getId()) != request.getUserId()) {
				throw new SocialCrapException(
						String.format(SocialCrapResponseCode.UN_AUTHORIZED.getMessage(), "to update this post"),
						SocialCrapResponseCode.UN_AUTHORIZED.getCode(), HttpStatus.BAD_REQUEST);
			}
		}
		PostConverter.updateData(request, entity);
		postDao.updateT(entity);
		return true;
	}

	@Override
	public Boolean delete(Long id) {
		if (postDao.isExist(id)) {
			postDao.deleteT(id);
			return true;
		}
		return false;
	}

	@Override
	public List<PostResponse> getResponseList(List<Post> requestList) {
		if (CollectionUtils.isEmpty(requestList)) {
			return new ArrayList<>();
		}
		List<PostResponse> responseList = new ArrayList<>();
		responseList = requestList.stream().map(r -> getResponse(r)).collect(Collectors.toList());
		return responseList;
	}

	@Override
	public PostResponse getResponse(Post request) {
		if (request == null) {
			return null;
		}
		PostResponse response = PostConverter.getResponseFromEntity(request);
//		List<PostLike> postLikedList = postLikeDao.getAllLiked(request.getId());
//
//		List<Long> userIdListLiked = null;
//		List<User> usersLiked = null;
//		if (!CollectionUtils.isEmpty(postLikedList)) {
//			userIdListLiked = postLikedList.stream().map(PostLike::getUserId).collect(Collectors.toList());
//		}
//		if (!CollectionUtils.isEmpty(userIdListLiked)) {
//			usersLiked = userDao.getAllByIdList(userIdListLiked);
//		}

//		List<PostLike> postDisLikedList = postLikeDao.getAllDisLiked(request.getId());
//		List<Long> userIdListDisLiked = null;
//		List<User> usersDisLiked = null;
//		if (!CollectionUtils.isEmpty(postDisLikedList)) {
//			userIdListDisLiked = postDisLikedList.stream().map(PostLike::getUserId).collect(Collectors.toList());
//		}
//		if (!CollectionUtils.isEmpty(userIdListDisLiked)) {
//			usersDisLiked = userDao.getAllByIdList(userIdListDisLiked);
//		}

		LikeAndDisLikeResponse likeAndDisLikeResponse = new LikeAndDisLikeResponse();
		likeAndDisLikeResponse.setNumberOfLikes((long) request.getLikedByUsers().size());
		likeAndDisLikeResponse.setLikedBy(UserConverter.getWrappedEntityList(request.getLikedByUsers()));
//		if (!CollectionUtils.isEmpty(usersLiked)) {
//			likeAndDisLikeResponse.setNumberOfLikes(new Long(usersLiked.size()));
//			likeAndDisLikeResponse.setLikedBy(UserConverter.getWrappedEntityList(usersLiked));
//		}
//		if (!CollectionUtils.isEmpty(usersDisLiked)) {
//			likeAndDisLikeResponse.setNumberOfDisLikes(new Long(usersDisLiked.size()));
//			likeAndDisLikeResponse.setDisLikedBy(UserConverter.getWrappedEntityList(usersDisLiked));
//		}

		response.setLikesAndDisLikes(likeAndDisLikeResponse);
		response.setComments(commentService.getResponseList(request.getComments()));
		response.setUser(UserConverter.getWrappedEntity(request.getPostedBy()));
		response.setUpload(UploadConverter.getResponseFromEntity(request.getUpload()));
		response.setTags(UserConverter.getWrappedEntityList(request.getTags()));
		return response;
	}

	@Override
	public Boolean like(PostLikeRequest request) throws SocialCrapException {
		if (request == null) {
			return null;
		}
//		PostLike entity = null;
		User user = userDao.getTById(request.getUserId());
		if (user == null) {
			throw new SocialCrapException(
					String.format(SocialCrapResponseCode.USER_ID_NOT_FOUND.getMessage(), request.getUserId()),
					SocialCrapResponseCode.USER_ID_NOT_FOUND.getCode(), HttpStatus.BAD_REQUEST);
		}
		Post entity = postDao.getTById(request.getPostId());
		if (entity == null) {
			throw new SocialCrapException(
					String.format(SocialCrapResponseCode.POST_ID_NOT_FOUND.getMessage(), request.getPostId()),
					SocialCrapResponseCode.POST_ID_NOT_FOUND.getCode(), HttpStatus.BAD_REQUEST);
		}
//		if (postLikeDao.isExistByUserIdAndPostId(request.getUserId(), request.getPostId())) {
//			entity = postLikeDao.getByUserIdAndPostId(request.getUserId(), request.getPostId());
//			if (entity.getLikeOrDisLike().equals(LikeOrDisLike.LIKE)) {
//				postLikeDao.deleteT(entity.getId());
//				return true;
//			}
//			entity.setLikeOrDisLike(LikeOrDisLike.LIKE);
//			postLikeDao.updateT(entity);
//			notificationDao.saveT(notification);
//			return true;
//		}
//		entity = PostLikeConverter.getEntityFromRequest(request);
//		entity.setLikeOrDisLike(LikeOrDisLike.LIKE);
//		postLikeDao.saveT(entity);
		List<User> users = new ArrayList<>();
		users = entity.getLikedByUsers();
		Map<Long, User> map = new HashMap<>();
		if (!CollectionUtils.isEmpty(users)) {
			for (User u : users) {
				if (!map.containsKey(u.getId())) {
					map.put(u.getId(), u);
				}
			}
		}
		if (map.containsKey(user.getId())) {
			entity.getLikedByUsers().remove(entity.getLikedByUsers().indexOf(user));
		} else {
			entity.getLikedByUsers().add(user);
			if (user.getId() != entity.getPostedBy().getId()) {
				String message = String.format(
						com.socialcrap.api.common.enums.Notification.NOTIFICATION_POST_LIKE.getMessage(),
						user.getFirstName(), entity.getPost());
				Notification notification = NotificationConverter.getEntityFromRequest(message, entity.getPostedBy());
				notificationDao.saveT(notification);
			}
		}
		postDao.updateT(entity);
		return true;
	}

	@Override
	public Boolean disLike(PostLikeRequest request) {
		if (request == null) {
			return null;
		}
		User user = userDao.getTById(request.getUserId());
		Post post = postDao.getTById(request.getPostId());
		String message = String.format(
				com.socialcrap.api.common.enums.Notification.NOTIFICATION_POST_DISLIKED.getMessage(),
				user.getFirstName(), post.getPost());
		Notification notification = NotificationConverter.getEntityFromRequest(message, post.getPostedBy());
		PostLike entity = null;
		if (postLikeDao.isExistByUserIdAndPostId(request.getUserId(), request.getPostId())) {
			entity = postLikeDao.getByUserIdAndPostId(request.getUserId(), request.getPostId());
			if (entity.getLikeOrDisLike().equals(LikeOrDisLike.DISLIKE)) {
				postLikeDao.deleteT(entity.getId());
				return true;
			}
			entity.setLikeOrDisLike(LikeOrDisLike.DISLIKE);
			postLikeDao.updateT(entity);
			notificationDao.saveT(notification);
			return true;
		}
		entity = PostLikeConverter.getEntityFromRequest(request);
		entity.setLikeOrDisLike(LikeOrDisLike.DISLIKE);
		postLikeDao.saveT(entity);
		notificationDao.saveT(notification);
		return true;
	}

	@Override
	public void validateId(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public PaginationResponse<List<PostResponse>> getAll(String token, Integer limit, Integer offset)
			throws SocialCrapException {

		loginTokenService.validateAdminToken(token);

		List<Post> entityList = postDao.getAllT(limit, PaginationUtil.getOffset(limit, offset));
		PaginationResponse<List<PostResponse>> responseList = null;
		if (!CollectionUtils.isEmpty(entityList)) {
			responseList = new PaginationResponse<>(getResponseList(entityList));
			return responseList;
		}
		return new PaginationResponse<>(new ArrayList<>());
	}

	@Override
	public Long getTotalCount() {
		return postDao.getTotalCount();
	}

	private void activateOrDeactivate(Long id, boolean status) throws SocialCrapException {
		Post entity = postDao.getTById(id);
		if (entity == null) {
			String message = String.format(SocialCrapResponseCode.POST_ID_NOT_FOUND.getMessage(), id);
			throw new SocialCrapException(message, SocialCrapResponseCode.POST_ID_NOT_FOUND.getCode(),
					HttpStatus.NOT_FOUND);
		}
		postDao.activateAndDeActivate(entity.getId(), status);
	}

	@Override
	public void activate(Long id) throws SocialCrapException {
		activateOrDeactivate(id, true);
	}

	@Override
	public void deactivate(Long id) throws SocialCrapException {
		activateOrDeactivate(id, false);
	}

	@Override
	public void addAll(List<PostRequest> requestList) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAll(List<PostRequest> requestList) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<PostResponse> getCustomPostForUser(Long userId) {
		if (userId == null) {
			return null;
		}
		User user = userDao.getTById(userId);
		List<User> friends = userService.getFriends(userId);
		if (!CollectionUtils.isEmpty(friends)) {
			List<Long> friendIds = friends.stream().map(User::getId).collect(Collectors.toList());
			if (!CollectionUtils.isEmpty(friendIds)) {
				List<Post> posts = postDao.getAllSortedByUserIds(friendIds, Sorting.DESC, BaseEntity.Table.CREATED);
				if (!CollectionUtils.isEmpty(user.getPost())) {
					posts.addAll(user.getPost());
				}
				if (!CollectionUtils.isEmpty(posts)) {
					Collections.sort(posts, (p1, p2) -> p2.getCreated().compareTo(p1.getCreated()));
					return getResponseList(posts);
				}
			}
		}
		List<Post> posts = postDao.getAllT();
		Collections.sort(posts, (p1, p2) -> p2.getCreated().compareTo(p1.getCreated()));
		return getResponseList(posts);
	}

	@Override
	public Boolean activateAndDeactivate(Long postId) {
		Post post = postDao.getTById(postId);
		if (post != null) {
			if (post.isActive()) {
				post.setActive(false);
			} else {
				post.setActive(true);
			}
			postDao.updateT(post);
		}
		return true;
	}
}
