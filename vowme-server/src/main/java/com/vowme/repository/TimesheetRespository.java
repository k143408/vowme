package com.vowme.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vowme.model.Timesheet;

public interface TimesheetRespository extends JpaRepository<Timesheet, Long> {

}
