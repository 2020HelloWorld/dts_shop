package com.qiguliuxing.dts.wx.pay;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.qiguliuxing.dts.core.util.ResultUtil;
import com.qiguliuxing.dts.db.domain.DtsOrder;
import com.qiguliuxing.dts.db.domain.DtsUser;
import com.qiguliuxing.dts.db.domain.OrderDto;
import com.qiguliuxing.dts.db.domain.Order;
import com.qiguliuxing.dts.db.service.DtsUserService;
import com.qiguliuxing.dts.wx.service.OrderService;
import com.qiguliuxing.dts.wx.util.AliOSSUtil;
import com.qiguliuxing.dts.wx.util.OrderNoUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/pay/alipay")
public class AliPayController {

    @Resource
    private AlipayProperties aliPayProperties;
    @Resource
    private OrderService orderService;

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

        return form;
    }

    @PostMapping("/createOrder")
    @ApiOperation("创建支付订单")
    public Object createOrder(@RequestBody OrderDto orderDto) throws Exception {
        System.out.println("开始创建订单");
        //1.先根据前端传入的商品信息和用户信息创建订单
        List<DtsUser> dtsUsers = dtsUserService.queryByMobile(orderDto.getPhone());
        DtsOrder order = new DtsOrder();
        order.setUserId(dtsUsers.
                get(0).getId());
        order.setOrderStatus(DtsOrder.PAY_STATE_CREATED);
        order.setMobile(orderDto.getPhone());
        order.setSubject("订单标题");
        order.setMessage("随便写");
        order.setGoodsPrice(new BigDecimal(orderDto.getPrice()));
        String orderNo = orderService.createOrder(order);
        DtsOrder newOrder = orderService.queryByOrderSn(orderNo);
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

        //returnUrl
        String returnUrl = " http://192.168.1.12:3000/#/wxpay?type=" + orderDto.getType();
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", newOrder.getOrderSn());  //订单编号
        bizContent.put("total_amount", orderDto.getPrice());
        bizContent.put("subject", newOrder.getSubject());
        bizContent.put("product_code", "QUICK_WAP_WAY");
        bizContent.put("timeout_express","5m");

        request.setBizContent(bizContent.toJSONString());

        request.setReturnUrl(returnUrl); // 设置回调地址
        String form = alipayClient.pageExecute(request).getBody();
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
            String tradeNo = params.get("trade_no");//支付宝交易号
            String sellerId = params.get("seller_id");//卖家支付宝用户号
            String outTradeNo = params.get("out_trade_no");//商户订单号
            String tradeStatus = params.get("trade_status");//交易状态（TRADE_SUCCESS表示成功）
            //通过订单号查询对应订单进行状态更新
            DtsOrder order = orderService.queryByOrderSn(outTradeNo);
            if ("TRADE_SUCCESS".equals(tradeStatus)) {
                order.setOrderStatus(DtsOrder.PAY_STATE_SUCCESS);//更改订单状态为成功
                order.setTradeNo(tradeNo);//记录支付宝交易号
                order.setSuccessTime(LocalDateTime.now());//记录支付成功时间
                //最后更新订单数据
                orderService.update(order);
                return "success";
            }else if ("TRADE_FINISHED".equals(tradeStatus)) {
                order.setOrderStatus(DtsOrder.PAY_STATE_FAIL);
                order.setTradeNo(tradeNo);
                orderService.update(order);
                return "fail";
            }
        }
        return "fail";
    }


    @GetMapping("/return")
    public String test() throws Exception {
        return "hello";
    }

    /**
     * 上传文件
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/upload")
    public String testUpload(MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename();
        String fileName = null;//文件名（不唯一）
        if (originalFilename != null) {
            fileName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        InputStream inputStream = file.getInputStream();//文件流
        return AliOSSUtil.uploadFile(fileName,inputStream);
    }
}

