package com.nubomed.AccessSystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 编码生成
 * </p>
 *
 * @author ${author}
 * @since 2021-09-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("msg_detail")
@ApiModel(value="MsgDetail对象", description="编码生成")
public class MsgDetail extends Model<MsgDetail> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "终端设备的秘钥")
    private String msgKey;

    @ApiModelProperty(value = "对应消息表中的id")
    private String msgid;

    @ApiModelProperty(value = "验证人员唯一性的字段")
      @TableId(value = "id")
    private String id;

    @ApiModelProperty(value = "人员的名字")
    private String msgName;

    @ApiModelProperty(value = "作为维根号发送到闸机")
    private String icNo;

    @ApiModelProperty(value = "人脸照片")
    private byte[] photo;

    @ApiModelProperty(value = "员工身份证号")
    private String idNo;

    @ApiModelProperty(value = "起始时间")
    private Double  startTs;

    @ApiModelProperty(value = "截至时间")
    private Double endTs;

    @ApiModelProperty(value = "有效时间内最大过闸次数，默认是10000")
    private Double passCount;

    @ApiModelProperty(value = "1:表示是访客，0表示是非访客")
    private int visitor;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
