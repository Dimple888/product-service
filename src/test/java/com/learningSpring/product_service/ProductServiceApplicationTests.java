package com.learningSpring.product_service;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learningSpring.product_service.dto.ProductRequest;
import com.learningSpring.product_service.repository.ProductRepository;

@SpringBootTest
@Testcontainers // to let springboot know we are using test containers for the test cases
@AutoConfigureMockMvc // used with MockMvc
class ProductServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;// this provides a mock servlet env to make a req from test container to
							// controller endpoints and to receive responses
	
	@Autowired
	private ObjectMapper objectMapper;//POJO <> Json conversion betweeen pojo to json or viceversa
	
	@Autowired
	private ProductRepository productRepository;

	@Container // to let junit know this is a mongoDB docker Container it is not a local db
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {

		// to cnnct to the mongodb when container on random port fetch the uri and set
		// here dynamically
		dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Test
	void shouldCreateProduct() throws Exception {
		
		ProductRequest productRequest = getProductRequest();//building the ProductRequest whcih is input/RequestBody to controller
		String productReq = objectMapper.writeValueAsString(productRequest); //to convert to string
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
				.contentType(MediaType.APPLICATION_JSON)
				.content(productReq)).andExpect(status().isCreated()); //post mapping so status is created is checked
		
		Assertions.assertThat(productRepository.findAll().size() == 1); //size will be 1 after inserting our requestbody data
	}

	private ProductRequest getProductRequest() {

		return ProductRequest.builder().name("Iphone 13").description("Iphone").price(BigDecimal.valueOf(12345))
				.build();

	}

}
