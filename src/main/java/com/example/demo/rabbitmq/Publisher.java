package com.example.demo.rabbitmq;

import com.rabbitmq.client.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

@Component
@RequiredArgsConstructor
public class Publisher {

    private final ConnectionFactory connectionFactory;

    public void publish() throws IOException, TimeoutException {
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        String message = "First message";

        // Below is the props type, generally used in case of Headers-Exchange

        /**
        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("item1", "Mobile");
        headers.put("item2", "Television");

        BasicProperties basicProperties = new AMQP.BasicProperties.Builder().headers(headers).build();
        **/

        channel.basicPublish("", "Queue-1", null, message.getBytes());

        channel.close();
        connection.close();
    }

}
