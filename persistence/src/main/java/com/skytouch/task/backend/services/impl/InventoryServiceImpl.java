package com.skytouch.task.backend.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.skytouch.task.backend.repositories.RestockInvoiceRepository;
import com.skytouch.task.backend.services.InventoryService;
import com.skytouch.task.commons.event.Event;
import com.skytouch.task.commons.event.EventType;
import com.skytouch.task.commons.event.services.Producer;
import com.skytouch.task.commons.model.RestockInvoice;
import com.skytouch.task.commons.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Implementation of the Inventory Service interface.
 *
 * @author Waldo Terry
 */
@Service
@Slf4j
public class InventoryServiceImpl extends BackendService implements InventoryService {

    private final RestockInvoiceRepository repository;

    public InventoryServiceImpl(RestockInvoiceRepository repository, Producer producer) {
        this.repository = repository;
        this.producer = producer;
    }
    @Override
    public List<RestockInvoice> generateInventoryRestockInvoice(String correlationalId) {
        List<RestockInvoice> response = repository.generateRestockInvoices(20);

        Event event = new Event(correlationalId, EventType.GENERATE_INVOICES, DateUtils.toDateString(new Date()), response);

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
