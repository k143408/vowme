package com.vowme.controller;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vowme.model.Approval;
import com.vowme.model.User;
import com.vowme.service.CauseService;
import com.vowme.service.UserService;
import com.vowme.util.helper.FeedbackCustom;
import com.vowme.util.helper.KeyValue;
import com.vowme.util.helper.MutliModel;

/**
 * The Class VolunteerController.
 */
@RestController
@RequestMapping("api/volunteer/")
public class VolunteerController extends BaseController {

	/** The user serivce. */
	@Autowired
	private UserService userService;

	/** The cause service. */
	@Autowired
	private CauseService causeService;

	/**
	 * Gets the.
	 *
	 * @param pageable
	 *            the pageable
	 * @return the callable
	 */
	@GetMapping
	public Callable<Page<User>> get(@PageableDefault(size = 12) Pageable pageable) {
		return new Callable<Page<User>>() {
			@Override
			public Page<User> call() throws Exception {
				return userService.getVolunteers(pageable);
			}
		};
	}

	/**
	 * Gets the by user id.
	 *
	 * @param userId
	 *            the user id
	 * @param pageable
	 *            the pageable
	 * @return the by user id
	 */
	@GetMapping("{userId}")
	public Callable<Page<User>> getByUserId(@PathVariable Long userId, @PageableDefault(size = 10) Pageable pageable) {
		return new Callable<Page<User>>() {
			@Override
			public Page<User> call() throws Exception {
				return userService.getVolunteers(userId, pageable);
			}
		};
	}

	@GetMapping("pending/{userId}")
	public Callable<Page<MutliModel>> getByUserIdAndCauseId(@PathVariable Long userId,
			@PageableDefault(size = 100) Pageable pageable) {
		return new Callable<Page<MutliModel>>() {
			@Override
			public Page<MutliModel> call() throws Exception {
				return userService.getPendingVolunteers(userId, pageable);

			}
		};
	}

	/**
	 * Gets the cause name by user id.
	 *
	 * @param userId
	 *            the user id
	 * @param pageable
	 *            the pageable
	 * @return the cause name by user id
	 */
	@GetMapping("cause/name/{userId}")
	public Callable<Page<KeyValue>> getCauseNameByUserId(@PathVariable Long userId,
			@PageableDefault(size = 10) Pageable pageable) {
		return new Callable<Page<KeyValue>>() {
			@Override
			public Page<KeyValue> call() throws Exception {
				return causeService.getCauseListOnlyName(userId, pageable);
			}
		};
	}

	/**
	 * Gets the cause by user id.
	 *
	 * @param userId
	 *            the user id
	 * @return the cause by user id
	 */
	@GetMapping("cause/{userId}")
	public Callable<Long> getCauseByUserId(@PathVariable Long userId) {
		return new Callable<Long>() {
			@Override
			public Long call() throws Exception {
				return userService.getCauseCount(userId);
			}
		};
	}

	/**
	 * Gets the backout cause by user id.
	 *
	 * @param userId
	 *            the user id
	 * @return the backout cause by user id
	 */
	@GetMapping("cause/backout/{userId}")
	public Callable<Long> getBackoutCauseByUserId(@PathVariable Long userId) {
		return new Callable<Long>() {
			@Override
			public Long call() throws Exception {
				return userService.getBackoutCauseCount(userId);
			}
		};
	}

	/**
	 * Gets the participates cause by user id.
	 *
	 * @param userId
	 *            the user id
	 * @return the participates cause by user id
	 */
	@GetMapping("cause/doing/{userId}")
	public Callable<Long> getParticipatesCauseByUserId(@PathVariable Long userId) {
		return new Callable<Long>() {
			@Override
			public Long call() throws Exception {
				return userService.getParticipatesCauseCount(userId);
			}
		};
	}

	/**
	 * Gets the details.
	 *
	 * @param userId
	 *            the user id
	 * @param pageable
	 *            the pageable
	 * @return the details
	 */
	@GetMapping("detail/{userId}")
	public Callable<Page<User>> getDetails(@PathVariable Long userId, @PageableDefault(size = 10) Pageable pageable) {
		return new Callable<Page<User>>() {
			@Override
			public Page<User> call() throws Exception {
				return userService.getVolunteers(userId, pageable);
			}
		};
	}

	/**
	 * Gets the feedback.
	 *
	 * @param userId
	 *            the user id
	 * @param pageable
	 *            the pageable
	 * @return the feedback
	 */
	@GetMapping("feedback/{userId}")
	public Callable<Page<FeedbackCustom>> getFeedback(@PathVariable Long userId,
			@PageableDefault(size = 10) Pageable pageable) {
		return new Callable<Page<FeedbackCustom>>() {
			@Override
			public Page<FeedbackCustom> call() throws Exception {
				return userService.getFeedback(userId, pageable);
			}
		};
	}

	@GetMapping("override/{volId}")
	public Callable<Boolean> overrideApproval(@RequestParam Long userId, @PathVariable Long volId,
			@RequestParam Long causeId) {
		return new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return userService.overrideApproval(userId, volId, causeId);
			}
		};
	}

	@GetMapping("approval/{volId}")
	public Callable<Approval> approvalForCause(@RequestParam Long userId, @PathVariable Long volId,
			@RequestParam Long causeId, @RequestParam(required = false, defaultValue = "") String comment) {
		return () -> {
			return userService.approvalForCause(userId, volId, causeId, comment);
		};
	}

}
