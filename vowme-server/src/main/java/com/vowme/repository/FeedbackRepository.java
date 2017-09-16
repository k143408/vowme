package com.vowme.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vowme.model.Feedback;
import com.vowme.util.helper.FeedbackCustom;


/**
 * The Interface FeedbackRepository.
 */
@Repository("feedbackRepository")
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

	/**
	 * Gets the feedback.
	 *
	 * @param userId the user id
	 * @param pageable the pageable
	 * @return the feedback
	 */
	@Query("SELECT new com.vowme.util.helper.FeedbackCustom(f.id,f.feedback, f.cause.name ,f.commenter.firstname, f.commenter.lastname) FROM Feedback f JOIN f.receiver r WHERE r.id =?1")
	Page<FeedbackCustom> getFeedback(Long userId, Pageable pageable);

}
