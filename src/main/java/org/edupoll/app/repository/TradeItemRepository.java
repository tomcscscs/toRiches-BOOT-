package org.edupoll.app.repository;

import org.edupoll.app.entity.TradeItem;
import org.edupoll.app.entity.TradeType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeItemRepository extends JpaRepository<TradeItem, Integer> {
	
	

}
