package com.vowme.service.impl;

import java.util.concurrent.Callable;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.vowme.model.Boardcast;
import com.vowme.model.Cause;
import com.vowme.model.Email;
import com.vowme.repository.BoardcastRepository;
import com.vowme.repository.EmailRespository;
import com.vowme.service.NotificationService;

@Service("notificationService")
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private JavaMailSender emailSender;
	
	@Autowired
	private BoardcastRepository boardcastRepository;

	@Autowired
	private EmailRespository emailRepository;


	@Override
	public Callable<Boardcast> notify(Cause cause, String to, String subject, String text) {
		return () -> {
			Email email = null;
			try {
				MimeMessage message = emailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(message, true);
				helper.setTo(to);
				helper.setText(text, true);
				helper.setSubject(subject);
				Runnable runnable = () -> {
				emailSender.send(message);
				};
				new Thread(runnable).start();
				email = new Email(text, to, new Byte("1").byteValue(), subject);
			} catch (MessagingException me) {
				email = new Email(text, to, new Byte("0").byteValue(), subject);
			}
			
			emailRepository.save(email);
			
			Boardcast boardcast = new Boardcast(cause, email);
			email.addBoardcast(boardcast);

			boardcastRepository.save(boardcast);

			return boardcast;
		};
	}

}
