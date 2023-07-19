package com.hellparty.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-19
 * description  :
 */

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS(); // 웹 소켓을 지원하지 않는 브라우저에서도 환경을 제공하도록하는 SockJS 라이브러리를 사용한다.
    }

    @Override
    // 메시지 브로커 설정
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 스프링에서 제공하는 Simple 브로커를 사용하겠다. 그리고 prefix에 해당하는 url로 요청이 올 경우 이 메시지를 Simple 브로커가 처리하겠다.
        // 관습 : queue 라는 prefix는 메시지가 1:1라 송신될때, topic 이라는 prefix는 메시지가 1:N 으로 송신될때 사용한다.
        registry.enableSimpleBroker("/topic");

        // 상황에 따라 바로 브로커로 가는 게 아니라 메시지의 처리가 가공이 필요할 때 핸들러를 타게 할 수 있다.
        // /app 이 붙어있는 경로로 발신되면 해당 경로를 처리하고 있는 핸들러로 전송된다.
        registry.setApplicationDestinationPrefixes("/app");
    }
}
