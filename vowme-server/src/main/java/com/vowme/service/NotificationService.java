package com.vowme.service;

import java.util.concurrent.Callable;

import com.vowme.model.Boardcast;
import com.vowme.model.Cause;

public interface NotificationService {
	
	public Callable<Boardcast> notify(Cause cause, String to, String subject, String text);
	
		
}
