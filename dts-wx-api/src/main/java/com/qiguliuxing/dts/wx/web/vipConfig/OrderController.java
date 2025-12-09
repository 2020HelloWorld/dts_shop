package com.qiguliuxing.dts.wx.web.vipConfig;

import com.qiguliuxing.dts.core.util.ResponseUtil;
import com.qiguliuxing.dts.db.domain.Order;
import com.qiguliuxing.dts.wx.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wx/order")
@Validated
public class OrderController {
    @Autowired
    private OrderService orderService;


    /**
     * 创建订单
     * @param order
     * @return
     */
    @PostMapping("/createOrder")
    @ApiOperation("创建订单")
    public Object createOrder(@RequestBody Order order){
        orderService.createOrder(order);
        return ResponseUtil.ok();
    }

    @GetMapping("/queryAllOrder")
    @ApiOperation("查询所有订单")
    public Object queryAllOrder(){
        List<Order> orderList = orderService.queryAllOrder();
        return ResponseUtil.ok(orderList);
    }


}
