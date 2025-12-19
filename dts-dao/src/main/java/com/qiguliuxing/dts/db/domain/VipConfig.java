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
    public static final String COPPER_VIP = "copper";
    public static final String SILVER_VIP = "silver";
    public static final String GOLD_VIP = "gold";
    public static final String PLATINUM_VIP = "platinum";
    public static final String BLACKGOLD_VIP = "blackgold";
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    @TableField("id")
    private Integer id;

    /**
     * 会员名称
     */
    @ApiModelProperty("会员名称")
    @TableField("name")
    private String name;

    /**
     * VIP等级
     */
    @ApiModelProperty("VIP等级")
    @TableField("type")
    private String type;

    /**
     * 排序，越小越靠前
     */
    @ApiModelProperty("排序，越小越靠前")
    @TableField("sort")
    private Integer sort;

    /**
     * 会员描述信息
     */
    @ApiModelProperty("会员描述信息")
    @TableField("introduce")
    private String introduce;

    @ApiModelProperty("价格")
    @TableField("price")
    private float price;

    /**
     * 是否有效（0.有效1.无效）默认0
     */
    @ApiModelProperty("是否有效（0.有效1.无效）默认0")
    @TableField("is_deleted")
    private Integer isDeleted;

    /**
     * 礼品
     */
    @ApiModelProperty("礼品")
    @TableField("gifts")
    private String gifts;

    /**
     * 旅游权限详情
     */
    @ApiModelProperty("旅游权限详情")
    @TableField("travel")
    private String travel;

    /**
     * 单次购买会员有效时长（以天为单位）
     */
    @ApiModelProperty("是否有效（0.有效1.无效）默认0")
    @TableField("duration")
    private Integer duration;

}
