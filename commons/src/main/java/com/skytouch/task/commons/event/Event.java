package com.skytouch.task.commons.event;

import com.skytouch.task.commons.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

/**
 * Standard event object to communicate between the components of the task architecture.
 *
 * @author Waldo Terry
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    //Used to build and identify chains of event hops for logging and tracking purposes.
    private String correlationalId;
    //event type. Will help determine which actions a handler needs to take upon receiving an event.
    private EventType eventType;
    //Date the event was first sent at.
    private String sendTime;
    //Body of the event, the actual business data.
    private Object eventData;

    /**
     * This constructor creates an event that is the start of a new event chain; it creates its own correlational Id.
     *
     * @param type Event type to create.
     * @param sendTime Creation timestamp.
     * @param content Business data to add to the event.
     */
    public Event (EventType type, Date sendTime, Object content) {
        this.correlationalId = UUID.randomUUID().toString();
        this.eventType = type;
        this.sendTime = DateUtils.toDateString(sendTime);
        this.eventData = content;
    }
}
