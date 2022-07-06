package com.skytouch.task.commons.dtos.requests;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * DTO used to handle Product creation requests. It (and all DTOs) exist to decouple view definitions and requirements from
 * model specifications and restrictions.
 *
 * @author Waldo Terry
 */
@Data
public class ProductDTO {

    /*
    SKU for the product. Ideally should be unique but for the purposes of this task (and amateur volume testing) no unique
    constraint was set. Cannot be null.
     */
    @NotNull(message = "SKU cannot be null.")
    private String sku;

    @NotNull(message = "Product description cannot be null.")
    //Product description. Must not be null.
    private String description;

    //Stock size for the product. Greater than or equal to 0.
    @Min(value = 0, message = "Inventory size for the product cannot be less than 0.")
    private Integer inventoryStock;

    //Price to set for the product. Greater than 1 cent.
    @DecimalMin(value = "0.01", message = "Price ")
    private BigDecimal price;
}

