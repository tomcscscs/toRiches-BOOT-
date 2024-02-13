package org.edupoll.app.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PurchaseLog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Account owner;
	
	@ManyToOne
	private TradeItem target;
	
	private Integer quantity;
	
	private Integer price;
	
	private LocalDateTime purchasedAt;
	
	
	@PrePersist
	public void prePersist() {
		purchasedAt = LocalDateTime.now();
	}
	
	
}










