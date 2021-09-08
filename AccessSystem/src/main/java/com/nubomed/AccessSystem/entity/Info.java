package com.nubomed.AccessSystem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2021-09-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("info")
@ApiModel(value="Info对象", description="")
public class Info extends Model<Info> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "消息的id")
    private String id;

    @ApiModelProperty(value = "机器的sn号码")
    private String sn;

    @ApiModelProperty(value = "1:注册人员;2:删除人员;3:open;4:reboot")
    private Integer cmd;

    @ApiModelProperty(value = "保存信息的详情的字符串")
    private String msg;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
