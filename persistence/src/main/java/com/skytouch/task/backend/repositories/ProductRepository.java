package com.skytouch.task.backend.repositories;

import com.skytouch.task.commons.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Standard JPA repository for the <code>Product</code> entity. No custom queries needed here.
 *
 * @author Waldo Terry
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {


}
