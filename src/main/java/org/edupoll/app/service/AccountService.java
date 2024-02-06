package org.edupoll.app.service;

import java.util.Optional;
import java.util.UUID;

import org.edupoll.app.command.NewAccount;
import org.edupoll.app.entity.Account;
import org.edupoll.app.repository.AccountRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AccountService {

	private final AccountRepository accountRepository;

	public Account readAccountByUserName(String username) {
		Optional<Account> optional = accountRepository.findByUsername(username);
		if (optional.isEmpty()) {
			return null;
		}
		return optional.get();

		// return accountRepository.findByUsername(username).orElse(null);
	}

	public boolean createNewAccount(NewAccount newAccount) {
		if (accountRepository.findByUsername(newAccount.getUsername()).isPresent()) {
			return false;
		}

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Account entity = Account.builder().username(newAccount.getUsername())//
				.password("{bcrypt}" + encoder.encode(newAccount.getPassword()))//
				.nickname(newAccount.getNickname()).build();
		accountRepository.save(entity);

		return true;
	}

	public String updatePasswordForce(String username) {

		Account found = accountRepository.findByUsername(username).get();
		String newPassword = UUID.randomUUID().toString().split("-")[0];

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		found.setPassword("{bcrypt}" + encoder.encode(newPassword));
		accountRepository.save(found);
		
		return newPassword; // 원래 비밀번호를 줘야한다. 
		
	}

}
