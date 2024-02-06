package org.edupoll.app.controller;

import org.edupoll.app.command.ForgotAccount;
import org.edupoll.app.command.NewAccount;
import org.edupoll.app.entity.Account;
import org.edupoll.app.service.AccountService;
import org.edupoll.app.service.MailService;
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
	private final MailService mailService;
	
	
	@GetMapping("/signin")
	public String showAccountSignin(@RequestParam(required=false) String username, Model model) {
		if(username == null) {
			return "account/signin-username";
		}else {
			model.addAttribute("username", username);
			
			if(accountService.readAccountByUserName(username) == null) {
				model.addAttribute("notfound", true);
				return "account/signin-username";
			}
			return "account/signin-password";
		}
	}
	
	@GetMapping("/forgotpassword")
	public String showPasswordForgot() {
		return "account/forgotpass-username";
	}
	
	@PostMapping("/forgotpassword") 
	public String proceedPasswordForgot(ForgotAccount forgotAccount, Model model) {
		Account account = accountService.readAccountByUserName(forgotAccount.getUsername());
		if(account == null) {

			return "account/forgotpass-conflict";
		}
		String temporalPassword = accountService.updatePasswordForce(forgotAccount.getUsername());
		mailService.sendTemporalPasswordMessage(forgotAccount.getUsername(), temporalPassword);
		return "account/forgotpass-success";
	}
	
	
	
	
	@GetMapping("/register")
	public String showAccountRegister(Model model) {
		Account dummy = Account.builder().build();
		model.addAttribute("newAccount", dummy);

		return "account/register-account";
	}

	@PostMapping("/register")
	public String proceedAccountRegister(@ModelAttribute @Valid NewAccount cmd, BindingResult result, 
			Model model) {
		if (result.hasErrors()) {
			return "account/register-account";
		}

		boolean isCreated = accountService.createNewAccount(cmd);
		if (!isCreated) {
			return "redirect:/register/conflict?username=" + cmd.getUsername();
		}
		Account account = accountService.readAccountByUserName(cmd.getUsername());
		mailService.sendWelcomePreDefinedMessage(account);
		
		return "redirect:/";
	}
 
	@GetMapping("/register/conflict")
	public String proceedAccountRegister(@RequestParam String username, Model model) {

		model.addAttribute("username", username);

		return "account/register-conflict";
	}
}
