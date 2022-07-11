package com.skytouch.task.commons.dtos.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.text.StringEscapeUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Request DTO for product updates.
 *
 * @author Waldo Terry
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateProductDTO {

    @NotNull(message = "The product ID is mandatory for a successful update.")
    private Integer id;
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
    @Min(value = 0, message = "Inventory stock for the product cannot be less than 0.")
    @NotNull(message = "Inventory stock cannot be null.")
    private Integer inventoryStock;

    //Price to set for the product. Greater than 1 cent.
    @DecimalMin(value = "0.01", message = "Price ")
    @NotNull(message = "Product price cannot be null.")
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
