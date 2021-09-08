package com.nubomed.AccessSystem.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.socket.CloseStatus;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.FluxSink;

/**
 * @author zhanglei
 * @date 2021/8/23 15:10
 */
public class WebSocketSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketSender.class);

    private final WebSocketSession session;
    private final FluxSink<WebSocketMessage> sink;
    private final Long instanceId;

    public WebSocketSender(WebSocketSession session, FluxSink<WebSocketMessage> sink, Long instanceId) {
        this.session = session;
        this.sink = sink;
        this.instanceId = instanceId;
    }

    public void sendData(String data) {
        LOGGER.info("发送到 instanceId：{} 的 websocket 消息: {}", instanceId, data);
        sink.next(session.textMessage(data));
    }

    public void close() {
        LOGGER.info("close websocket , instanceId : {}", instanceId);
        session.close(CloseStatus.GOING_AWAY);
    }

    public String getSessionId() {
        return session.getId();
    }
}
