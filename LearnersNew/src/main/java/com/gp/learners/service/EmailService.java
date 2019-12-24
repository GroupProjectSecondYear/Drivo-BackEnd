package com.gp.learners.service;

import java.util.Properties;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import com.gp.learners.config.EmailConfig;
import com.gp.learners.entities.mapObject.PaymentEmailBodyMap;


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
		
		//Gmail Mail Config 
		Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "true");
		
		return mailSender;
	}
	
	public String setUpEmailInstance(String from ,String to ,String subject,PaymentEmailBodyMap message) {
		
		JavaMailSenderImpl mailSender=setUpEmailSender();
		
		//Add HTML for EmailBody
		Boolean flag=false;
		try {
			MimeMessage mailMessage = mailSender.createMimeMessage();
			String htmlMsg = setCoursePaymentHtmlBody(message);
			mailMessage.setSubject(subject);
	        MimeMessageHelper helper;
	        helper = new MimeMessageHelper(mailMessage, true);
	        helper.setFrom(from);
	        helper.setTo(to);
	        helper.setText(htmlMsg, true);
	        mailSender.send(mailMessage);
	        
	        flag=true;
		} catch (Exception e) {
			flag=false;
			System.out.println("Mail Service Error");
		}
		
		if(flag) {
			return "success";
		}
		return "notsuccess";
		
		
//		SimpleMailMessage mailMessage = new SimpleMailMessage();
//		mailMessage.setFrom(from);
//		mailMessage.setTo(to);
//		mailMessage.setSubject(subject);
//		mailMessage.setText(message);
		
		
		//send email
//		mailSender.send(mailMessage);
//		return "success";
	}
	
	private String setCoursePaymentHtmlBody(PaymentEmailBodyMap message){
		String htmlMsg = 
				"<!DOCTYPE html>\r\n" + 
				"<html>\r\n" + 
				"<head>\r\n" + 
				"	<title>Drivo Learners Payment</title>\r\n" + 
				"</head>\r\n" + 
				"<body>\r\n" + 
				"	<h1 align=\"center\"><u>Drivo Learners</u></h1>\r\n" + 
				"	<hr>\r\n" + 
				"	<p></p>\r\n" + 
				"	<pre>Package	:"+message.getTitle()+"</pre>\r\n" + 
				"	<pre>Amount		:(Rs)"+ message.getAmount()+"</pre>\r\n" + 
				"	<pre>Date		:"+message.getDate()+"</pre>\r\n" + 
				"<hr>\r\n" + 
				"	<small align=\\\"center\\\">Thank You.</small>"+
				"</body>\r\n" + 
				"</html>";
		
		return htmlMsg;
	}
}
