package com.example.demo.config;

import com.rabbitmq.client.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMQBean {

    @Bean
    public ConnectionFactory connectionFactory(){
        return new ConnectionFactory();
    }

}
