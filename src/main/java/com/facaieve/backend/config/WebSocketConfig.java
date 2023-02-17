package com.facaieve.backend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker //문자 채팅용 웹소켓을 활성화시킴
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer   {//스톰프로 구현
//    private final WebSocketHandler webSocketHandler;// 들어온 정보를 처리할 핸들러
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry
//                .addHandler(webSocketHandler, "ws/chat")//핸들러와 웹소켓의 주소를 설정하여 해당 주소로 웹소켓 연결할 수 있음.
//                .setAllowedOrigins("*");//cord 설정
//    }
    @Override  // 웹 소켓 연결을 위한 엔드포인트 설정 및 stomp sub/pub 엔드포인트 설정
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue", "subscribe");//메세지를 구독하는 요청 url -> 채팅방을 시작하여 메시지를 받을 때(subscribe), SimpleBroker가 /queue 경로로 subscribe하는 클라이언트에게 메시지를 전달하는 작업 수행
        config.setApplicationDestinationPrefixes("/publish", "/app");//메세지를 발행하는 요청 url -> 메세지를 보낼 때
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {//클라이언트와 handshake할 endpoint를 지정
        registry.addEndpoint("/ws-stomp").setAllowedOrigins("*").withSockJS();
    }


}
