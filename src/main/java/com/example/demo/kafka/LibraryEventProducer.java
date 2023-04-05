package com.example.demo.kafka;

import com.example.demo.domain.LibraryEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
@Slf4j
@RequiredArgsConstructor
public class LibraryEventProducer {

    private final KafkaTemplate<Integer, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final String TOPIC = "library-events";

    /**
     * Send a message with the given topic and key(Asynchronously).
     * @param libraryEvent the message.
     * @throws JsonProcessingException
     */
    public void sendLibraryEvent(@NonNull LibraryEvent libraryEvent) throws JsonProcessingException{

        Integer key = libraryEvent.getLibraryEventId();
        String value = objectMapper.writeValueAsString(libraryEvent);

        kafkaTemplate.sendDefault(key, value).whenComplete((result, ex) -> {
            if (ex != null) {
                handleFailure(key, value, ex);
            } else {
                handleSuccess(key, value, result);
            }
        });
    }

    /**
     * Send a message with the given topic and key(Asynchronously).
     * @param libraryEvent the message.
     * @throws JsonProcessingException
     */
    public void sendLibraryEvent_Approach2(@NonNull LibraryEvent libraryEvent) throws JsonProcessingException{

        Integer key = libraryEvent.getLibraryEventId();
        String value = objectMapper.writeValueAsString(libraryEvent);

        ProducerRecord<Integer, String> producerRecord = buildProducerRecord(key, value, TOPIC);
        kafkaTemplate.send(producerRecord).whenComplete((result, ex) -> {
            if (ex != null) {
                handleFailure(key, value, ex);
            } else {
                handleSuccess(key, value, result);
            }
        });
    }

    private ProducerRecord<Integer, String> buildProducerRecord(Integer key, String value, String s) {

        List<Header> recordHeaders = List.of(new RecordHeader("event-source", "scanner".getBytes()));

        return new ProducerRecord<>(TOPIC, null, key, value, recordHeaders);
    }

    /**
     * Send a message with the given topic and key(Synchronously).
     * @param libraryEvent the message.
     * @throws JsonProcessingException
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws TimeoutException
     */
    public SendResult<Integer, String> sendLibraryEventSynchronous(@NonNull LibraryEvent libraryEvent) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {

        Integer key = libraryEvent.getLibraryEventId();
        String value = objectMapper.writeValueAsString(libraryEvent);
        SendResult<Integer, String> sendResult = null;

        try{
            sendResult = kafkaTemplate.sendDefault(key, value).get(1, TimeUnit.SECONDS);
            handleSuccess(key, value, sendResult);
        }catch (ExecutionException | InterruptedException e){
            log.error("ExecutionException/InterruptedException Sending the Message and the exception is {}", e.getMessage());
            throw e;
        }catch (Exception e){
            log.error("Exception Sending the Message and the exception is {}", e.getMessage());
            throw e;
        }

        return sendResult;
    }

    private void handleSuccess(Integer key, String value, SendResult<Integer, String> result) {
        log.info("Message sent successfully for the key: {} and the value is {}, partition is {} ", key, value, result.getRecordMetadata().partition());
    }

    private void handleFailure(Integer key, String value, Throwable ex) {
        log.error("Error Sending the Message and the exception is {}", ex.getMessage());
        try {
            throw ex;
        }catch (Throwable throwable){
            log.error("Error in OnFailure: {}", throwable);
        }
    }
}
