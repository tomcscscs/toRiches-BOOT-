package org.edupoll.app.config.support;

import java.util.List;
import java.util.Random;

import org.edupoll.app.entity.TradeItem;
import org.edupoll.app.entity.TradeItemPriceLog;
import org.edupoll.app.repository.TradeItemPriceLogRepository;
import org.edupoll.app.repository.TradeItemRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BackgroundService {

	private final TradeItemRepository tradeItemRepository;
	private final TradeItemPriceLogRepository tradeItemPriceLogRepository;

	@Scheduled(cron = "*/30 * * * * ?")
	@Transactional
	public void updateTradeItemsPrice() {
		Random random = new Random();
		List<TradeItem> tradeItems = tradeItemRepository.findAll();

		List<TradeItemPriceLog> tradeItemPriceLogs = tradeItems.stream().map(t -> {
			if (Math.random() < 0.4)
				t.setTrend(!t.getTrend());
			// =============================================
			double start = 1.0;
			double end = 1.3;
			if (!t.getTrend()) {
				start = 0.7;
				end = 1.0;
			}
			int price = t.getPrice().getCurrent();

			double rate = random.nextDouble(start, end);
			int updatedPrice = (int) (price * rate) / 100 * 100;

			updatedPrice = updatedPrice > t.getPrice().getMaximum() ? t.getPrice().getMaximum()
					: (updatedPrice < t.getPrice().getMinimum() ? t.getPrice().getMinimum() : updatedPrice);

			t.getPrice().setCurrent(updatedPrice);

			return TradeItemPriceLog.builder().tradeItem(t).previousPrice(price).updatedPrice(updatedPrice).build();
		}).toList();

		tradeItemRepository.saveAll(tradeItems);
		tradeItemPriceLogRepository.saveAll(tradeItemPriceLogs);
		// log.info("Item price udpated. result : {}", tradeItems.size());
	}

}
