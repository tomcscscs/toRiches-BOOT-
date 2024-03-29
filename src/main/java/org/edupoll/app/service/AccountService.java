package org.edupoll.app.service;

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

	public boolean createNewAccount(NewAccount newAccount) {
		if (accountRepository.findByUsername(newAccount.getUsername()).isPresent()) {
			return false;
		}

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Account entity = Account.builder().username(newAccount.getUsername())//
				.password(encoder.encode(newAccount.getPassword()))//
				.nickname(newAccount.getNickname()).build();
		
		accountRepository.save(entity);

		return true;
	}
	
}


