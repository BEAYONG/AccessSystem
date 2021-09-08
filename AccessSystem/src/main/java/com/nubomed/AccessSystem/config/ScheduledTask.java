package com.nubomed.AccessSystem.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhanglei
 * @date 2021/8/30 16:22
 */
@Slf4j
@Component
public class ScheduledTask {

    public final static long SECOND = 1000;
    public final static long HALF_MIN = 30 * SECOND;

    @Autowired
    private ConcurrentHashMap<Long, Long> heartBeatMap;
    @Autowired
    private ConcurrentHashMap<Long, WebSocketSender> senderMap;
    @Autowired
    private ConcurrentHashMap<String, Long> sessionMap;



    /**
     * 心跳包检查
     */
    @Scheduled(fixedDelay = HALF_MIN)
    public void heartBeatCheck() {
        log.debug("开始心跳检查,当前 {} 个连接", sessionMap.size());
        long now = System.currentTimeMillis();
        // 查找过期服务，最后一次心跳时间 + 服务端配置的心跳间隔时间 < 当前时间

        for (Long k : heartBeatMap.keySet()) {
            Long v = heartBeatMap.get(k);
            if (now - v > 3 * HALF_MIN) {
                log.info("剔除超时连接：{}", k);
                WebSocketSender sender = senderMap.get(k);
                if (sender != null){
                    sender.close();
                    // remove map
                    sessionMap.remove(sender.getSessionId());
                }
                heartBeatMap.remove(k);
                senderMap.remove(k);

            }

        }

        log.debug("结束心跳检查,当前 {} 个连接,耗时 {} ms", sessionMap.size(), System.currentTimeMillis() - now);
    }
}
