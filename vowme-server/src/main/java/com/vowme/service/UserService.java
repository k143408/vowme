package com.vowme.service;

import java.util.List;
import java.util.concurrent.Callable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vowme.dto.DateParam;
import com.vowme.dto.EOI;
import com.vowme.dto.EoiDTO;
import com.vowme.dto.LoginDTO;
import com.vowme.dto.PostExpressionOfInterestResult;
import com.vowme.dto.UserDTO;
import com.vowme.dto.VolunteerDTO;
import com.vowme.model.Approval;
import com.vowme.model.Boardcast;
import com.vowme.model.User;
import com.vowme.util.helper.FeedbackCustom;
import com.vowme.util.helper.MutliModel;


/**
 * The Interface UserService.
 */
public interface UserService {

	/**
	 * Gets the volunteers.
	 *
	 * @param pageable
	 *            the pageable
	 * @return the volunteers
	 */
	public Page<User> getVolunteers(Pageable pageable);

	/**
	 * Gets the volunteers.
	 *
	 * @return the volunteers
	 */
	public List<User> getVolunteers();

	/**
	 * Gets the organizer team.
	 *
	 * @param userId the user id
	 * @return the organizer team
	 */
	public List<User> getOrganizerTeam(Long userId);

	/**
	 * Save.
	 *
	 * @param user the user
	 * @return the user
	 */
	public User save(UserDTO user);

	/**
	 * Gets the volunteers.
	 *
	 * @param userId the user id
	 * @param pageable the pageable
	 * @return the volunteers
	 */
	public Page<User> getVolunteers(Long userId, Pageable pageable);

	/**
	 * Gets the user.
	 *
	 * @param userId the user id
	 * @return the user
	 */
	public User getUser(Long userId);

	/**
	 * Gets the cause count.
	 *
	 * @param userId the user id
	 * @return the cause count
	 */
	public Long getCauseCount(Long userId);

	/**
	 * Gets the backout cause count.
	 *
	 * @param userId the user id
	 * @return the backout cause count
	 */
	public Long getBackoutCauseCount(Long userId);

	/**
	 * Gets the participates cause count.
	 *
	 * @param userId the user id
	 * @return the participates cause count
	 */
	public Long getParticipatesCauseCount(Long userId);

	/**
	 * Gets the feedback.
	 *
	 * @param userId the user id
	 * @param pageable the pageable
	 * @return the feedback
	 */
	public Page<FeedbackCustom> getFeedback(Long userId, Pageable pageable);

	/**
	 * Gets the rank by id.
	 *
	 * @param userId the user id
	 * @return the rank by id
	 */
	public Float getRankById(Long userId);

	
	public Callable<Boardcast> joinCause(Long causeId, Long userId, String email);

	
	public Page<MutliModel> getPendingVolunteers(Long userId, Pageable pageable);

	
	public Boolean overrideApproval(Long userId, Long volId,Long causeId);

	
	public Approval approvalForCause(Long userId, Long volId, Long causeId, String comment);

	
	public List<User> getAllOrganizor();

	
	public VolunteerDTO registerVolunteer(LoginDTO account);

	public Approval EOIForCause(EoiDTO eoi, Long userId, Long causeId);

	public boolean isEOIExists(Long userId, Long causeId);

	public Boolean logHours(DateParam dateParam, Long userId, Long causeId);

	public PostExpressionOfInterestResult postFeedback(EOI eoi, Long userId, Long causeId);
	

}
