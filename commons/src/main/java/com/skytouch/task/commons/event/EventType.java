package com.skytouch.task.commons.event;

/**
 * Ennumerates the event types that the task architecture will tolerate.
 *
 * @author Waldo Terry
 */
public enum EventType {
    //Requests a change to the definition of a product.
    PRODUCT_UPDATE,
    //Requests the creation of a new product.
    PRODUCT_CREATION,
    //Requests the deletion of a product.
    PRODUCT_DELETION,
    //Requests a list of all products.
    LIST_PRODUCTS,
    //Requests a specific product from the catalog.
    FIND_PRODUCT,
    //Requests the generation of the invoice for product restocking.
    GENERATE_INVOICES,
    //Generated on those cases where the processing of another event produces an error. This new error event will then
    //be sent to the queue in response.
    ERROR
}
