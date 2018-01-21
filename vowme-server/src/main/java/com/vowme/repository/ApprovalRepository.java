package com.vowme.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vowme.model.Approval;
import com.vowme.model.Cause;
import com.vowme.model.User;

public interface ApprovalRepository extends JpaRepository<Approval, Long>{
	
	
	@Query("SELECT distinct a FROM User u JOIN u.approvals a JOIN a.cause c WHERE u.id = ?1 and c.id = ?2")
	Approval getUserWithCause(Long volId, Long causeId);
	
	@Query("SELECT distinct c FROM User u JOIN u.approvals a JOIN a.cause c WHERE u.id = ?1 and a.isApproved in (0)")
	Page<Cause> getCauseExpressed(Long volId,Pageable pageable);
	
	@Query("SELECT distinct c FROM User u JOIN u.approvals a JOIN a.cause c WHERE u.id = ?1 and a.isApproved in (0,2)")
	Page<Cause> getApproval(Long userId,Pageable pageable);
	
	@Query("SELECT distinct u FROM User u JOIN u.approvals a JOIN a.cause c WHERE u.id = ?1 and c.id = ?2 and a.isApproved in (-1)")
	User getApprovalUserWithCause(Long volId, Long causeId);
	
}
