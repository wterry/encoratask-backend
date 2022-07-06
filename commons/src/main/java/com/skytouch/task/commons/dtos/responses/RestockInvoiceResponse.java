package com.skytouch.task.commons.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Response DTO for inventory restock invoices.
 *
 * @author Waldo Terry
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestockInvoiceResponse {

    private Integer id;
    private String sku;
    private String description;
    private Integer missingStock;
    private BigDecimal restockPrice;
}
