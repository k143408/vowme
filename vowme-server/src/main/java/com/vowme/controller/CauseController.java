package com.vowme.controller;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vowme.model.Cause;
import com.vowme.service.CauseService;
import com.vowme.util.helper.CauseShortDetail;

@RestController
@RequestMapping("api/cause/")
public class CauseController extends BaseController {

	@Autowired
	private CauseService causeService;
	
	@GetMapping("short/{userId}")
	public Callable<Page<CauseShortDetail>> getCausesShortDescriptionByUserId(@PathVariable Long userId,@PageableDefault(size = 12) Pageable pageable){
		return new Callable<Page<CauseShortDetail>>() {
			@Override
			public Page<CauseShortDetail> call() throws Exception {
				return causeService.getCausesShortDescription(userId, pageable);
			}
		};
	}
	
	@PostMapping
	public Callable<Cause> addCause(@RequestBody Cause cause){
		return new Callable<Cause>() {
			@Override
			public Cause call() throws Exception {
				return causeService.addCauseWithTeam(cause);
			}
		};
	}
}
