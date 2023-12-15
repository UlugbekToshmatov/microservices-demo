package com.example.productservice;

import com.example.productservice.controllers.ProductController;
import com.example.productservice.dto.ProductRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
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
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
import java.util.Objects;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
* The purpose of creating these tests is to integrate automated tests against microservices'
* controller methods rather than testing them manually with Postman
*/

@SpringBootTest
@Testcontainers		// this annotation is required to integrate tests with container(s)
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	//	This field is declared to use container of docker image "mongo" with version: 4.4.2
	@Container
	final static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.4.2"));

	// This field is to make requests to controller methods
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ProductController productController;


	//	This method is to set the uri of the container as datasource address, which is running in docker
	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry propertyRegistry) {
		propertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Test
	void shouldCreateProduct() throws Exception {
		ProductRequest product = getProductRequest();
		String jsonObj = objectMapper.writeValueAsString(product);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/product")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonObj))
			.andExpect(status().isCreated());

		Assertions.assertEquals(1, Objects.requireNonNull(productController.getAll().getBody()).size());
		Assertions.assertEquals(0, mongoDBContainer.getEnv().size());	// why not 1???
		Assertions.assertTrue(mongoDBContainer.isRunning());
	}

	private ProductRequest getProductRequest() {
		return ProductRequest.builder()
			.name("Nokia")
			.description("6230i")
			.price(BigDecimal.valueOf(99.99))
			.build();
	}

}
