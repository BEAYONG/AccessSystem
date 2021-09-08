package com.nubomed.AccessSystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhanglei
 * @date 2021/8/23 14:59
 */
@Configuration
public class WebSocketConfiguration {

    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }

    @Bean
    public HandlerMapping webSocketMapping() {
        return new WebSocketMappingHandlerMapping();
    }

    @Bean
    public ConcurrentHashMap<Long, WebSocketSender> senderMap() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    public ConcurrentHashMap<String, Long> sessionMap() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    public ConcurrentHashMap<Long, Long> heartBeatMap() {
        return new ConcurrentHashMap<>();
    }
}
