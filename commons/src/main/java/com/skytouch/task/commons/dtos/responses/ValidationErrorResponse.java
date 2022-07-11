package com.skytouch.task.commons.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO used to handle validation errors reported from the REST API.
 *
 * @author Waldo Terry
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorResponse {

    private String field;
    private String error;
}
