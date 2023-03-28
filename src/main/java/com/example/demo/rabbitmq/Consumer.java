package com.example.demo.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
@RequiredArgsConstructor
public class Consumer {

    private final ConnectionFactory connectionFactory;

    public void consume() throws IOException, TimeoutException {

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody());
            System.out.println("Message received = " + message);
        };

        channel.basicConsume("Queue-1", true, deliverCallback, consumerTag -> {});

    }
}
