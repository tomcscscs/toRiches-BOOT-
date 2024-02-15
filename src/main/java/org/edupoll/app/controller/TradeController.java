package org.edupoll.app.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.edupoll.app.command.PurchaseItem;
import org.edupoll.app.command.SalesItem;
import org.edupoll.app.data.ChartDataSet;
import org.edupoll.app.entity.Account;
import org.edupoll.app.entity.TradeItem;
import org.edupoll.app.entity.TradeItemPriceLog;
import org.edupoll.app.repository.AccountRepository;
import org.edupoll.app.repository.TradeItemPriceLogRepository;
import org.edupoll.app.repository.TradeItemRepository;
import org.edupoll.app.service.TradeService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/trade")
@RequiredArgsConstructor
public class TradeController {
	private final AccountRepository accountRepository;
	private final TradeItemRepository tradeItemRepository;
	private final TradeItemPriceLogRepository tradeItemPriceLogRepository;
	private final TradeService tradeService;
	
	@GetMapping("/{id}")
	public String showSpecificTradeItem(@PathVariable Integer id, Model model, 
						Authentication authentication) {
		
		TradeItem item = tradeItemRepository.findById(id).get();
		List<TradeItemPriceLog> priceLogs = tradeItemPriceLogRepository.findTop5ByTradeItemOrderByUpdatedAtDesc(item);

		if(authentication != null) {
			Account authenticatedAccount = 
					accountRepository.findByUsername(authentication.getName()).get();
			model.addAttribute("authenticatedAccount", authenticatedAccount);
		}
		
		
		model.addAttribute("item", item);
		model.addAttribute("priceLogs", priceLogs);
		return "trade/item-detail";
	}
	
	
	@PostMapping("/{id}/purchase")
	public String proceedPurchaseItem(Authentication authentication , @PathVariable Integer id, 
			@Valid PurchaseItem cmd, BindingResult result) {
		if(result.hasErrors()) {
			return "trade/purchase-conflict";
		}
		boolean confirmed = tradeService.confirmPurchase(authentication.getName(), id, cmd.getQuantity());
		if(!confirmed) {
			return "trade/purchase-conflict";
		}
		return "redirect:/private/inventory";
	}
	
	
	@PutMapping("/{id}/sales")
	public String proceedSalesItem(Authentication authentication , @PathVariable Integer id, 
			@Valid SalesItem cmd, BindingResult result) {
		if(result.hasErrors()) {
			return "trade/sales-conflict";
		}
		boolean confirmed = tradeService.confirmSales(authentication.getName(), id, cmd.getQuantity());
		if(!confirmed) {
			return "trade/sales-conflict";
		}
		
		return "redirect:/private/inventory";
	}
	
	
	
	@GetMapping("/{id}/callback")
	public String showSpecificTradeItem(@PathVariable Integer id) {
		
		return "redirect:/trade/"+id;
	}
	

	

	@ExceptionHandler(NoSuchElementException.class)
	public String handleEntitiyNotFoundException() {
//		return "redirect:/";
		return "trade/item-not-found";
	}

}
