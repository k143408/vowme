/**
 * 
 */
package com.vowme.controller;

import java.util.List;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vowme.model.TeamNotification;
import com.vowme.service.NotifyService;

/**
 * @author Nisum017
 *
 */
@RestController
@RequestMapping("api/notify/")
public class NotifyController extends BaseController {

	@Autowired
	private NotifyService notifyService;
	
	@GetMapping
	public Callable<List<TeamNotification>> getStatus(@RequestParam String email) {
		return () -> {
			return notifyService.getNotifications(email);
		};
	}
	
	@GetMapping("seen")
	public Callable<TeamNotification> makeItSeen(@RequestParam Long id) {
		return () -> {
			return notifyService.makeItSeen(id);
		};
	}
	
	@PostMapping("action")
	public Callable<TeamNotification> action(@RequestBody TeamNotification teamNotification, @RequestParam Long userid) {
		return () -> {
			return notifyService.action(teamNotification,userid);
		};
	}
}
