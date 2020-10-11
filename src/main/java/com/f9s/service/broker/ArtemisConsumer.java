package com.f9s.service.broker;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ArtemisConsumer {

    @JmsListener(destination = "${jms.topic.destination}")
    public void receive(String msg){
        System.out.println("Received Message: " + msg);
    }
}
