package com.skytouch.task.commons.event.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.skytouch.task.commons.dtos.requests.ProductDTO;
import com.skytouch.task.commons.event.Event;

import java.io.IOException;

/**
 * Interface used to wire a service that will enable posting Event messages to a messaging queue.
 *
 * @author Waldo Terry
 */
public interface Producer {

    String NO_RESULTS_RECEIVED = "No search results were received.";

    String ERROR_IN_SEARCH = "Search produces an error. Error is: ";

    /**
     * Posts a message to the provided kafka topic.
     *
     * @param event The <code>Event</code> to post. Cannot be null and must contain some form of business data (empty
     *              events only clutter up the queue).
     * @return The <code>correlationalId</code> associated with the posted event. It bears mentioning that this method will
     *         NEVER generate a correlational id for any given event. It is assumed that it will be provided by the caller,
     *         but it can be <code>null</code>.
     * @throws JsonProcessingException if for some reason the provided <code>Event</code> cannot be marshalled into a valid
     *         JSON format.
     */
    String sendEvent(Event event) throws JsonProcessingException;

    /**
     * This  method requests the sending of a message to <code>task_topic</code> so the thread stops to wait for a related
     * reply in the dedicated <code>reply_topic</code>, whereas the business data in the reply will be extracted and
     * returned.
     *
     * It is expected that implementers of this method have a means to poll the <code>reply_topic</code> for its messages
     * and are able to find the reply corresponding to the first event sent.
     *
     * @param event Original event to send. Its <code>correlationalId</code> is the key through which the reply is built
     *              and matched to this event.
     * @return The business data extracted from the reply event
     * @throws IOException If the input event cannot be marshalled into a valid Json.
     */
    String requestReplyEvent(Event event) throws IOException;

    /**
     * Method that sends a message to the dedicated reply queue to implement a request-reply like behaviour.
     *
     * @param event Event to send.
     * @return The correlational Id of the sent event.
     * @throws JsonProcessingException If the event cannot be marshalled into a valid Json format.
     */
    String sendReplyEvent(Event event) throws JsonProcessingException;
}
