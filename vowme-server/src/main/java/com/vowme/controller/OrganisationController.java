package com.vowme.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vowme.model.User;
import com.vowme.service.CauseService;

@RestController
@RequestMapping("api/organisation/")
public class OrganisationController {

	@Autowired
	private CauseService causeService;

	@GetMapping("{id}")
	public User getOrganisation(@PathVariable("id") Long causeId) {
		return causeService.getCausesById(causeId).getUser();
	}
}
