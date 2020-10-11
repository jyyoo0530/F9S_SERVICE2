package com.f9s.service.broker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class ArtemisProducer {

    @Autowired
    JmsTemplate jmsTemplate;

    @Value("${jms.topic.destination}")
    String destinationTopic;

    public void send(String msg){
        jmsTemplate.convertAndSend(destinationTopic, msg);
    }
}
