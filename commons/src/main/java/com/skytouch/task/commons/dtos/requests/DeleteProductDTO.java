package com.skytouch.task.commons.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * DTO to request the deletion of a product.
 *
 * @author Waldo Terry
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteProductDTO {

    @NotNull(message = "Product ID to delete is mandatory.")
    private Integer id;
}
