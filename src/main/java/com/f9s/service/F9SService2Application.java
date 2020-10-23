package com.f9s.service;

import com.f9s.service.broker.ChartXAxis;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
public class F9SService2Application {

    public static void main(String[] args) throws IOException {

        ChartXAxis.setTable("dayIdx.csv");
        ChartXAxis.setTable("dayXAxis.csv");
        ChartXAxis.setTable("dayTimestamp.csv");
        ChartXAxis.setTable("weekIdx.csv");
        ChartXAxis.setTable("weekXAxis.csv");
        ChartXAxis.setTable("weekTimestamp.csv");
        ChartXAxis.setTable("monthIdx.csv");
        ChartXAxis.setTable("monthXAxis.csv");
        ChartXAxis.setTable("monthTimestamp.csv");
        ChartXAxis.setTable("yearIdx.csv");
        ChartXAxis.setTable("yearXAxis.csv");
        ChartXAxis.setTable("yearTimestamp.csv");

        SpringApplication.run(F9SService2Application.class, args);
    }

}
