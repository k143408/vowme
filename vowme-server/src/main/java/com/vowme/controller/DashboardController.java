package com.vowme.controller;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vowme.model.Cause;
import com.vowme.service.CauseService;


/**
 * The Class DashboardController.
 */
@RestController
@RequestMapping("api/dashboard")
public class DashboardController extends BaseController {

	/** The cause service. */
	@Autowired
	private CauseService causeService;

	/**
	 * Gets the.
	 *
	 * @param userId
	 *            the user id
	 * @param pageable
	 *            the pageable
	 * @return the callable
	 */
	@GetMapping("/cause/user/{userId}")
	public Callable<Page<Cause>> get(@PathVariable Long userId, @PageableDefault(size = 12) Pageable pageable) {
		return new Callable<Page<Cause>>() {
			@Override
			public Page<Cause> call() throws Exception {
				return causeService.getCauses(userId, pageable);
			}
		};
	}

	/**
	 * Gets the.
	 *
	 * @param causeId the cause id
	 * @return the callable
	 */
	@GetMapping("/cause/{causeId}")
	public Callable<Cause> get(@PathVariable Long causeId) {
		return new Callable<Cause>() {
			@Override
			public Cause call() throws Exception {
				return causeService.getCausesById(causeId);
			}
		};
	}
}
