package org.edupoll.app.controller;

import java.util.List;

import org.edupoll.app.entity.Account;
import org.edupoll.app.entity.Inventory;
import org.edupoll.app.repository.AccountRepository;
import org.edupoll.app.repository.InventoryRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/private")
@RequiredArgsConstructor
public class PrivateController {

	private final AccountRepository accountRepository;
	private final InventoryRepository inventoryRepository;

	@GetMapping("/inventory")
	public String showInventory(Authentication authentication, Model model) {
		Account account = accountRepository.findByUsername(authentication.getName()).get();
		model.addAttribute("account", account);
		List<Inventory> invetory = inventoryRepository.findByOwner(account);
		model.addAttribute("inventory", invetory);

		return "private/inventory";
	}
}
