package com.skytouch.task.commons.config.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Part of the kafka configuration properties structure. Binds values relevant to the consumers.
 *
 * @author Waldo Terry
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Consumers {

    //Consumer group for kafka.
    private String group;

    //Topic on which replies will be sent from the backend to the frontend for the request-reply pattern in searches.
    private String replyTopic;

    //Topic where the frontend will post requests to the frontend.
    private String topicName;
}
