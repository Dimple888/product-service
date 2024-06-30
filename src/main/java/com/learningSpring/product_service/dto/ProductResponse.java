package com.learningSpring.product_service.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
	
	private String id;//from db i'm sending the id as well to ui
	private String name;
	private String description;
	private BigDecimal price;

}
