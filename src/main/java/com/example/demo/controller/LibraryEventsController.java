package com.example.demo.controller;

import com.example.demo.domain.LibraryEvent;
import com.example.demo.domain.LibraryEventType;
import com.example.demo.kafka.LibraryEventProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LibraryEventsController {

    private final LibraryEventProducer libraryEventProducer;

    @PostMapping("/v1/libraryevent")
    public ResponseEntity<LibraryEvent> postLibraryEvent(@RequestBody LibraryEvent libraryEvent) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {

        libraryEvent.setLibraryEventType(LibraryEventType.NEW);

        // invoke kafka producer (Asynchronous)
        libraryEventProducer.sendLibraryEvent(libraryEvent);

        // invoke kafka producer (Asynchronous - Approach 2)
        libraryEventProducer.sendLibraryEvent_Approach2(libraryEvent);

        // invoke kafka producer (Synchronous)
        libraryEventProducer.sendLibraryEventSynchronous(libraryEvent);

        return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);

    }
}
