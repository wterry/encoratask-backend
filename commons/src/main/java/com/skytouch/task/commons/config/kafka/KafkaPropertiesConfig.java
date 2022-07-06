package com.skytouch.task.commons.config.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties for kafka elements.
 *
 * @author Waldo Terry
 */
@Configuration
@ConfigurationProperties(prefix = "kafka")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KafkaPropertiesConfig {

    //kafka broker uris
    private String bootstrapServers;
    //information related to consumers.
    private Consumers consumers;
}
