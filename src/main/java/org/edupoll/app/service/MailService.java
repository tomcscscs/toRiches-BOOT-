package org.edupoll.app.service;

import org.edupoll.app.entity.Account;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {
	private final JavaMailSender javaMailSender;
	private final TemplateEngine templateEngine;

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

	public void sendWelcomeMimeMessage(Account target) {

		MimeMessage message = javaMailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
			helper.setTo(target.getUsername());
			helper.setFrom("no-reply@gmail.com");
			message.setSubject("투리치스 환영한다.");

			Context ctx = new Context();
			ctx.setVariable("account", target);
			String text = templateEngine.process("mail/welcome-mail", ctx);
			helper.setText(text, true);

			/*
			 * String text ="<h2> 안녕하세요 투리치스입니다.</h2>"; text +=
			 * "<a href='http://192.168.4.46:8040/index'>투리치스 바로가기</a>";
			 * 
			 * helper.setText(text, true);
			 */

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
	
	public void sendTemporalPasswordMessage(String target, String temporalPassword) {

		SimpleMailMessage message = new SimpleMailMessage();

		message.setTo(target);
		message.setFrom("no-reply@gmail.com");
		message.setSubject("[투리치스] 비밀번호 임시 재발급");

		String text = "회원님이 계정 비밀번호가 " +temporalPassword+" 로 변경되었습니다.\n";
		text += "임시 비밀번호로 로그인 후 새로운 비밀번호를 설정하시는 걸 권장합니다.";
		message.setText(text);
		try {
			javaMailSender.send(message);
		} catch (Exception e) {
			log.warn("Fail to send mail. Cause : " + e.getMessage());
		}

	}
	
	

}
	

