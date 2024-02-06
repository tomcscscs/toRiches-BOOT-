package org.edupoll.app.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {
	private final JavaMailSender javaMailSender;

	public void sendWelcomMail(String target) {

		SimpleMailMessage message = new SimpleMailMessage();

		message.setTo(target);
		message.setFrom("no-reply@gmail.com");
		message.setSubject("[투리치스]환영합니다.");
		String text = "투리치스에 회원이 되신걸 환영합니다.\n";
		message.setText(target);

		try {
			javaMailSender.send(message);
		} catch (Exception e) {
			log.warn(e.getMessage());
		}

	}
	
	public void sendWelcomeMimeMessage(String target) {
		
		MimeMessage message = javaMailSender.createMimeMessage();
		
		
		try {
		MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8" );
		helper.setTo(target);
		helper.setFrom("no-reply@gmail.com");
		message.setSubject("투리치스 환영한다.");
		
		
		String text ="<h2> 안녕하세요 투리치스입니다.</h2>";
		text += "<a href='http://192.168.4.46:8040/index'>투리치스 바로가기</a>";
		
		helper.setText(text, true);
		
		
		javaMailSender.send(helper.getMimeMessage());
		} catch (Exception e) {
			
			log.warn(e.getMessage());
		}
		
		
		
		
		
		
		/*
		 * message.setTo(target); message.setFrom("no-reply@gmail.com");
		 * message.setSubject("[투리치스]환영합니다."); String text = "투리치스에 회원이 되신걸 환영합니다.\n";
		 * message.setText(target);
		 * 
		 * try { javaMailSender.send(message); } catch (Exception e) {
		 * log.warn(e.getMessage()); }
		 */
		
	}
	
}