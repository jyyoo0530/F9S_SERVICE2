package com.f9s.service.websocket;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint("/f9s")
                .setAllowedOrigins("*")
                .setHandshakeHandler(new DefaultHandshakeHandler() {
                    public boolean beforeHandShake(
                            ServerHttpRequest request,
                            ServerHttpResponse response,
                            WebSocketHandler wsHandler,
                            Map attributes) throws Exception {

                        if (request instanceof ServletServerHttpRequest) {
                            ServletServerHttpRequest servletRequest
                                    = (ServletServerHttpRequest) request;
                            HttpSession session = servletRequest
                                    .getServletRequest().getSession();
                            attributes.put("sessionId", session.getId());
                        }

                        return true;
                    }

                })
                .withSockJS();

//                .setSupressCors(true)
//                .setHeartbeatTime(60000_000);
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new TopicInterceptor());
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableStompBrokerRelay("/topic", "/queue")
                .setRelayHost("data.freight9.com")
                .setRelayPort(61613)
                .setSystemLogin("f9s")
                .setSystemPasscode("12345678")
                .setClientLogin("f9s")
                .setClientPasscode("12345678")
                .setSystemHeartbeatReceiveInterval(1000)
                .setSystemHeartbeatSendInterval(1000);
        registry.setApplicationDestinationPrefixes("/app");


    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.setMessageSizeLimit(31 * 1024); // default : 64 * 1024
    }

//    @Bean
//    public TaskScheduler heartBeatScheduler(){
//        return new ThreadPoolTaskScheduler();
//    }

}
