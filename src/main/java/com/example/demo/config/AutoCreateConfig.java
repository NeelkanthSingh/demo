package com.example.demo.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class AutoCreateConfig {

    @Bean
    public NewTopic libraryEvents() {

        return TopicBuilder.name("library-events")
                .partitions(3)
                .replicas(3)
                .build();
    }

    @Bean
    public NewTopic libraryEventsRetry() {

        return TopicBuilder.name("${topics.retry}")
                .partitions(3)
                .replicas(3)
                .build();
    }

    @Bean
    public NewTopic libraryEventsDlt() {

        return TopicBuilder.name("${topics.dlt}")
                .partitions(3)
                .replicas(3)
                .build();
    }
}
