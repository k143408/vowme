package com.vowme.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vowme.model.Cause;
import com.vowme.model.Recommended;

public interface RecommendedRepository extends JpaRepository<Recommended, Long> {
	
	@Query("select distinct c from Recommended s JOIN s.user u JOIN s.cause c WHERE u.id = ?1")
	Page<Cause> getRecommendedCause(Long userId, Pageable pageable);

}
