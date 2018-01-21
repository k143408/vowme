package com.vowme.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vowme.model.Cause;
import com.vowme.model.User;
import com.vowme.util.helper.CauseShortDetail;
import com.vowme.util.helper.KeyValue;


/**
 * The Interface CauseService.
 */
public interface CauseService {

	/**
	 * Gets the causes.
	 *
	 * @param userId
	 *            the user id
	 * @param pageable
	 *            the pageable
	 * @return the causes
	 */
	Page<Cause> getCauses(Long userId, Pageable pageable);

	/**
	 * Gets the cause count.
	 *
	 * @param userId the user id
	 * @return the cause count
	 */
	Long getCauseCount(Long userId);

	/**
	 * Gets the backout cause count.
	 *
	 * @param userId the user id
	 * @return the backout cause count
	 */
	Long getBackoutCauseCount(Long userId);

	/**
	 * Gets the participates cause count.
	 *
	 * @param userId the user id
	 * @return the participates cause count
	 */
	Long getParticipatesCauseCount(Long userId);

	/**
	 * Gets the cause list only name.
	 *
	 * @param userId the user id
	 * @param pageable the pageable
	 * @return the cause list only name
	 */
	Page<KeyValue> getCauseListOnlyName(Long userId, Pageable pageable);

	/**
	 * Gets the causes by id.
	 *
	 * @param causeId the cause id
	 * @return the causes by id
	 */
	Cause getCausesById(Long causeId);

	
	Page<CauseShortDetail> getCausesShortDescription(Long userId, Pageable pageable);

	Cause addCauseWithTeam(Cause cause);
	
	Cause saveCause(Cause cause);
	
	List<Cause> getAllCause();

	Page<Cause> getShortListCause(Long userId, Pageable pageable);

	Page<Cause> getAccpectedCause(Long userId, Pageable pageable);

	Page<Cause> getRecommendedCause(Long userId, Pageable pageable);

	User getEOI(Long userId, Long causeId);

	Boolean addShortListCause(Long userId, Long causeId);

	Boolean deleteShortListCause(Long userId, Long causeId);

	Page<Cause> getExpressedinterestCause(Long userId, Pageable pageable);

	Page<Cause> getAllCause(Pageable pageable);
}
