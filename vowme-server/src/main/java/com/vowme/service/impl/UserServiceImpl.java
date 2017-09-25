package com.vowme.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vowme.dto.UserDTO;
import com.vowme.model.Approval;
import com.vowme.model.Boardcast;
import com.vowme.model.Cause;
import com.vowme.model.Participate;
import com.vowme.model.User;
import com.vowme.repository.ApprovalRepository;
import com.vowme.repository.BoardcastRepository;
import com.vowme.repository.ParticipateRepository;
import com.vowme.repository.UserRepository;
import com.vowme.service.CauseService;
import com.vowme.service.FeedbackService;
import com.vowme.service.NotificationService;
import com.vowme.service.UserService;
import com.vowme.util.DateUtils;
import com.vowme.util.StringUtil;
import com.vowme.util.helper.FeedbackCustom;
import com.vowme.util.helper.MutliModel;

@Service("Userservice")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CauseService causeService;

	@Autowired
	private FeedbackService feedbackService;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private BoardcastRepository boardcastRepository;

	@Autowired
	private ApprovalRepository approvalRepository;

	@Autowired
	private ParticipateRepository participateRepository;

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

	@Transactional
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
		return feedbackService.getUserFeedback(userId, pageable);
	}

	@Override
	public Float getRankById(Long userId) {
		return userRepository.getOne(userId).getRank();
	}

	@Override
	public Callable<Boardcast> joinCause(Long causeId, Long userId, String email) {
		Cause cause = causeService.getCausesById(causeId);
		User organizer = this.getUser(userId);
		User user = userRepository.findByEmail(email);

		return notificationService.notify(cause, user == null ? email : user.getEmail(),
				String.format("Join Team of %s", cause.getName()), getInventationContent(organizer, cause));

	}

	private String getInventationContent(User user, Cause cause) {
		return String.format("%s wants you to join '%s'. Kindly reponse by signing in dashboard", user.getFullName(),
				cause.getName());
	}

	private String getRejectionContent(User user, Cause cause, String comments) {
		return String.format("%s has reject you from join '%s'. <br/> Rejection Reason is given below : <br/> %s",
				user.getFullName(), cause.getName(), comments);
	}

	@Override
	public Page<MutliModel> getPendingVolunteers(Long userId, Pageable pageable) {
		return userRepository.getPendingVolunteersByUserId(userId, pageable);
	}

	@Override
	public Boolean overrideApproval(Long userId, Long volId, Long causeId) {
		Approval approval = approvalRepository.getUserWithCause(volId, causeId);
		if (approval != null) {
			approval.setOverride(new Byte("1"));
			approval.setActionby(userRepository.findById(userId));
			approvalRepository.save(approval);
			return true;
		}
		return false;
	}

	@Transactional
	@Override
	public Approval approvalForCause(Long userId, Long volId, Long causeId, String comments) {
		Approval approval = approvalRepository.getUserWithCause(volId, causeId);
		if (approval != null) {
			User volunteer = approval.getUser();
			Cause cause = approval.getCause();
			User user = userRepository.findById(userId);
			Byte status = StringUtil.isEmptyOrNull(comments) ? new Byte("1") : new Byte("0");
			approval.setIsApproved(status);
			approval.setActionby(user);
			approval.setDescription(comments);
			if (!StringUtil.isEmptyOrNull(comments)) {
				Participate participate = new Participate(volunteer, cause);
				cause.addParticipate(participate);
				participateRepository.save(participate);
			}
			approvalRepository.save(approval);
			try {
				notificationService
						.notify(cause, volunteer.getEmail(), String.format("Notification from %s", cause.getName()),
								StringUtil.isEmptyOrNull(comments) ? getRejectionContent(user, cause, comments) : getSucessfullNoticfication(user, cause))
						.call();
			} catch (Exception e) {
			}

		}
		return Optional.ofNullable(approval).orElse(new Approval());
	}

	private String getSucessfullNoticfication(User user, Cause cause) {
		return String.format("%s has accepted your request for joining '%s'.", user.getFullName(), cause.getName());
	}

	@Override
	public List<User> getAllOrganizor() {
		// TODO Auto-generated method stub
		return null;
	}
}
