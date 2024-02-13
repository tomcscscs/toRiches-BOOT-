package org.edupoll.app.service;

import java.time.LocalDateTime;

import org.edupoll.app.entity.Account;
import org.edupoll.app.entity.LoginLog;
import org.edupoll.app.repository.AccountRepository;
import org.edupoll.app.repository.LoginLogRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginLogService {

	private final LoginLogRepository loginLogRepository;
	private final AccountRepository accountRepository;

	public void createLog(String username) {
		Account account = accountRepository.findByUsername(username).get();
		LoginLog log = LoginLog.builder().account(account).loggedAt(LocalDateTime.now()).build();
		loginLogRepository.save(log);
	}
	
}
