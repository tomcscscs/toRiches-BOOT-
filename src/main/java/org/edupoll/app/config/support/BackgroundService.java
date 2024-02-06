package org.edupoll.app.config.support;

import java.util.List;
import java.util.Random;

import org.edupoll.app.entity.TradeItem;
import org.edupoll.app.repository.TradeItemRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BackgroundService {

	private final TradeItemRepository tradeItemRepository;

	@Scheduled(cron = "*/15 * * * * ?")
	public void updateTradeItemsPrice() {
		Random random = new Random();
		List<TradeItem> tradeItems = tradeItemRepository.findAll();
		tradeItems.stream().forEach(t -> {
			int r = random.nextInt(0, 10);
			if (r < 4) {
				t.setTrend(!t.getTrend());
			}
			// =============================================
			double start = 0;
			double end = 0;
			if (t.getTrend()) {
				start = 1.0;
				end = 1.3;
			} else {
				start = 0.7;
				end = 1.0;
			}
			int current = t.getPrice().getCurrent();
			double rate = random.nextDouble(start, end);
			current *= rate;
			current = current / 100 * 100;

			if (current > t.getPrice().getMaximum()) {
				t.getPrice().setCurrent(t.getPrice().getMaximum());
			} else if (current < t.getPrice().getMinimum()) {
				t.getPrice().setCurrent(t.getPrice().getMinimum());
			} else {
				t.getPrice().setCurrent(current);
			}
//			tradeItemRepository.save(t);
		});

		tradeItemRepository.saveAll(tradeItems);
		
		log.info("Item price udpated. result : {}", tradeItems.size());
		
		/*
		 * for(TradeItem t : tradeItems) { t.getPrice().getCurrent(); }
		 */
	}

}
