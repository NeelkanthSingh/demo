package com.example.demo.kafka;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AcknowledgingConsumerAwareMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class LibraryEventsConsumerManuelOffset implements AcknowledgingConsumerAwareMessageListener<Integer, String> {

    @Override
    @KafkaListener(topics = {"library-events"})
    public void onMessage(ConsumerRecord<Integer, String> data, Acknowledgment acknowledgment, Consumer<?, ?> consumer) {
        log.info("ConsumerRecord: {}", data);
        acknowledgment.acknowledge();
    }
}
