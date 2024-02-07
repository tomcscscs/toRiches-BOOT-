package org.edupoll.app.data;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChartDataSet {
	private List<Integer> data;
	private List<LocalDateTime> labels;
}
