package com.skytouch.task.backend.listeners;

import com.skytouch.task.backend.services.InventoryService;
import com.skytouch.task.backend.services.ProductService;
import com.skytouch.task.commons.event.EventType;
import com.skytouch.task.commons.model.Product;
import com.skytouch.task.commons.utils.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedHashMap;


/**
 * Listener set up to take messages left on a Kafka topic by the task front end service, from which database operations
 * will be triggered.
 *
 * @author Waldo Terry
 */
@Component
@AllArgsConstructor
@Slf4j
public class BackendListener {

    private final ProductService productService;
    private final InventoryService inventoryService;
    /**
     * Receives all messages from the producer at the front end and distributes them to the appropriate operations on the
     * backend to process the messages.
     *
     * @param event Event received from the kafka queue.
     */
    @KafkaListener(topics = {"${kafka.consumers.topicName}"}, containerFactory = "kafkaListenerContainerFactory")
    public void handleTaskMessage(LinkedHashMap<String, Object> event) {
        log.info("Event received!!");

        for (String key : event.keySet()) {
            log.info("Keys : " + key);
            log.info("Value: " + event.get(key));
        }

        String correlationalId = (String) event.get("correlationalId");
        EventType eventType = EventType.valueOf((String) event.get("eventType"));

        try {
            switch (eventType) {
                case PRODUCT_CREATION:
                    log.info("Creating a new product...");
                    Product toCreate = JsonUtils.getObjectFromJson((String) event.get("eventData"), Product.class);
                    productService.createProduct(toCreate);
                    break;
                case PRODUCT_DELETION:
                    log.info("Deleting an existing product...");
                    Product toDelete = JsonUtils.getObjectFromJson((String) event.get("eventData"), Product.class);
                    productService.deleteProduct(toDelete);
                    break;
                case PRODUCT_UPDATE:
                    log.info("Updating an existing product...");
                    Product toUpdate = JsonUtils.getObjectFromJson((String) event.get("eventData"), Product.class);
                    productService.updateProduct(toUpdate);
                    break;
                case FIND_PRODUCT:
                    log.info("Requesting one specific product...");
                    productService.findProduct((Integer) event.get("eventData"), correlationalId);
                    break;
                case LIST_PRODUCTS:
                    log.info("Requesting findAllProducts...");
                    productService.findAllProducts(correlationalId);
                    break;
                case GENERATE_INVOICES:
                    log.info("Generating invoices...");
                    inventoryService.generateInventoryRestockInvoice(correlationalId);
                    break;
                default:
                    //DISCARD UNKNOWN EVENT TYPE.
                    log.info("Unknown Event Type [" + eventType + "] received. Discarding...");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            log.error("Error reading received business data...");
        }

    }
}
