package com.learningSpring.product_service.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(value = "product")//to this product as mongodb document use @Document of mongodb mapping import
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Product {
	
	@Id
	private String id;
	private String name;
	private String description;
	private BigDecimal price;
	

}
