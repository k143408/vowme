package com.vowme.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vowme.model.TeamNotification;

public interface TeamNotificationRepository extends JpaRepository<TeamNotification, Long>{

	
	List<TeamNotification> findByEmail(String email);
	
	

}
