package com.vowme.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vowme.model.Email;

public interface EmailRespository extends JpaRepository<Email, Long>{

}
