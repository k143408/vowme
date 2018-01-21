package com.vowme.controller;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vowme.dto.LoginDTO;
import com.vowme.dto.VolunteerDTO;
import com.vowme.service.UserService;

@RestController
@RequestMapping("api/account/")
public class AccountController extends BaseController {

	@Autowired
	private UserService userService;

	@PostMapping("register-volunteer")
	public VolunteerDTO getRegister(@RequestBody LoginDTO account) {

		return userService.registerVolunteer(account);
	}

}
