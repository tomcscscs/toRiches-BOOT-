package org.edupoll.app.repository;

import java.util.List;

import org.edupoll.app.entity.Account;
import org.edupoll.app.entity.Inventory;
import org.edupoll.app.entity.TradeItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository 
				extends JpaRepository<Inventory, String>{

	
	public Inventory findByOwnerAndTarget(Account owner, TradeItem target);
	
	public List<Inventory> findByOwner(Account owner);
	
}
