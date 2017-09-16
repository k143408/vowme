package com.vowme.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vowme.dto.UserDTO;
import com.vowme.model.User;
import com.vowme.repository.UserRepository;
import com.vowme.service.CauseService;
import com.vowme.service.FeedbackService;
import com.vowme.service.UserService;
import com.vowme.util.DateUtils;
import com.vowme.util.helper.FeedbackCustom;

@Service("Userservice")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CauseService causeService;

	@Autowired
	private FeedbackService feedbackService; 
	
	@Override
	public Page<User> getVolunteers(Pageable pageable) {
		return userRepository.getVolunteers(pageable);
	}

	@Override
	public List<User> getVolunteers() {
		return userRepository.getVolunteers();
	}

	@Override
	public List<User> getOrganizerTeam(Long userId) {
		return userRepository.findTeamByOwnerId(userId);
	}

	@Override
	public User save(UserDTO userDto) {
		User user = userRepository.findOne(userDto.getUserId());

		user.getUserInfo().setAboutMe(userDto.getAboutMe());
		user.getUserInfo().setAddress(userDto.getAddress());
		user.getUserInfo().setCity(userDto.getCity());
		user.getUserInfo().setContactNumber1(userDto.getPhoneNumber());
		user.getUserInfo().setZipcode(userDto.getZipCode());
		user.setEmail(userDto.getEmail());
		user.setFirstname(userDto.getFirstName());
		user.setLastname(userDto.getLastName());
		user.setUpdatedAt(DateUtils.getCurrentTime());
		user.getUserInfo().setUpdatedAt(DateUtils.getCurrentTime());
		userRepository.save(user);

		return user;
	}

	@Override
	public Page<User> getVolunteers(Long userId, Pageable pageable) {
		return userRepository.getVolunteersByUserId(userId, pageable);
	}

	@Override
	public User getUser(Long userId) {
		return userRepository.findOne(userId);
	}

	@Override
	public Long getCauseCount(Long userId) {
		return causeService.getCauseCount(userId);
	}

	@Override
	public Long getBackoutCauseCount(Long userId) {
		return causeService.getBackoutCauseCount(userId);
	}

	@Override
	public Long getParticipatesCauseCount(Long userId) {
		return causeService.getParticipatesCauseCount(userId);
	}

	@Override
	public Page<FeedbackCustom> getFeedback(Long userId, Pageable pageable) {
		return feedbackService.getUserFeedback(userId,pageable);
	}

	@Override
	public Float getRankById(Long userId) {
		return userRepository.getOne(userId).getRank();
	}

}
