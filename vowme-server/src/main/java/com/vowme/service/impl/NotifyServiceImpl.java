package com.vowme.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vowme.model.Cause;
import com.vowme.model.Team;
import com.vowme.model.TeamNotification;
import com.vowme.model.User;
import com.vowme.repository.TeamNotificationRepository;
import com.vowme.repository.TeamRepository;
import com.vowme.service.CauseService;
import com.vowme.service.NotifyService;
import com.vowme.service.UserService;
import com.vowme.util.StringUtil;

@Service("notifyService")
public class NotifyServiceImpl implements NotifyService {

	@Autowired
	private TeamNotificationRepository teamNotificationRepository;
	
	@Autowired
	private CauseService causeService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TeamRepository teamRepository;
	
	@Override
	public List<TeamNotification> getNotifications(String email) {
		return teamNotificationRepository.findByEmail(email);
	}

	@Override
	public TeamNotification makeItSeen(Long notifyId) {
		TeamNotification existingOne = teamNotificationRepository.findOne(notifyId);
		if (existingOne != null) {
			existingOne.setNotified(1);
			existingOne = teamNotificationRepository.save(existingOne);
		}
		return existingOne;
	}

	@Transactional
	@Override
	public TeamNotification action(TeamNotification teamNotification, Long teamMemberId) {
		if (teamNotification == null ) return teamNotification;  
			if (teamNotification.getStatus().intValue() == 1) {
            Map<String, Long> map = StringUtil.toMap(teamNotification.getParameters());
            	Long causeId = map.get("cause");
            	Cause cause = causeService.getCausesById(causeId);
            	User user = userService.getUser(teamMemberId);
            	teamRepository.save(new Team(cause,user));
            	teamNotificationRepository.save(teamNotification);
			}else {
				teamNotificationRepository.save(teamNotification);
			}
		
		return teamNotification;
	}


}
