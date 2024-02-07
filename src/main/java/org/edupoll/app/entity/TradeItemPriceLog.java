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

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TradeItemPriceLog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private TradeItem tradeItem;
	
	private Integer previousPrice;
	private Integer updatedPrice;
	
	private LocalDateTime updatedAt;
	
	
	
	@PrePersist
	public void prePersist() {
		updatedAt = LocalDateTime.now();
	}
	
	
	
}
