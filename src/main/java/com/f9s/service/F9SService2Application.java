package com.f9s.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@SpringBootApplication
public class F9SService2Application {


    public static void main(String[] args) {
        SpringApplication.run(F9SService2Application.class, args);
    }

}
