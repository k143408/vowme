package com.vowme.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vowme.dto.AccessTokenDTO;
import com.vowme.model.User;
import com.vowme.repository.UserRepository;

@RestController
@RequestMapping("oauth/token")
public class OAuthController extends BaseController  {
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping
	public AccessTokenDTO getToken(@RequestParam(name="username",required=false) String username, @RequestParam(name="providerkey",required=false) Long providerkey,@RequestParam(name="loginprovider",required=false) String provider){
		User user = null;
		if (StringUtils.hasText(username))
		 user = userRepository.findByUsername(username);
		else if (StringUtils.hasText(provider) && !ObjectUtils.isEmpty(providerkey) ){
			user = userRepository.findByProvider(provider,providerkey);
		}
		return new AccessTokenDTO(user == null ? "" : user.getId().toString());
	}
	
	
}
