package com.example.demo.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConsumerRecordRecoverer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.ExponentialBackOffWithMaxRetries;
import org.springframework.util.backoff.ExponentialBackOff;
import org.springframework.util.backoff.FixedBackOff;

import java.util.List;

@Configuration
@EnableKafka
@Slf4j
@RequiredArgsConstructor
public class LibraryEventsConsumerConfig {

    private final KafkaTemplate kafkaTemplate;

    @Value("${topics.retry}")
    private String retryTopic;

    @Value("${topics.dlt}")
    private String deadLetterTopic;

    // There can Be our own recovery logic instead of using DeadLetterPublishingRecoverer through ConsumerRecordRecoverer
    public ConsumerRecordRecoverer recoverer() {
    }

    public DeadLetterPublishingRecoverer publishingRecoverer(){

        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate, (record, ex) -> {

            if(ex.getCause() instanceof RecoverableDataAccessException) {
                return new TopicPartition(retryTopic, record.partition());
            } else {
                return new TopicPartition(deadLetterTopic, record.partition());
            }
        });

        return recoverer;
    }

    public DefaultErrorHandler errorHandler() {
        var fixedBackOff = new FixedBackOff(1000L, 2);

        var expBackOff = new ExponentialBackOffWithMaxRetries(2);
        expBackOff.setInitialInterval(1000L);
        expBackOff.setMultiplier(2.0);
        expBackOff.setMaxInterval(2000L);

        var errorHandler = new DefaultErrorHandler(
                publishingRecoverer(),
//                fixedBackOff
                expBackOff
        );

        var exceptionsToIgnoreList = List.of(NullPointerException.class);
        var exceptionsToRetryList = List.of(ArithmeticException.class);

        exceptionsToRetryList.forEach(errorHandler::addRetryableExceptions);
        exceptionsToIgnoreList.forEach(errorHandler::addNotRetryableExceptions);

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
