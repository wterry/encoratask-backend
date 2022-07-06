package com.skytouch.task.backend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.skytouch.task.backend.listeners.BackendListener;
import com.skytouch.task.backend.services.InventoryService;
import com.skytouch.task.backend.services.ProductService;
import com.skytouch.task.commons.event.EventType;
import com.skytouch.task.commons.model.Product;
import com.skytouch.task.commons.utils.DateUtils;
import com.skytouch.task.commons.utils.JsonUtils;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = PersistenceApplication.class)
@SpringBootTest
public class BackendListenerTests {

    @Mock
    private ProductService productService;

    @Mock
    private InventoryService inventoryService;

    private LinkedHashMap<String, Object> testEvent;

    private Product testProduct;

    @InjectMocks
    @Autowired
    private BackendListener listener;

    @Before
    public void initResources() {
        testEvent = new LinkedHashMap<>();

        testEvent.put("correlationalId", "Test_Id");
        testEvent.put("sendTime", DateUtils.toDateString(new Date()));
        //Event type to be assigned by each test.
        //Event data to be assigned by each test.

        testProduct = new Product();

        testProduct.setId(1);
        testProduct.setInventoryStock(1);
        testProduct.setDescription("Placeholder product");
        testProduct.setPrice(BigDecimal.ONE);
        testProduct.setSku("Placeholder SKU");
    }

    @Test
    public void contextLoad() {
        Assertions.assertThat(listener).isNotNull();
    }

    @Test
    public void testListenerCreation() {
        MockitoAnnotations.initMocks(this);

        String eventData = null;

        try {
            eventData = JsonUtils.getJsonFromObject(testProduct);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        testEvent.put("eventType", EventType.PRODUCT_CREATION.name());
        testEvent.put("eventData", eventData);

        when(productService.createProduct(testProduct)).thenReturn(null);
        listener.handleTaskMessage(testEvent);
    }

    @Test
    public void testListenerUpdate() {
        MockitoAnnotations.initMocks(this);

        String eventData = null;

        try {
            eventData = JsonUtils.getJsonFromObject(testProduct);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        testEvent.put("eventType", EventType.PRODUCT_UPDATE.name());
        testEvent.put("eventData", eventData);

//        when(productService.updateProduct(testProduct)
        listener.handleTaskMessage(testEvent);
    }

    @Test
    public void testListenerDelete() {
        MockitoAnnotations.initMocks(this);

        String eventData = null;

        try {
            eventData = JsonUtils.getJsonFromObject(testProduct);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        testEvent.put("eventType", EventType.PRODUCT_DELETION.name());
        testEvent.put("eventData", eventData);

//        when(productService.updateProduct(testProduct)
        listener.handleTaskMessage(testEvent);
    }

    @Test
    public void testListenerFindAll() {
        MockitoAnnotations.initMocks(this);

        testEvent.put("eventType", EventType.LIST_PRODUCTS.name());
        testEvent.put("eventData", null);

        listener.handleTaskMessage(testEvent);
    }

    @Test
    public void testListenerFindOne() {
        MockitoAnnotations.initMocks(this);

        Integer eventData = 1;

        testEvent.put("eventType", EventType.FIND_PRODUCT.name());
        testEvent.put("eventData", eventData);

        listener.handleTaskMessage(testEvent);
    }
}
