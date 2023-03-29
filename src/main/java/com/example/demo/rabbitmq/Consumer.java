//package com.example.demo.rabbitmq;
//
//import com.example.demo.model.MessageObject;
//import org.springframework.amqp.rabbit.annotation.Queue;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Service;
//
//@Service
//public class Consumer {
//
//    @RabbitListener(queues = "Mobile")
//    public void getMessage(MessageObject message){
//        System.out.println(message.getMessage());
//    }
//}
