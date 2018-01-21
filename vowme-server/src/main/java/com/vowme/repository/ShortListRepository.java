package com.vowme.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vowme.model.Cause;
import com.vowme.model.Shortlist;

public interface ShortListRepository extends JpaRepository<Shortlist, Long> {

	@Query("select distinct c from Shortlist s JOIN s.user u JOIN s.cause c WHERE u.id = ?1")
	Page<Cause> getShortListCause(Long userId, Pageable pageable);
	
	@Query("select distinct s from Shortlist s JOIN s.user u JOIN s.cause c WHERE u.id = ?1 and c.id = ?2")
	List<Shortlist> findshortListUserAndCause(Long userId,Long causeId);


}
