package com.vowme.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.vowme.model.Cause;
import com.vowme.model.User;
import com.vowme.repository.ApprovalRepository;
import com.vowme.service.CauseService;
import com.vowme.service.UserService;

public class NotificationJob {

	@Autowired
	private CauseService causeService;

	@Autowired
	private UserService userService;

	@Autowired
	private ApprovalRepository approvalRepository;

	private boolean successFlag;

	@Scheduled(cron = "*/1000 * * * * *")
	public void notification() {

		List<Cause> allCause = causeService.getAllCause();

		allCause.parallelStream().forEach(cause -> {
			cause.getApprovals().parallelStream()
					.filter((app) -> isOverrided(app.getOverride()) && isDone(app.getIsApproved()))
					.forEach(approval -> {
						User user = approval.getUser();
						if (user.isVerified()) {
							user.getUserSkills().forEach(skill -> {
								cause.getCauseSkills().forEach(causeskill -> {
									if (causeskill.getSkill().equals(skill.getSkill())) {
										this.successFlag = true;
									}
								});
							});
							if (user.getRank() >= 3) {
								this.successFlag = true;
							}
						}
					});
		});

	}

	private boolean isOverrided(Byte override) {
		return override.equals(new Byte("0"));
	}

	private boolean isDone(Byte status) {
		return status.equals(new Byte("2"));
	}
}
