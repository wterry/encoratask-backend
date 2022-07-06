package com.skytouch.task.backend;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.skytouch.task.backend.repositories.ProductRepository;
import com.skytouch.task.backend.services.InventoryService;
import com.skytouch.task.backend.services.ProductService;
import com.skytouch.task.commons.event.Event;
import com.skytouch.task.commons.event.services.Producer;
import com.skytouch.task.commons.model.Product;
import com.skytouch.task.commons.model.RestockInvoice;
import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersistenceApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
public class ProductTests {
	@Mock
	private Producer producer;

	@Autowired
	@InjectMocks
	private ProductService productService;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	@InjectMocks
	private InventoryService inventoryService;

	private static Product testProduct;

	@Test
	public void contextLoads() {
		Assertions.assertThat(productService).isNotNull();
		Assertions.assertThat(productRepository).isNotNull();
		Assertions.assertThat(inventoryService).isNotNull();
	}

	@Before
	public void initTestProduct() {
		if (testProduct == null) {
			testProduct = new Product();

			testProduct.setDescription("Test product created with JUnit");
			testProduct.setPrice(BigDecimal.TEN);
			testProduct.setSku("TESTSKU");
			testProduct.setInventoryStock(2);
		}
	}

	@Test
	public void createProduct() {
		testProduct = productService.createProduct(testProduct);
		Assertions.assertThat(testProduct.getId()).isNotNull();
		Assertions.assertThat(testProduct.getLastModified()).isNotNull();
	}

	@Test
	public void productUpdate() {
		testProduct.setPrice(BigDecimal.ONE);
		Date baseModified = testProduct.getLastModified();
		productService.updateProduct(testProduct);

		testProduct = productRepository.findOne(testProduct.getId());

		Assertions.assertThat(testProduct.getPrice()).isEqualByComparingTo(BigDecimal.ONE);
		Assertions.assertThat(testProduct.getLastModified()).isNotEqualTo(baseModified);
	}

	@Test
	public void findAllProducts() {
		MockitoAnnotations.initMocks(this);

		try {
			when(producer.sendReplyEvent(new Event())).thenReturn("mockCorrelationalId");

			List<Product> searchResults = productService.findAllProducts("mockCorrelationalId");

			Assertions.assertThat(searchResults.size()).isGreaterThanOrEqualTo(1);
		}
		catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void findAProduct() {
		MockitoAnnotations.initMocks(this);

		try {
			when(producer.sendReplyEvent(new Event())).thenReturn("mockCorrelationalId");

			Product  searchResults = productService.findProduct(testProduct.getId(), "mockCorrelationalId");

			Assertions.assertThat(searchResults).isNotNull();
			Assertions.assertThat(searchResults.getSku()).isEqualTo(testProduct.getSku());
		}
		catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void restockInventoryInvoices() {
		MockitoAnnotations.initMocks(this);

		try {
			when(producer.sendReplyEvent(new Event())).thenReturn("mockCorrelationalId");

			List<RestockInvoice> searchResults = inventoryService.generateInventoryRestockInvoice( "mockCorrelationalId");

			Assertions.assertThat(searchResults.size()).isGreaterThanOrEqualTo(1);
		}
		catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testElimination() {
		Integer originalId = testProduct.getId();
		productService.deleteProduct(testProduct);

		testProduct = productRepository.findOne(originalId);

		Assertions.assertThat(testProduct).isNull();
	}


}
