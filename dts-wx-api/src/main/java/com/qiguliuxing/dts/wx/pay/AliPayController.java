package com.qiguliuxing.dts.wx.pay;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.qiguliuxing.dts.core.util.ResultUtil;
import com.qiguliuxing.dts.db.domain.DtsUser;
import com.qiguliuxing.dts.db.domain.OrderDto;
import com.qiguliuxing.dts.db.domain.VipOrder;
import com.qiguliuxing.dts.db.service.DtsUserService;
import com.qiguliuxing.dts.wx.service.VipOrderService;
import com.qiguliuxing.dts.wx.util.OrderNoUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/pay/alipay")
public class AliPayController {

    @Resource
    private AlipayProperties aliPayProperties;
    @Resource
    private VipOrderService vipOrderService;

    @Resource
    private DtsUserService dtsUserService;

    /**
     * 仅用于测试，无实际意义
     * @return
     * @throws Exception
     */
    @GetMapping("/createOrderTest")
    @ApiOperation("创建支付订单")
    public String createOrderTest() throws Exception {
        //1.先根据前端传入的商品信息和用户信息创建订单
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
        bizContent.put("out_trade_no", "TEST202501180099");
        bizContent.put("total_amount", 10);
        bizContent.put("subject", "测试订单");
        bizContent.put("product_code", "QUICK_WAP_WAY");
        bizContent.put("timeout_express","5m");

        request.setBizContent(bizContent.toJSONString());

        String form = alipayClient.pageExecute(request).getBody();

//        // form 是完整 HTML 表单，需要前端渲染
//        Map<String, Object> result = new HashMap<>();
//        result.put("payForm", form); // H5 页面可直接写入并自动提交
        return form;
    }

    @PostMapping("/createOrder")
    @ApiOperation("创建支付订单")
    public Object createOrder(@RequestBody OrderDto orderDto) throws Exception {
        //1.先根据前端传入的商品信息和用户信息创建订单
        List<DtsUser> dtsUsers = dtsUserService.queryByMobile(orderDto.getPhone());
        VipOrder order = new VipOrder();
        order.setUserId(dtsUsers.
                get(0).getId());
        order.setVipLevel(1);
        order.setState(0);
        order.setSubject("购买会员");
        order.setBody("cnv;r");
        order.setFeeAmount(Float.valueOf(orderDto.getPrice()));
        order.setUserNote("bcuievcwvcvwel");
        vipOrderService.createOrder(order);

        //VipOrder vipOrder = vipOrderService.queryByOrderNo(orderNo);
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
        //bizContent.put("out_trade_no", order.getOrderNo());
        bizContent.put("out_trade_no", OrderNoUtils.generateOrderNo());
        bizContent.put("total_amount", orderDto.getPrice());
        bizContent.put("subject", order.getSubject());
        bizContent.put("product_code", "QUICK_WAP_WAY");
        bizContent.put("timeout_express","5m");

        request.setBizContent(bizContent.toJSONString());

        String form = alipayClient.pageExecute(request).getBody();

    //        // form 是完整 HTML 表单，需要前端渲染
    //        Map<String, Object> result = new HashMap<>();
    //        result.put("payForm", form); // H5 页面可直接写入并自动提交
        return ResultUtil.ok(form);
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

