package org.edupoll.app.repository;

import java.util.List;

import org.edupoll.app.entity.TradeItem;
import org.edupoll.app.entity.TradeItemPriceLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeItemPriceLogRepository extends JpaRepository<TradeItemPriceLog, Long>{

	public List<TradeItemPriceLog> findByTradeItemOrderByUpdatedAtAsc(TradeItem tradeItem);
	public List<TradeItemPriceLog> findByTradeItemOrderByUpdatedAtDesc(TradeItem tradeItem);
	
	public List<TradeItemPriceLog> findByTradeItemIdOrderByUpdatedAtAsc(Integer tradeItemId);
	public List<TradeItemPriceLog> findByTradeItemIdOrderByUpdatedAtDesc(Integer tradeItemId);
	
	public List<TradeItemPriceLog> findTop5ByTradeItemOrderByUpdatedAtDesc(TradeItem tradeItem);
	public List<TradeItemPriceLog> findTop5ByTradeItemIdOrderByUpdatedAtDesc(Integer tradeItemId);
}
