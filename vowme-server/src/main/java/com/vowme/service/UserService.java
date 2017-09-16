package com.vowme.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vowme.dto.UserDTO;
import com.vowme.model.User;
import com.vowme.util.helper.FeedbackCustom;


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
	
	

}
