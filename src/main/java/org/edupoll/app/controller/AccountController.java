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

	private final AccountService accountService; // 여기에다 리포지토리가 아니라 컨틀로러에서 서비스로 연결되는 형태를 띈다.

	@GetMapping("/register")
	public String showAccountRegister() {
		return "/account/account-register-form";
	}

	@PostMapping("/register")
	public String proceedAccountRegister(@ModelAttribute @Valid NewAccount cmd, BindingResult result, Model model) {
		if (result.hasErrors()) {

			String emailAddress = cmd.getUsername();
			model.addAttribute("emailAddress", emailAddress);
			String nickname = cmd.getNickname();

			model.addAttribute("nickname", nickname);

			return "account/conflict-errorpage";

		}
		boolean isCreated = accountService.createNewAccount(cmd);
		if (!isCreated) {

		}

		return "redirect:/";

	}
}
