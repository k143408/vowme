package com.vowme.controller;

import java.util.List;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vowme.dto.UserDTO;
import com.vowme.model.User;
import com.vowme.service.UserService;


/**
 * The Class UserController.
 */
@RestController
@RequestMapping("/api/user/")
public class UserController extends BaseController {

	/** The user service. */
	@Autowired
	private UserService userService;
	
	/**
	 * Gets the teams by user id.
	 *
	 * @param userId the user id
	 * @return the teams by user id
	 */
	@GetMapping("team/{userId}")
	public Callable<List<User>> getTeamsByUserId(@PathVariable Long userId){
		return new Callable<List<User>>() {
			@Override
			public List<User> call() throws Exception {
				return userService.getOrganizerTeam(userId);
			}
		};
	}
	
	/**
	 * Gets the rank by user id.
	 *
	 * @param userId the user id
	 * @return the rank by user id
	 */
	@GetMapping("rank/{userId}")
	public Callable<Float> getRankByUserId(@PathVariable Long userId){
		return new Callable<Float>() {
			@Override
			public Float call() throws Exception {
				return userService.getRankById(userId);
			}
		};
	}
	
	/**
	 * Gets the details.
	 *
	 * @param userId the user id
	 * @return the details
	 */
	@GetMapping("{userId}")
	public Callable<User> getDetails(@PathVariable Long userId) {
		return new Callable<User>() {
			@Override
			public User call() throws Exception {
				return userService.getUser(userId);
			}
		};
	}
	
	/**
	 * Post.
	 *
	 * @param user the user
	 * @return the callable
	 */
	@PostMapping
	public Callable<User> post(@RequestBody UserDTO user) {
		return new Callable<User>() {
			@Override
			public User call() throws Exception {
				return userService.save(user);
			}
		};
	}
}
