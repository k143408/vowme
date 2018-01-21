package com.vowme.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vowme.dto.AccessDTO;
import com.vowme.dto.DateParam;
import com.vowme.dto.EOI;
import com.vowme.dto.PostExpressionOfInterestResult;
import com.vowme.model.Approval;
import com.vowme.model.Cause;
import com.vowme.model.User;
import com.vowme.service.CauseService;
import com.vowme.service.UserService;

@RestController
@RequestMapping("api/opportunity/")
public class OpportunityController extends BaseController {

	@Autowired
	private CauseService causeService;

	@Autowired
	private UserService userService;

	@GetMapping("shortlist/{id}")
	public Page<Cause> shortlist(@PathVariable("id") Long userId, @PageableDefault(size = 4) Pageable pageable) {
		return causeService.getShortListCause(userId, pageable);
	}

	@PostMapping("shortlist/{id}")
	public Boolean addShortlist(@RequestBody AccessDTO access, @PathVariable("id") Long userId) {
		return causeService.addShortListCause(userId, new Long(access.getId()));
	}

	@DeleteMapping("shortlist/{causeId}/{userId}")
	public Boolean addShortlist(@PathVariable("causeId") Long causeId, @PathVariable("userId") Long userId) {
		return causeService.deleteShortListCause(userId, causeId);
	}

	@GetMapping("accepted/{id}")
	public Page<Cause> accepted(@PathVariable("id") Long userId, @PageableDefault(size = 4) Pageable pageable) {
		return causeService.getAccpectedCause(userId, pageable);
	}

	@GetMapping("recommended/{id}")
	public Page<Cause> recommended(@PathVariable("id") Long userId, @PageableDefault(size = 4) Pageable pageable) {
		Page<Cause> recommendedCause = causeService.getRecommendedCause(userId, pageable);
		return recommendedCause.getTotalElements() == 0 ? causeService.getAllCause(pageable) : recommendedCause;
	}
	
	@GetMapping("expressedinterest/{id}")
	public Page<Cause> expressedinterest(@PathVariable("id") Long userId, @PageableDefault(size = 4) Pageable pageable) {
		return causeService.getExpressedinterestCause(userId, pageable);
	}

	@GetMapping("eoi/{userId}/{causeId}")
	public User eoi(@PathVariable("userId") Long userId, @PathVariable("causeId") Long causeId) {
		User user = causeService.getEOI(userId, causeId);
		return user == null ? userService.getUser(userId) : user;
	}

	@PostMapping("eoi/{userId}/{causeId}")
	public PostExpressionOfInterestResult generateEOI(@RequestBody EOI eoi, @PathVariable("userId") Long userId,
			@PathVariable("causeId") Long causeId) {
		if (!userService.isEOIExists(userId, causeId)) {
			Approval eoiForCause = userService.EOIForCause(eoi.getEoi(), userId, causeId);
			if (eoiForCause != null) {
				return new PostExpressionOfInterestResult("Created");
			} else {
				return new PostExpressionOfInterestResult("LIMIT_REACHED");
			}
		} else {
			return new PostExpressionOfInterestResult("APPLICATION_EXISTS");
		}
	}
	
	@PostMapping("log/{userId}/{causeId}")
	public Boolean log(@RequestBody DateParam dateParam, @PathVariable("userId") Long userId,
			@PathVariable("causeId") Long causeId) {
		return userService.logHours(dateParam,userId,causeId);	
	}

}
