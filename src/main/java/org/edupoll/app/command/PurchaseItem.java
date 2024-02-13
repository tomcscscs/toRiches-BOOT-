package org.edupoll.app.command;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PurchaseItem {
	@NotNull
	private Integer itemId;
	
	@Min(value = 1)
	private Integer quantity;
	
}
