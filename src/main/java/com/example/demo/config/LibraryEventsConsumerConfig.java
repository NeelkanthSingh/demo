package com.example.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
@EnableKafka
@Slf4j
public class LibraryEventsConsumerConfig {

    public DefaultErrorHandler errorHandler() {
        var fixedBackOff = new FixedBackOff(1000L, 2);
        var errorHandler = new DefaultErrorHandler(fixedBackOff);

        errorHandler
                .setRetryListeners((record, ex, deliveryAttempt) -> {
                    log.info("Error in consumer: {}, record: {}, deliveryAttempt: {}", ex.getMessage(), record, deliveryAttempt);
                });

        return errorHandler;
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
            ConsumerFactory<Object, Object> kafkaConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        configurer.configure(factory, kafkaConsumerFactory);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        factory.setCommonErrorHandler(errorHandler()); // This is to handle the error in the consumer. With this, we can retry the message, however many times.
        // factory.setConcurrency(3); // This is to spin up multiple threads to consume, use only if you are not running on cloud.
        return factory;

    }

}
