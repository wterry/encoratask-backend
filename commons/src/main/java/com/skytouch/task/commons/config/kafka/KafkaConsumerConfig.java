package com.skytouch.task.commons.config.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * A separate configuration class for the consumption of events from a kafka topic.
 *
 * The actual configuration values are set on the properties yml properties file.
 *
 * @author Waldo Terry
 */
@Configuration
public class KafkaConsumerConfig {

//    @Value("${kafka.bootstrap-servers}")
//    private String bootstrapServers;
//
//    @Value("${kafka.consumers.group-id}")
//    private String consumerGroup;

    private KafkaPropertiesConfig properties;

    public KafkaConsumerConfig(KafkaPropertiesConfig properties) {
        this.properties = properties;
    }
    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, properties.getConsumers().getGroup());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
//        props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);


        return props;
    }

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {

        return new DefaultKafkaConsumerFactory<>(consumerConfigs(), new StringDeserializer(), new JsonDeserializer<>(Object.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory());
//        factory.setMessageConverter(new StringJsonMessageConverter());


        return factory;
    }
}
