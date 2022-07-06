package com.skytouch.task.commons.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A stock response DTO that allows rest services to have a standarized response structure to handle both correct and incorrect
 * responses without changing the response makeup itself.
 *
 * @author Waldo Terry
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private Integer statusCode;
    private String message;
    private Object content;
}
