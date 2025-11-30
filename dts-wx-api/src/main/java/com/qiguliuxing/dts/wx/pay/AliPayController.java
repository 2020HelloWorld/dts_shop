package com.qiguliuxing.dts.wx.pay;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.qiguliuxing.dts.db.domain.VipOrder;
import com.qiguliuxing.dts.wx.service.VipOrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pay/alipay")
public class AliPayController {

    @Resource
    private AlipayProperties aliPayProperties;
    @Resource
    private VipOrderService vipOrderService;

    @GetMapping("/createOrder")
    @ApiOperation("创建支付订单")
    public String createOrder(@RequestParam String orderNo,
                                           @RequestParam Integer amount) throws Exception {

        //1.先根据订单号查询对应订单信息
        VipOrder vipOrder = vipOrderService.queryByOrderNo(orderNo);
        AlipayClient alipayClient = new DefaultAlipayClient(
                aliPayProperties.getGateway(),
                aliPayProperties.getAppId(),
                aliPayProperties.getPrivateKey(),
                "json",
                "UTF-8",
                aliPayProperties.getAlipayPublicKey(),
                "RSA2"
        );

        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        request.setNotifyUrl(aliPayProperties.getNotifyUrl());
        request.setReturnUrl(aliPayProperties.getReturnUrl());

        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", orderNo);
        bizContent.put("total_amount", vipOrder.getFeeAmount().toString());
        bizContent.put("subject", vipOrder.getSubject());
        bizContent.put("product_code", "QUICK_WAP_WAY");
        bizContent.put("timeout_express","5m");

        request.setBizContent(bizContent.toJSONString());

        String form = alipayClient.pageExecute(request).getBody();

//        // form 是完整 HTML 表单，需要前端渲染
//        Map<String, Object> result = new HashMap<>();
//        result.put("payForm", form); // H5 页面可直接写入并自动提交
        return form;
    }

    @PostMapping("/notify")
    public String notify(HttpServletRequest request) throws Exception {

        System.out.println("执行回调notify");

        Map<String, String> params = new HashMap<>();
        request.getParameterMap().forEach((k, v) -> params.put(k, v[0]));

        boolean signVerified = AlipaySignature.rsaCheckV1(
                params,
                aliPayProperties.getAlipayPublicKey(),
                "UTF-8",
                "RSA2"
        );

        if (signVerified) {
            String outTradeNo = params.get("out_trade_no");
            String tradeStatus = params.get("trade_status");

            if ("TRADE_SUCCESS".equals(tradeStatus)) {
                // TODO 更新订单状态为已支付
            }
            return "success";
        }

        return "fail";
    }


    @GetMapping("/return")
    public String test() throws Exception {
        return "hello";
    }
}

