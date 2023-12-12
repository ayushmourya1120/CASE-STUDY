package com.cms.config;

import com.cms.model.MQMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Queue;

@Component
public class MQSender {


    @Autowired
    private Queue queue;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(MQMessage message) {
        rabbitTemplate.convertAndSend("register_queue", message);
    }

}
