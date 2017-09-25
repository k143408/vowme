package com.vowme.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vowme.model.Boardcast;

public interface BoardcastRepository extends JpaRepository<Boardcast, Long> {

}