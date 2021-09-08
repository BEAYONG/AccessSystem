package com.nubomed.AccessSystem.config;

import org.springframework.beans.BeansException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author zhanglei
 * @date 2021/8/23 15:02
 */
public class WebSocketMappingHandlerMapping extends SimpleUrlHandlerMapping {

    private Map<String, WebSocketHandler> handlerMap = new LinkedHashMap<>();

    /**
     * Register WebSocket handlers annotated by @WebSocketMapping
     */
    @Override
    public void initApplicationContext() throws BeansException {
        Map<String, Object> beanMap = obtainApplicationContext()
                .getBeansWithAnnotation(com.nubomed.AccessSystem.config.WebSocketMapping.class);
        beanMap.values().forEach(bean -> {
            if (!(bean instanceof WebSocketHandler)) {
                throw new RuntimeException(
                        String.format("Controller [%s] doesn't implement WebSocketHandler interface.",
                                bean.getClass().getName()));
            }
            com.nubomed.AccessSystem.config.WebSocketMapping annotation = AnnotationUtils.getAnnotation(bean.getClass(), com.nubomed.AccessSystem.config.WebSocketMapping.class);
            handlerMap.put(Objects.requireNonNull(annotation).value(),
                    (WebSocketHandler) bean);
        });
        super.setOrder(Ordered.HIGHEST_PRECEDENCE);
        super.setUrlMap(handlerMap);
        super.initApplicationContext();
    }

}
