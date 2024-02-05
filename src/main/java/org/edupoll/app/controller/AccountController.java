package org.edupoll.app.controller;

import org.edupoll.app.command.NewAccount;
import org.edupoll.app.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AccountController {

	private final AccountService accountService;
	
	@GetMapping("/register")
	public String showAccountRegister() {
		return "/account/account-register-form";
	}
	
	
	
	@PostMapping("/register")
	public String proceedAccountRegister(@ModelAttribute @Valid NewAccount cmd,
			BindingResult result, Model model) {
		if(result.hasErrors()) {
			// ....
			
		}
		boolean isCreated = accountService.createNewAccount(cmd);
		if(!isCreated) {
			// ......
		}
		
		
		return "redirect:/";
		
	}
}












