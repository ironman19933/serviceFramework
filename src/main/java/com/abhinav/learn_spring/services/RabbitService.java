package com.abhinav.learn_spring.services;

import lombok.extern.java.Log;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log
@Service
public class RabbitService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String exchange, String routingKey, Object object) {
        log.info("abhinav-sending message to rabbitmq");
        rabbitTemplate.convertAndSend(exchange, routingKey, object);
        log.info("abhinav-message sent to rabbitmq");
    }
}
