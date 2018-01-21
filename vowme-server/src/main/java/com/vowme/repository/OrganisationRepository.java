package com.vowme.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vowme.model.Organisation;

public interface OrganisationRepository extends JpaRepository<Organisation, Long> {

}
