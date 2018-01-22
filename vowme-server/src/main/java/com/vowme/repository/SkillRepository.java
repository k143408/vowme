package com.vowme.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vowme.model.Skill;

public interface SkillRepository extends JpaRepository<Skill, Long> {

}
