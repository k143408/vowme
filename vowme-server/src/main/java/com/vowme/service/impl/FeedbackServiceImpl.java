/**
 * 
 */
package com.vowme.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vowme.model.Cause;
import com.vowme.model.Feedback;
import com.vowme.model.User;
import com.vowme.repository.FeedbackRepository;
import com.vowme.service.FeedbackService;
import com.vowme.util.helper.FeedbackCustom;

/**
 * @author Nisum017
 *
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {

	@Autowired
	FeedbackRepository feedbackRespository;

	@Override
	public Page<FeedbackCustom> getUserFeedback(Long userId, Pageable pageable) {
		return feedbackRespository.getFeedback(userId, pageable);
	}

	@Override
	public void saveUserFeedback(User user, Cause cause, String feedback) {
		feedbackRespository.save(new Feedback(feedback, cause, cause.getUser(), user)); 
	}

}
