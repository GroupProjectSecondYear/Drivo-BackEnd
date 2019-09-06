package com.gp.learners.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import com.gp.learners.config.EmailConfig;

@Service
public class EmailService {
	
	@Autowired
	private EmailConfig emailConfig;
	
	//create mail sender
	private JavaMailSenderImpl setUpEmailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost(emailConfig.getHost());
		mailSender.setPort(emailConfig.getPort());
		mailSender.setUsername(emailConfig.getUsername());
		mailSender.setPassword(emailConfig.getPassword());
		
		return mailSender;
	}
	
	public String setUpEmailInstance(String from ,String to ,String subject,String message) {
		JavaMailSenderImpl mailSender=setUpEmailSender();
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(from);
		mailMessage.setTo(to);
		mailMessage.setSubject(subject);
		mailMessage.setText(message);
		
		
		//send email
		mailSender.send(mailMessage);
		return "success";
	}
}
