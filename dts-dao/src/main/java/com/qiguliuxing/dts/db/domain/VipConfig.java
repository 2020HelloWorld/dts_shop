package com.qiguliuxing.dts.db.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("dts_vip_config")
public class VipConfig implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    @TableField("id")
    private Integer id;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    @TableField("name")
    private String name;

    /**
     * VIP等级，从1开始，不重复
     */
    @ApiModelProperty("VIP等级，从1开始，不重复")
    @TableField("level")
    private Integer level;

    /**
     * 排序，越小越靠前
     */
    @ApiModelProperty("排序，越小越靠前")
    @TableField("sort")
    private Integer sort;

    /**
     * 描述信息
     */
    @ApiModelProperty("描述信息")
    @TableField("description")
    private String description;

    @ApiModelProperty("价格")
    @TableField("price")
    private float price;
}
