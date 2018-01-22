package com.vowme.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vowme.model.Cause;
import com.vowme.model.User;
import com.vowme.util.helper.FeedbackCustom;


/**
 * The Interface FeedbackService.
 */
public interface FeedbackService {

	/**
	 * Gets the user feedback.
	 *
	 * @param userId the user id
	 * @param pageable the pageable
	 * @return the user feedback
	 */
	Page<FeedbackCustom> getUserFeedback(Long userId, Pageable pageable);

	void saveUserFeedback(User findById, Cause causesById, String qualification);

}
