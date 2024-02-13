package org.edupoll.app.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.edupoll.app.data.ChartDataSet;
import org.edupoll.app.entity.TradeItem;
import org.edupoll.app.entity.TradeItemPriceLog;
import org.edupoll.app.repository.TradeItemPriceLogRepository;
import org.edupoll.app.repository.TradeItemRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/trade")
@RequiredArgsConstructor
public class TradeController {

	private final TradeItemRepository tradeItemRepository;
	private final TradeItemPriceLogRepository tradeItemPriceLogRepository;

	@GetMapping("/{id}")
	public String showSpecificTradeItem(@PathVariable Integer id, Model model) {
		TradeItem item = tradeItemRepository.findById(id).get();
		
		List<TradeItemPriceLog> priceLogs = tradeItemPriceLogRepository.findTop5ByTradeItemOrderByUpdatedAtDesc(item);

		model.addAttribute("item", item);
		model.addAttribute("priceLogs", priceLogs);
		return "trade/item-detail";
	}

	@GetMapping("/api/pricelog/{id}") // ajax
	@ResponseBody
	public ChartDataSet handlePriceLogApi(@PathVariable Integer id) {

		List<TradeItemPriceLog> priceLogs = tradeItemPriceLogRepository.findByTradeItemIdOrderByUpdatedAtAsc(id);

		List<Integer> updatePrices = priceLogs.stream().map(t -> t.getUpdatedPrice()).toList();
		List<LocalDateTime> updatedAts = priceLogs.stream().map(t -> t.getUpdatedAt()).toList();

		return new ChartDataSet(updatePrices, updatedAts);
	}

	@ExceptionHandler(NoSuchElementException.class)
	public String handleEntitiyNotFoundException() {
//		return "redirect:/";
		return "trade/item-not-found";
	}

	@GetMapping("/main")
	public String showLogListAll(Model model) {
		List<TradeItemPriceLog> itemLog = tradeItemPriceLogRepository.findAll();
		model.addAttribute("loglist", itemLog );
		
		return "trade/item-main";
	}
	
	@GetMapping("/{id}/callback")
	public String showSpecificTradeItem(@PathVariable Integer id) {
		
		return "redirect:/trade/" + id;
	}

}
