package com.nubomed.AccessSystem.common;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
@ApiModel("websocket通用接口返回结果")
public class WsBaseResult {

    public WsBaseResult(String msgid, String sn, String cmd) {
        this.cmd = cmd;
        this.msgid = msgid;
        this.sn = sn;
    }

    public WsBaseResult(String msgid, String sn, String cmd, String msg) {
        this.cmd = cmd;
        this.msgid = msgid;
        this.sn = sn;
        this.msg = msg;
    }
    @ApiModelProperty(value = "指令", required = true)
    private String cmd;

    @ApiModelProperty(value = "消息的序列号", required = true)
    private String msgid;

    @ApiModelProperty(value = "机器的sn号码", required = true)
    private String sn;

    @ApiModelProperty(value = "操作的执行结果状态", required = true)
    private String msg;




    public static WsBaseResult success(String msgid,String sn,String cmd) {
        WsBaseMsg msg = WsBaseMsg.builder().status(0).msg("ok").build();
        String response = JSON.toJSONString(msg);
        return new WsBaseResult(msgid,sn,cmd,response);
    }

    public static WsBaseResult error(String msgid, String sn, String cmd) {
        WsBaseMsg msg = WsBaseMsg.builder().status(1).msg("failed").build();
        String response = JSON.toJSONString(msg);
        return new WsBaseResult(msgid,sn,cmd,response);

    }
}
