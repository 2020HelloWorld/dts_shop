package com.qiguliuxing.dts.wx.pay;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "alipay")
public class AlipayProperties {

    private String appId;
    private String privateKey;
    private String alipayPublicKey;
    private String notifyUrl;
    private String returnUrl;
//    private String gateway = "https://openapi.alipay.com/gateway.do";
    private String gateway = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";
}

