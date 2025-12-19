package com.qiguliuxing.dts.db.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderDto implements Serializable {
    private String name;
    private String phone;
    private String price;
    private String type;//会员类型
    private Integer orderType;//订单类型(0.会员订单1.普通订单)
}
