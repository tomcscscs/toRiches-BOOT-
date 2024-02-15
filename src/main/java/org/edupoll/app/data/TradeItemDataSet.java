package org.edupoll.app.data;

import java.util.List;

import org.edupoll.app.entity.TradeItem;
import org.edupoll.app.entity.TradeItemPriceLog;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TradeItemDataSet {
	private TradeItem tradeItem;
	private List<TradeItemPriceLog> latestLog;
}
