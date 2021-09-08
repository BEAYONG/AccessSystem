package com.nubomed.AccessSystem.handler;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nubomed.AccessSystem.common.WsBaseResult;
import com.nubomed.AccessSystem.config.WebSocketMapping;
import com.nubomed.AccessSystem.entity.Info;
import com.nubomed.AccessSystem.entity.MsgDetail;
import com.nubomed.AccessSystem.mapper.InfoMapper;
import com.nubomed.AccessSystem.mapper.MsgDetailMapper;
import com.nubomed.AccessSystem.util.ProjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.HashMap;
import java.util.Objects;


@Slf4j
@Component
@WebSocketMapping("/devs")
public class MsgHandler implements WebSocketHandler {

    @Autowired
    private InfoMapper infoMapper;
    @Autowired
    private MsgDetailMapper msgDetailMapper;


    public Mono<Void> handle(WebSocketSession session) {
        return session.receive().flatMap(webSocketMessage -> {
            String payloadAsText = webSocketMessage.getPayloadAsText();
            return process(payloadAsText, session);
        }).then();
    }

    public Mono<Void> process(String payloadAsText, WebSocketSession session) {
        JSONObject info = JSON.parseObject(String.valueOf(payloadAsText));
        String cmd = info.getString("cmd");
        switch (cmd){
            case "setPerson":
                Info message = new Info();
                message.setId(info.getString("msgid"));
                message.setSn(info.getString("sn"));
                message.setCmd(ProjectUtil.CmdToNum(info.getString("cmd")));
                String msg1 = info.getString("msg");
                infoMapper.insert(message);
                MsgDetail msgDetail = ProjectUtil.ToMsgDetail(msg1);
                msgDetail.setMsgid(info.getString("msgid"));
                msgDetailMapper.insert(msgDetail);
                WsBaseResult wsBaseResult1 = WsBaseResult.success(info.getString("msgid"),info.getString("sn"),"");
                return Mono.zip(session.send(Flux.just(session.textMessage(JSON.toJSONString(wsBaseResult1)))), session.close()).then();
            case "removePerson":
                String msg2 = info.getString("msg");
                MsgDetail msgDetail1 = ProjectUtil.ToMsgDetail(msg2);
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("id",msgDetail1.getId());
                msgDetailMapper.deleteByMap(hashMap);
                WsBaseResult wsBaseResult2 = WsBaseResult.success(info.getString("msgid"),info.getString("sn"),"");
                return Mono.zip(session.send(Flux.just(session.textMessage(JSON.toJSONString(wsBaseResult2)))), session.close()).then();
            case "open":
            case "reboot":
                WsBaseResult wsBaseResult3 = WsBaseResult.success(info.getString("msgid"),info.getString("sn"),"");
                return Mono.zip(session.send(Flux.just(session.textMessage(JSON.toJSONString(wsBaseResult3)))), session.close()).then();
            default:
                WsBaseResult wsBaseResult4 = WsBaseResult.error(info.getString("msgid"),info.getString("sn"),"");
                return Mono.zip(session.send(Flux.just(session.textMessage(JSON.toJSONString(wsBaseResult4)))), session.close()).then();
        }
    }

}