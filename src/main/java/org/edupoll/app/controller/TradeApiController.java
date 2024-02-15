package org.edupoll.app.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.edupoll.app.data.ChartDataSet;
import org.edupoll.app.data.TradeItemDataSet;
import org.edupoll.app.entity.TradeItem;
import org.edupoll.app.entity.TradeItemPriceLog;
import org.edupoll.app.repository.TradeItemPriceLogRepository;
import org.edupoll.app.repository.TradeItemRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
@RequestMapping("/trade/api")
public class TradeApiController {
	private final TradeItemPriceLogRepository tradeItemPriceLogRepository;
	private final TradeItemRepository tradeItemRepository;
	
	@GetMapping("/pricelog/{id}")
	@ResponseBody
	public ChartDataSet handlePriceLogApi(@PathVariable Integer id) {

		List<TradeItemPriceLog> priceLogs = tradeItemPriceLogRepository.findByTradeItemIdOrderByUpdatedAtAsc(id);

		List<Integer> updatePrices = priceLogs.stream().map(t -> t.getUpdatedPrice()).toList();
		List<LocalDateTime> updatedAts = priceLogs.stream().map(t -> t.getUpdatedAt()).toList();

		return new ChartDataSet(updatePrices, updatedAts);
	}
	
	@GetMapping("/latest/{id}")
	@ResponseBody 
	public TradeItemDataSet handleLatestTradeItemApi(@PathVariable Integer id) {
		TradeItem item = tradeItemRepository.findById(id).get();
		List<TradeItemPriceLog> logs =tradeItemPriceLogRepository.findTop5ByTradeItemIdOrderByUpdatedAtDesc(id);
		
		return new TradeItemDataSet(item, logs);
		
	}
	
}



