package org.edupoll.app.controller;

import org.edupoll.app.command.NewAccount;
import org.edupoll.app.entity.Account;
import org.edupoll.app.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AccountController {

	private final AccountService accountService;

	
	@GetMapping("/signin")
	public String showAccountSignin(@RequestParam(required=false) String username, Model model) {
		if(username == null) {
			return "account/account-input-username";
		}else {
			model.addAttribute("username", username);
			return "account/account-input-password";
		}
	}
	
	
	
	
	
	@GetMapping("/register")
	public String showAccountRegister(Model model) {
		Account dummy = Account.builder().build();
		model.addAttribute("newAccount", dummy);

		return "account/account-register-form";
	}

	@PostMapping("/register")
	public String proceedAccountRegister(@ModelAttribute @Valid NewAccount cmd, BindingResult result, Model model) {
		if (result.hasErrors()) {
//			System.out.println(result.getFieldError("username"));
//			System.out.println(result.getFieldError("password"));
//			System.out.println(result.getFieldError("nickname"));
//			model.addAttribute("newAccount", cmd);
			return "account/account-register-form";
		}

		boolean isCreated = accountService.createNewAccount(cmd);
		if (!isCreated) {
			return "redirect:/register/conflict?username=" + cmd.getUsername();
		}

		return "redirect:/";
	}

	@GetMapping("/register/conflict")
	public String proceedAccountRegister(@RequestParam String username, Model model) {

		model.addAttribute("username", username);

		return "account/account-register-conflict";
	}
}
