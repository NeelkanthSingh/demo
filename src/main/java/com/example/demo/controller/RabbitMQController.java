package com.example.demo.controller;

import com.example.demo.model.MessageObject;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Controller
@RequestMapping("/send")
@RequiredArgsConstructor
public class RabbitMQController {

    private final RabbitTemplate rabbitTemplate;

    @GetMapping("/message")
    ResponseEntity<String> publishMessage(@RequestParam String message) throws IOException, TimeoutException {
        MessageObject messageObject = MessageObject.builder().id(1).message(message).build();
        rabbitTemplate.convertAndSend("Mobile", messageObject);
        return ResponseEntity.ok("Message Sent");
    }
}
