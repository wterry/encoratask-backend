package com.skytouch.task.backend.services;

import com.skytouch.task.commons.model.RestockInvoice;

import java.util.List;

/**
 * Interface that defines methods required to handle inventory related events.
 *
 * @author Waldo Terry
 */
public interface InventoryService {

    /**
     * This method triggers the generation of inventory restock invoices. This method will produce a response message in
     * the queue whose business data contains the generated invoices (if any).
     *
     * @param correlationalId Unique string that identifies the execution chain to which the response event that this method
     *                        generates will belong to.
     * @return The list of generated invoices.
     */
    List<RestockInvoice> generateInventoryRestockInvoice(String correlationalId);
}
