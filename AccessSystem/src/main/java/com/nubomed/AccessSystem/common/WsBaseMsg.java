package com.nubomed.AccessSystem.common;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class WsBaseMsg {

    private Integer status;

    private String msg;
}
