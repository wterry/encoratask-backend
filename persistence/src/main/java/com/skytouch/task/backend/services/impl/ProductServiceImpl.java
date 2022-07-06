package com.skytouch.task.backend.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.skytouch.task.backend.repositories.ProductRepository;
import com.skytouch.task.backend.services.ProductService;
import com.skytouch.task.commons.event.Event;
import com.skytouch.task.commons.event.EventType;
import com.skytouch.task.commons.event.services.Producer;
import com.skytouch.task.commons.model.Product;
import com.skytouch.task.commons.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Implementation of the Product service interface.
 *
 * @author Waldo Terry
 */
@Service
@Slf4j
public class ProductServiceImpl extends BackendService implements ProductService {
    private final ProductRepository repository;


    public ProductServiceImpl (Producer producer, ProductRepository repository) {
        this.producer = producer;
        this.repository = repository;
    }

    @Override
    public Product createProduct(Product product) {
        product.setLastModified(new Date());
        return repository.save(product);
    }

    @Override
    public void updateProduct(Product product) {
        product.setLastModified(new Date());
        repository.save(product);
    }

    @Override
    public void deleteProduct(Product product) {
        repository.delete(product);
    }

    @Override
    public List<Product> findAllProducts(String correlationalId) {
        List<Product> response = repository.findAll();

        log.info("Found " + response.size() + " products!");
        Event event = new Event(correlationalId, EventType.LIST_PRODUCTS, DateUtils.toDateString(new Date()), response);

        try {
            producer.sendReplyEvent(event);
        } catch (JsonProcessingException e) {
            log.error("Json processing error generated");
            e.printStackTrace();
            handleErrorResponse(correlationalId, "Error marshalling event data into JSON");
        }

        return response;
    }

    @Override
    public Product findProduct(Integer id, String correlationalId) {
        Product response = repository.findOne(id);

        Event event = new Event(correlationalId, EventType.FIND_PRODUCT, DateUtils.toDateString(new Date()), response);

        try {
            producer.sendReplyEvent(event);
        } catch (JsonProcessingException e) {
            log.error("Json processing error generated");
            e.printStackTrace();
            handleErrorResponse(correlationalId, "Error marshalling event data into JSON");
        }

        return response;
    }
}
