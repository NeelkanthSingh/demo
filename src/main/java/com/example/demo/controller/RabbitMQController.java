package com.example.demo.controller;

import com.example.demo.rabbitmq.Publisher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Controller
@RequestMapping("/pub")
@RequiredArgsConstructor
public class RabbitMQController {

    private final Publisher publisher;

    @GetMapping("/post")
    ResponseEntity<String> publishMessage() throws IOException, TimeoutException {
        publisher.publish();
        return ResponseEntity.ok("First Message Sent");
    }
}
