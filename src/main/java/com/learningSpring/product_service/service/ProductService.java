package com.learningSpring.product_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.learningSpring.product_service.dto.ProductRequest;
import com.learningSpring.product_service.dto.ProductResponse;
import com.learningSpring.product_service.model.Product;
import com.learningSpring.product_service.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor//instead of creating constructor for productRepository we can add RequiredArgsConstructor at compile time it will create constructor for us here it will create for productRepository
@Slf4j
public class ProductService {
	
	private final ProductRepository productRepository;
	
	public void createProduct(ProductRequest productRequest) {
		
		Product product = Product.builder()
				.name(productRequest.getName())
				.description(productRequest.getDescription())
				.price(productRequest.getPrice())
				.build();
		productRepository.save(product);
		log.info("product {} is saved",product.getId());
	}

	public List<ProductResponse> getAllProducts() {
		
		List<Product> products = productRepository.findAll();
		//return products.stream().map(product -> mapToProductResponse(product)).toList();
		
		return products.stream().map(this :: mapToProductResponse).toList(); //instead of lambda expression we can use method references as well
	}

	private ProductResponse mapToProductResponse(Product product) {
		return ProductResponse.builder()
				.id(product.getId())
				.name(product.getName())
				.description(product.getDescription())
				.price(product.getPrice()).build();
	}

}
