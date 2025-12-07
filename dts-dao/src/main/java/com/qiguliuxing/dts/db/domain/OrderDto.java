package com.qiguliuxing.dts.db.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderDto implements Serializable {
    private String name;
    private String phone;
    private String price;
    private String type;
}
