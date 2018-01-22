package com.vowme.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vowme.model.Cause;
import com.vowme.util.helper.CauseShortDetail;
import com.vowme.util.helper.KeyValue;


/**
 * The Interface CauseRepository.
 */
@Repository
public interface CauseRepository extends JpaRepository<Cause, Long>, JpaSpecificationExecutor<Cause> {

	/**
	 * Gets the cause by user id.
	 *
	 * @param userId the user id
	 * @param pageable the pageable
	 * @return the cause by user id
	 */
	@Query("SELECT distinct c FROM Cause c JOIN c.user u WHERE u.id =?1")
	Page<Cause> getCauseByUserId(Long userId, Pageable pageable);


	/**
	 * Gets the cause short description by user id.
	 *
	 * @param userId the user id
	 * @param pageable the pageable
	 * @return the cause short description by user id
	 */
	@Query("SELECT new com.vowme.util.helper.CauseShortDetail(c.id,c.name,c.description,c.registrationdate,c.registrationdeadline,c.info) FROM Cause c JOIN c.user u WHERE u.id =?1")
	Page<CauseShortDetail> getCauseShortDescriptionByUserId(Long userId, Pageable pageable);
	
	
	/**
	 * Gets the cause count.
	 *
	 * @param userId the user id
	 * @return the cause count
	 */
	@Query("SELECT count(distinct c) FROM Cause c LEFT JOIN c.participates p LEFT JOIN c.backouts b JOIN p.user u WHERE u.id =?1")
	Long getCauseCount(Long userId);

	/**
	 * Gets the backout cause count.
	 *
	 * @param userId the user id
	 * @return the backout cause count
	 */
	@Query("SELECT count(distinct c) FROM Cause c JOIN c.backouts b JOIN b.user u WHERE u.id =?1")
	Long getBackoutCauseCount(Long userId);
	
	/**
	 * Gets the participates cause count.
	 *
	 * @param userId the user id
	 * @return the participates cause count
	 */
	@Query("SELECT count(distinct c) FROM Participate p JOIN p.cause c JOIN p.user u WHERE u.id =?1")
	Long getParticipatesCauseCount(Long userId);

	/**
	 * Gets the cause names.
	 *
	 * @param userId the user id
	 * @param pageable the pageable
	 * @return the cause names
	 */
	@Query("SELECT new com.vowme.util.helper.KeyValue(c.id,c.name) FROM Cause c JOIN c.participates p JOIN p.user u WHERE u.id =?1")
	Page<KeyValue> getCauseNames(Long userId, Pageable pageable);
	
	
}
