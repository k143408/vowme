package com.vowme.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vowme.dto.DateParam;

@RestController
@RequestMapping("api/hours")
public class HoursController extends BaseController {

	
	@PostMapping("{userId}")
	public void postHours(@RequestBody DateParam params, @PathVariable("userId") Long userId, @PathVariable("causeId") Long causeId){
		String nullValue = null;
		
		if (null == null) return;
	}
}
