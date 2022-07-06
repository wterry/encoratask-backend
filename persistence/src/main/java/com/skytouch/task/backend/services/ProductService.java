package com.skytouch.task.backend.services;

import com.skytouch.task.commons.model.Product;

import java.util.List;

/**
 * Service interface to define the business logic needed to process the different kinds of <code>Product</code> related
 * events that can be read from the queue.
 *
 * @author Waldo Terry
 */
public interface ProductService {

    /**
     * Persists a new <code>Product</code> to the database. Will NOT generate a response message in the queue.
     *
     * @param product The <code>Product</code> to persist without an ID.
     * @return The persisted <code>Product</code>
     */
    Product createProduct(Product product);

    /**
     * Updates a database <code>Product</code> with the provided values. Will NOT generate a response message in the queue.
     *
     * @param product The final expected state of the <code>Product</code>.
     */
    void updateProduct(Product product);

    /**
     * Deletes the provided product. Will NOT generate a response message in the queue.
     *
     * @param product Product to delete.
     */
    void deleteProduct(Product product);

    /**
     * Lists existing products. This method will generate a response message in the queue whose business data will contain
     * the search result.
     *
     * @param correlationalId Given that this method operates under the assumption that there will be a corresponding response
     *                        event, this correlational id serves to log the execution chain of the original event that
     *                        triggered this response.
     * @return List of existing products.
     */
    List<Product> findAllProducts(String correlationalId);

    /**
     * Fetches a specific product from the database. This method will produce a response message in the queue whose business
     * data will contain the search result.
     * @param id The product ID to search for.
     * @param correlationalId Given that this method operates under the assumption that there will be a corresponding response
     *                        event, this correlational id serves to log the execution chain of the original event that
     *                        triggered this response.
     * @return The found product or <code>null</code> if it doesn't exist.
     */
    Product findProduct(Integer id, String correlationalId);
}
