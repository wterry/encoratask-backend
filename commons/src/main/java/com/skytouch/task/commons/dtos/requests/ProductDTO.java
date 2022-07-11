package com.skytouch.task.commons.dtos.requests;

import lombok.Data;
import org.apache.commons.text.StringEscapeUtils;
import org.hibernate.validator.constraints.Length;

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
    @Length(max = 30, message="SKU only admits 30 characters.")
    private String sku;

    @NotNull(message = "Product description cannot be null.")
    @Length(max = 100, message = "Description only admits 100 characters.")
    //Product description. Must not be null.
    private String description;

    //Stock size for the product. Greater than or equal to 0.
    @Min(value = 0, message = "Inventory stock for the product cannot be less than 0.")
    @NotNull (message="Inventory stock cannot be null")
    private Integer inventoryStock;

    //Price to set for the product. Greater than 1 cent.
    @DecimalMin(value = "0.01", message = "Price ")
    @NotNull(message = "Product price cannot be null")
    private BigDecimal price;

    /**
     * Cleans text inputs, removing potentially malicious tags and limiting the resulting string to a valid size if it's
     * bigger.
     */
    public void sanitizeTextInputs()  {
        sku = StringEscapeUtils.escapeHtml4(sku);
        description = StringEscapeUtils.escapeHtml4(description);

        sku = sku.length() > 30? sku.substring(0, 30) : sku;
        description = description.length() > 100? description.substring(0,100) : description;
    }
}

