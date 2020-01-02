package com.mustr.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketStompConfig implements WebSocketMessageBrokerConfigurer{

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry
		.addEndpoint("/endpoint")
		.setAllowedOrigins("*")
		.withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/topic", "/queue");
		
		//RabbitMQ配置
		/*registry.enableStompBrokerRelay("/topic", "/queue")
		.setRelayHost("127.0.0.1")
		.setRelayPort(61613)
		.setClientLogin("guest")
		.setClientPasscode("guest")
		.setSystemLogin("guest")
		.setSystemPasscode("guest");*/
		
		registry.setApplicationDestinationPrefixes("/app");
		registry.setUserDestinationPrefix("/topic/user");
		
	}

	
}
