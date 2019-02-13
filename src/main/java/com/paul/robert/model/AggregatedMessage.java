package com.paul.robert.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class AggregatedMessage {
	
	@Builder.Default private int freeMemory = 0;
	@Builder.Default private int totalMemory = 0;
	@Builder.Default private int numOfProcessors = 0;

	private String operationalStatus;
	private Long randomlyGeneratedNumber;
	
}