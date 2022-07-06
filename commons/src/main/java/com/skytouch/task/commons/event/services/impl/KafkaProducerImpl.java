package com.skytouch.task.commons.event.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skytouch.task.commons.config.kafka.KafkaPropertiesConfig;
import com.skytouch.task.commons.event.Event;
import com.skytouch.task.commons.event.EventType;
import com.skytouch.task.commons.event.services.Producer;
import com.skytouch.task.commons.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;

/**
 * Common message producing routine that will queue <code>Event</code> into a kafka topic provided by the caller.
 *
 * This was generalized into a common routine because event data is built to be generic, that is to say, regardless of
 * what process implements this, the specific business data queued into kafka will not change what has to be done to execute
 * that queueing.
 *
 * @author Waldo Terry
 */
@Service
@Slf4j
public class KafkaProducerImpl implements Producer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ConsumerFactory<String, Object> consumerFactory;

    private final KafkaPropertiesConfig properties;

    private final ObjectMapper mapper = new ObjectMapper();

    public KafkaProducerImpl(KafkaTemplate<String, String> kafkaTemplate, ConsumerFactory<String, Object> consumerFactory, KafkaPropertiesConfig properties) {
        this.kafkaTemplate = kafkaTemplate;
        this.consumerFactory = consumerFactory;
        this.properties = properties;
    }

    /**
     * Posts a message to the provided kafka topic.
     *
     * @param event The <code>Event</code> to post. Cannot be null.
     * @return The <code>correlationalId</code> associated with the posted event. It bears mentioning that this method will
     *         NEVER generate a correlational id for any given event. It is assumed that it will be provided by the caller,
     *         but it can be <code>null</code>.
     * @throws JsonProcessingException if for some reason the provided <code>Event</code> cannot be marshalled into a valid
     *         JSON format.
     */
    @Override
    public String sendEvent(Event event) throws JsonProcessingException {
        String jsonContent = mapper.writeValueAsString(event);

        kafkaTemplate.send(properties.getConsumers().getTopicName(), jsonContent);

        log.info("Sending Event to [" + properties.getConsumers().getTopicName() + "] with Id: " + event.getCorrelationalId());
        return event.getCorrelationalId();
    }

    /**
     * This  method requests the sending of a message to <code>task_topic</code> so the thread stops to wait for a related
     * reply in the dedicated <code>reply_topic</code>, whereas the business data in the reply will be extracted and
     * returned.
     *
     * An independent consumer for <code>reply_topic</code> is set up in this method, that will remain polling until such
     * a time as an event in the reply queue is found whose correlationalId matches that of the original event received.
     *
     * @param event Original event to send. Its <code>correlationalId</code> is the key through which the reply is built
     *              and matched to this event.
     * @throws IOException If the input event cannot be marshalled into a valid Json.
     */
    @Override
    public String requestReplyEvent(Event event) throws IOException {
        String jsonContent = mapper.writeValueAsString(event);
        String responseData = null;

        kafkaTemplate.send(properties.getConsumers().getTopicName(), jsonContent);

        log.info("Sending Event to [" + properties.getConsumers().getTopicName() + "] with Id: " + event.getCorrelationalId());

        Consumer<String, Object> replyConsumer = consumerFactory.createConsumer();

        replyConsumer.subscribe(Collections.singleton(properties.getConsumers().getReplyTopic()));

        //will poll only for 15 secs before returning an error.
        int i = 150;
        try {
            consumerPoll:
            while (i > 0) {
                log.info("Polling consumer for topic [" + properties.getConsumers().getReplyTopic() + "]");
                ConsumerRecords<String, Object> records = replyConsumer.poll(100);

                log.info("Fetched " + records.count() + "messages");
                for (ConsumerRecord<String, Object> record : records) {
                    log.info("Record value: " + record.value());

                    LinkedHashMap<String, Object> eventContent = (LinkedHashMap<String, Object>) record.value();

                    if (eventContent.get("correlationalId").equals(event.getCorrelationalId())) {
                        if (EventType.valueOf((String) eventContent.get("eventType")).equals(EventType.ERROR)) {
                            responseData = ERROR_IN_SEARCH + eventContent.get("eventData");
                        }
                        else {
                            responseData = JsonUtils.getJsonFromObject(eventContent.get("eventData"));
                        }
                        break consumerPoll;
                    }
                }
                i --;
            }

            if (responseData == null){
                responseData = NO_RESULTS_RECEIVED;
            }
        }
        finally {
            replyConsumer.close();
        }

        return responseData;
    }

    /**
     * Method that sends a message to the dedicated reply queue to implement a request-reply like behaviour.
     *
     * @param event Event to send.
     * @return The correlational Id of the sent event.
     * @throws JsonProcessingException If the event cannot be marshalled into a valid Json format.
     */
    @Override
    public String sendReplyEvent(Event event) throws JsonProcessingException {
        String jsonContent = mapper.writeValueAsString(event);

        ListenableFuture<SendResult<String, String>> result = kafkaTemplate.send(properties.getConsumers().getReplyTopic(), jsonContent);

        log.info("Sending Event to [" + properties.getConsumers().getReplyTopic() + "] with Id: " + event.getCorrelationalId());

        return event.getCorrelationalId();
    }
}
