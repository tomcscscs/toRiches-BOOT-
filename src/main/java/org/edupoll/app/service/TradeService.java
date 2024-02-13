package org.edupoll.app.service;

import org.edupoll.app.entity.Account;
import org.edupoll.app.entity.Inventory;
import org.edupoll.app.entity.PurchaseLog;
import org.edupoll.app.entity.SalesLog;
import org.edupoll.app.entity.TradeItem;
import org.edupoll.app.repository.AccountRepository;
import org.edupoll.app.repository.InventoryRepository;
import org.edupoll.app.repository.PurchaseLogRepository;
import org.edupoll.app.repository.SalesLogRepository;
import org.edupoll.app.repository.TradeItemRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TradeService {

	private final TradeItemRepository tradeItemRepository;
	private final AccountRepository accountRepository;
	private final InventoryRepository inventoryRepositoy;
	private final PurchaseLogRepository purchaseLogRepository;
	private final SalesLogRepository salesLogRepository;

	// 구매 확정 처리
	@Transactional
	public boolean confirmPurchase(String username, Integer tradeItemId, Integer quantitiy) {
		// - account 찾기 .. (balance 체크도 해야되고, balance 변경도 해야 되서)
		Account account = accountRepository.findByUsername(username).get();
		// - tradeItem 찾기 .. (찾아서 세이브 써먹어야되고.. inventory 의 평균단가도 변경해야되서)
		TradeItem tradeItem = tradeItemRepository.findById(tradeItemId).get();

		long remainBalance = account.getBalance();
		int currentPrice = tradeItem.getPrice().getCurrent();

		int confirmPrice = currentPrice * quantitiy;
		if (confirmPrice > remainBalance) {
			return false;
		}
		// - inventory 에서 찾아.. 있으면 update, 없으면 save
		Inventory inventory = inventoryRepositoy.findByOwnerAndTarget(account, tradeItem);
		if (inventory == null) {
			// 신규 구매
			Inventory inven = Inventory.builder().owner(account).target(tradeItem).total(quantitiy)
					.average((double) currentPrice).build();
			inventoryRepositoy.save(inven);
		} else {
			int oldTotal = inventory.getTotal();
			double oldAverage = inventory.getAverage();
			inventory.setTotal(oldTotal + quantitiy);
			inventory.setAverage((oldTotal * oldAverage + currentPrice * quantitiy) / (oldTotal + quantitiy));
			inventoryRepositoy.save(inventory);
		}
		// accounnt update / balance 조정
		account.setBalance(remainBalance - confirmPrice);
		accountRepository.save(account);
		// - purchaseLog 저장하기

		PurchaseLog purchaseLog = PurchaseLog.builder().owner(account).target(tradeItem)//
				.quantity(quantitiy).price(currentPrice).build();

		purchaseLogRepository.save(purchaseLog);

		return true;
	}

	// 판매 확정 처리
	public boolean confirmSales(String username, Integer tradeItemId, Integer quantitiy) {
		Account account = accountRepository.findByUsername(username).get();
		TradeItem tradeItem = tradeItemRepository.findById(tradeItemId).get();

		Inventory inventory = inventoryRepositoy.findByOwnerAndTarget(account, tradeItem);
		// - 실제 판매하고자 하는 아이템을 가지고 있어야 한다. (수량 역시 만족하여야함)
		
		if (inventory == null || inventory.getTotal() < quantitiy) {
			return false;
		}
		// - 물품 판매 후 대금은 유저의 잔액에 추가한다.
		int currentPrice = tradeItem.getPrice().getCurrent();
		int confirmSalesPrice = currentPrice * quantitiy;

		account.setBalance(account.getBalance() + confirmSalesPrice);
		accountRepository.save(account);
		// - 물품 판매후 보유 수량을 update 한다. (단 수량이 0이되었다면 삭제한다)
		inventory.setTotal(inventory.getTotal() - quantitiy);
		inventoryRepositoy.save(inventory);
		if (inventory.getTotal() <= 0) {
			// inventoryRepositoy.delete(inventory);
			inventoryRepositoy.deleteById(inventory.getId());
		}

		SalesLog log = SalesLog.builder().owner(account).target(tradeItem)//
				.quantity(quantitiy).price(currentPrice).build();
		salesLogRepository.save(log);
		return true;
	}

}
