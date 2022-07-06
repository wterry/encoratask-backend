package com.skytouch.task.commons.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Response object for Product related searches.
 *
 * @author Waldo Terry
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private Integer id;
    private String sku;
    private String description;
    private BigDecimal price;
    private Integer inventoryStock;
    private String lastModified;


}
