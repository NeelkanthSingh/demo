package com.example.demo.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
@RequiredArgsConstructor
public class Publisher {

    private final ConnectionFactory connectionFactory;

    public void publish() throws IOException, TimeoutException {
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

//        String message = "First message";
//
//        channel.basicPublish("", "Queue-1", null, message.getBytes());

        channel.close();
        connection.close();
    }

}
