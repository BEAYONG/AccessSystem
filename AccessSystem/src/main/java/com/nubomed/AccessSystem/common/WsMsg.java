package com.nubomed.AccessSystem.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class WsMsg {

    @ApiModelProperty(value = "响应返回的状态信息", required = true)
    private String msg;

    @ApiModelProperty(value = "响应返回的状态码，成功返回0", required = true)
    private Integer status;

}
