package com.vowme.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vowme.model.Team;


public interface TeamRepository extends JpaRepository<Team, Long>{

}
