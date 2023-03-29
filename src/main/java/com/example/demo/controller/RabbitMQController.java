package com.example.demo.controller;

import com.example.demo.model.MessageObject;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

@Controller
@RequestMapping("/send")
@RequiredArgsConstructor
public class RabbitMQController {

    private final RabbitTemplate rabbitTemplate;

    @GetMapping("/message")
    ResponseEntity<String> publishMessage(@RequestParam String message) throws IOException, TimeoutException {
        MessageObject messageObject = MessageObject.builder().id(1).message(message).build();

        // All three exchange are implemented below

        /**
        rabbitTemplate.convertAndSend("Direct-exchange","mobile", messageObject);
        rabbitTemplate.convertAndSend("Fanout-exchange","mobile", messageObject);
        rabbitTemplate.convertAndSend("Topic-exchange","mobile", messageObject);
        **/

        return ResponseEntity.ok("Message Sent");
    }

    @GetMapping("/message/header-exchange")
    ResponseEntity<String> publishHeaderExchangeMessage(@RequestParam String message) throws IOException, TimeoutException {
        MessageObject messageObject = MessageObject.builder().id(1).message(message).build();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(bos);
        out.writeObject(messageObject);
        out.flush();
        out.close();

        byte[] messageBytes = bos.toByteArray();
        bos.close();

        Message msg = MessageBuilder.withBody(messageBytes)
                .setHeader("item1", "mobile")
                .setHeader("item2", "tv")
                .build();

        rabbitTemplate.send("Header-exchange", "", msg);

        return ResponseEntity.ok("Message Sent");
    }
}
