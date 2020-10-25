package com.socialcrap.api.common.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.socialcrap.api.common.converter.CommentConverter;
import com.socialcrap.api.common.converter.CommentLikeConverter;
import com.socialcrap.api.common.converter.NotificationConverter;
import com.socialcrap.api.common.converter.UserConverter;
import com.socialcrap.api.common.enums.LikeOrDisLike;
import com.socialcrap.api.common.enums.SocialCrapResponseCode;
import com.socialcrap.api.common.service.CommentService;
import com.socialcrap.api.exception.SocialCrapException;
import com.socialcrap.api.model.dao.CommentDao;
import com.socialcrap.api.model.dao.CommentLikeDao;
import com.socialcrap.api.model.dao.NotificationDao;
import com.socialcrap.api.model.dao.PostDao;
import com.socialcrap.api.model.dao.UserDao;
import com.socialcrap.api.model.entity.CommentEntity;
import com.socialcrap.api.model.entity.CommentLike;
import com.socialcrap.api.model.entity.Notification;
import com.socialcrap.api.model.entity.Post;
import com.socialcrap.api.model.entity.User;
import com.socialcrap.api.model.request.dto.CommentLikeRequest;
import com.socialcrap.api.model.request.dto.CommentRequest;
import com.socialcrap.api.model.response.dto.CommentResponse;
import com.socialcrap.api.model.response.dto.LikeAndDisLikeResponse;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentDao commentDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private PostDao postDao;
	@Autowired
	private CommentLikeDao commentLikeDao;
	@Autowired
	private NotificationDao notificationDao;

	@Override
	public List<CommentResponse> getAll() {
		return getResponseList(commentDao.getAllT());
	}

	@Override
	public CommentResponse getById(Long id) {
		return getResponse(commentDao.getTById(id));
	}

	@Override
	public Long add(CommentRequest request) {
		if (request == null) {
			return null;
		}
		if (!userDao.isExist(request.getUserId())) {
			return null;
		}
		if (!postDao.isExist(request.getPostId())) {
			return null;
		}
		CommentEntity entity = CommentConverter.getEntityFromRequest(request);
		if (entity == null) {
			return null;
		}
		User user = userDao.getTById(request.getUserId());
		Post post = postDao.getTById(request.getPostId());
		entity.setPost(post);
		entity.setCommentedBy(user);
		commentDao.saveT(entity);
		post.getComments().add(entity);
		postDao.updateT(post);
		String message = String.format(
				com.socialcrap.api.common.enums.Notification.NOTIFICATION_POST_COMMENT.getMessage(),
				user.getFirstName(), post.getPost());
		Notification notification = NotificationConverter.getEntityFromRequest(message, post.getPostedBy());
		notificationDao.saveT(notification);
		return entity.getId();
	}

	@Override
	public Boolean update(CommentRequest request, Long id) throws SocialCrapException {
		if (request == null) {
			return false;
		}
		if (commentDao.isExist(id)) {
			CommentEntity entity = commentDao.getTById(id);
			if (request.getUserId() != null) {
				if ((entity.getCommentedBy().getId()) != request.getUserId()) {
					throw new SocialCrapException(
							String.format(SocialCrapResponseCode.UN_AUTHORIZED.getMessage(), "to update this comment"),
							SocialCrapResponseCode.UN_AUTHORIZED.getCode(), HttpStatus.BAD_REQUEST);
				}
			}
			CommentConverter.updateData(request, entity);
			commentDao.updateT(entity);
			return true;
		}
		return false;
	}

	@Override
	public Boolean delete(Long id) {
		commentDao.deleteT(id);
		return true;
	}

	@Override
	public CommentResponse getResponse(CommentEntity request) {
		if (request == null) {
			return null;
		}
		CommentResponse response = CommentConverter.getResponseFromEntity(request);
		if (response == null) {
			return null;
		}
		response.setUser(UserConverter.getWrappedEntity(request.getCommentedBy()));
		List<CommentLike> commentLikedList = commentLikeDao.getAllLiked(request.getId());

		List<Long> userIdListLiked = null;
		List<User> usersLiked = null;
		if (!CollectionUtils.isEmpty(commentLikedList)) {
			userIdListLiked = commentLikedList.stream().map(CommentLike::getUserId).collect(Collectors.toList());
		}
		if (!CollectionUtils.isEmpty(userIdListLiked)) {
			usersLiked = userDao.getAllByIdList(userIdListLiked);
		}

		List<CommentLike> commentDisLikedList = commentLikeDao.getAllDisLiked(request.getId());
		List<Long> userIdListDisLiked = null;
		List<User> usersDisLiked = null;
		if (!CollectionUtils.isEmpty(commentDisLikedList)) {
			userIdListDisLiked = commentDisLikedList.stream().map(CommentLike::getUserId).collect(Collectors.toList());
		}
		if (!CollectionUtils.isEmpty(userIdListDisLiked)) {
			usersDisLiked = userDao.getAllByIdList(userIdListDisLiked);
		}

		LikeAndDisLikeResponse likeAndDisLikeResponse = new LikeAndDisLikeResponse();

		if (!CollectionUtils.isEmpty(usersLiked)) {
			likeAndDisLikeResponse.setNumberOfLikes(new Long(usersLiked.size()));
			likeAndDisLikeResponse.setLikedBy(UserConverter.getWrappedEntityList(usersLiked));
		}
		if (!CollectionUtils.isEmpty(usersDisLiked)) {
			likeAndDisLikeResponse.setNumberOfDisLikes(new Long(usersDisLiked.size()));
			likeAndDisLikeResponse.setDisLikedBy(UserConverter.getWrappedEntityList(usersDisLiked));
		}
		response.setLikeAndDisLikes(likeAndDisLikeResponse);
		return response;
	}

	@Override
	public List<CommentResponse> getResponseList(List<CommentEntity> requestList) {
		if (CollectionUtils.isEmpty(requestList)) {
			return new ArrayList<>();
		}
		List<CommentResponse> responseList = new ArrayList<>();
		responseList = requestList.stream().map(request -> getResponse(request)).collect(Collectors.toList());
		return responseList;
	}

	@Override
	public Boolean like(CommentLikeRequest request) {
		if (request == null) {
			return null;
		}
		CommentLike entity = null;
		User user = userDao.getTById(request.getUserId());
		CommentEntity comment = commentDao.getTById(request.getCommentId());
		String message = String.format(
				com.socialcrap.api.common.enums.Notification.NOTIFICATION_COMMENT_LIKE.getMessage(),
				user.getFirstName(), comment.getComment());
		Notification notification = NotificationConverter.getEntityFromRequest(message, comment.getCommentedBy());
		if (commentLikeDao.isExistByUserIdAndCommentId(request.getUserId(), request.getCommentId())) {
			entity = commentLikeDao.getByUserIdAndCommentId(request.getUserId(), request.getCommentId());
			if (entity.getLikeOrDisLike().equals(LikeOrDisLike.LIKE)) {
				commentLikeDao.deleteT(entity.getId());
				return true;
			}
			entity.setLikeOrDisLike(LikeOrDisLike.LIKE);
			commentLikeDao.updateT(entity);
			notificationDao.saveT(notification);
			return true;
		}
		entity = CommentLikeConverter.getEntityFromRequest(request);
		entity.setLikeOrDisLike(LikeOrDisLike.LIKE);
		commentLikeDao.saveT(entity);
		notificationDao.saveT(notification);
		return true;
	}

	@Override
	public Boolean disLike(CommentLikeRequest request) {
		if (request == null) {
			return null;
		}
		CommentLike entity = null;
		User user = userDao.getTById(request.getUserId());
		CommentEntity comment = commentDao.getTById(request.getCommentId());
		String message = String.format(
				com.socialcrap.api.common.enums.Notification.NOTIFICATION_COMMENT_DISLIKED.getMessage(),
				user.getFirstName(), comment.getComment());
		Notification notification = NotificationConverter.getEntityFromRequest(message, comment.getCommentedBy());
		if (commentLikeDao.isExistByUserIdAndCommentId(request.getUserId(), request.getCommentId())) {
			entity = commentLikeDao.getByUserIdAndCommentId(request.getUserId(), request.getCommentId());
			if (entity.getLikeOrDisLike().equals(LikeOrDisLike.DISLIKE)) {
				commentLikeDao.deleteT(entity.getId());
				return true;
			}
			entity.setLikeOrDisLike(LikeOrDisLike.DISLIKE);
			commentLikeDao.updateT(entity);
			notificationDao.saveT(notification);
			return true;
		}
		entity = CommentLikeConverter.getEntityFromRequest(request);
		entity.setLikeOrDisLike(LikeOrDisLike.DISLIKE);
		commentLikeDao.saveT(entity);
		notificationDao.saveT(notification);
		return true;
	}

	@Override
	public void validateId(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void activate(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deactivate(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addAll(List<CommentRequest> requestList) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAll(List<CommentRequest> requestList) {
		// TODO Auto-generated method stub

	}
}
