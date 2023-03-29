package com.example.demo.rabbitmq;

import com.example.demo.model.MessageObject;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

@Service
public class Consumer {

    // This is consumer service for Direct Exchange, Topic Exchange and Fanout Exchange

    /**

    @RabbitListener(queues = "Mobile")
    public void getMessage(MessageObject message){
        System.out.println(message.getMessage());
    }

    **/

    // This is consumer service for Header Exchange

    @RabbitListener(queues = "Mobile")
    public void getMessage(byte[] message) throws IOException, ClassNotFoundException {

        ByteArrayInputStream bis = new ByteArrayInputStream(message);
        ObjectInput in = new ObjectInputStream(bis);
        MessageObject messageObject = (MessageObject) in.readObject();
        in.close();
        bis.close();

        System.out.println(messageObject.getMessage());
    }
}
