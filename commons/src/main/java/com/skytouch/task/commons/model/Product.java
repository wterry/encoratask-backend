package com.skytouch.task.commons.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Model class that represents the persistent structure of products that will be persisted to the database.
 *
 * @author Waldo Terry
 */
@Data
@Entity
@Table(name = "Products")
public class Product {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "sku", nullable = false)
    private String sku;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "inventoryStock")
    private Integer inventoryStock;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "lastModified")
    private Date lastModified;

    @Column(name = "price")
    private BigDecimal price;

}
