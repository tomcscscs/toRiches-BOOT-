package org.edupoll.app.entity.embeddable;

import jakarta.persistence.Embeddable;
import lombok.Data;


@Data
@Embeddable
public class Price {
	
	private Integer current;
	private Integer maximum;
	private Integer minimum;
	
}
