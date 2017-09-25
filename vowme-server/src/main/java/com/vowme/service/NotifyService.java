package com.vowme.service;

import java.util.List;

import com.vowme.model.TeamNotification;

public interface NotifyService {

	List<TeamNotification> getNotifications(String email);

	TeamNotification makeItSeen(Long notifyId);

	TeamNotification action(TeamNotification teamNotification, Long userId);

}
