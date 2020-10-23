package com.f9s.service.websocket;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@EnableScheduling
public class WebSocketBroadcastController {

//    @Autowired
//    private SimpMessageSendingOperations messagingTemplate;
//    private Gson gson = new Gson();
//
//    @Scheduled(fixedDelay = 1000*2)
//    @SendTo("/topic/test")
//    public void sendMessage(){
//        messagingTemplate.convertAndSend("/topic/test", "");
//    }

}
