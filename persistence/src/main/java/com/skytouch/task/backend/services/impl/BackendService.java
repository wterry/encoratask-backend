package com.skytouch.task.backend.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.skytouch.task.commons.event.Event;
import com.skytouch.task.commons.event.EventType;
import com.skytouch.task.commons.event.services.Producer;
import com.skytouch.task.commons.utils.DateUtils;

import java.util.Date;

/**
 * Backend service superclass that will implement functionality common to all kafka service implementations.
 *
 * @author Waldo Terry
 */
public abstract class BackendService {

    protected Producer producer;

    /**
     * Standarized error handling for the queue message posting process in kafka implementations.
     * @param correlationalId
     * @param errorMsg
     */
    public void handleErrorResponse (String correlationalId, String errorMsg) {
        Event errorEvent = new Event(correlationalId, EventType.ERROR, DateUtils.toDateString(new Date()), errorMsg);

        try {
            producer.sendEvent(errorEvent);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }
}
