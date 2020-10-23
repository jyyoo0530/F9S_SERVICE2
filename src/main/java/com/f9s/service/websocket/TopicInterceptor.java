package com.f9s.service.websocket;

import com.f9s.service.broker.f9sTopicDestinations;
import lombok.Synchronized;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

public class TopicInterceptor implements ChannelInterceptor {


    @Override
    public void postSend(Message message, MessageChannel channel, boolean sent) {
        try {
            StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
            String sessionId = Objects.requireNonNull(accessor.getSessionId());
            String dest = ObjectUtils.isEmpty(accessor.getDestination()) ? "" : accessor.getDestination();

            switch (Objects.requireNonNull(accessor.getCommand())) {
                case CONNECT:
                    System.out.println("CONNECTED: " + sessionId);
                    break;
                case DISCONNECT:
                    System.out.println("DISCONNECTED: " + sessionId);
                    break;
                case SUBSCRIBE:
                    System.out.println("SUBSCRIBED: " + sessionId + "    " + dest);
                    f9sTopicDestinations.setDestination(sessionId, dest);
                    break;
                case UNSUBSCRIBE:
                    System.out.println("UNSUBSCRIBED: " + sessionId);
                    f9sTopicDestinations.removeDestinations(sessionId);
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
