package com.qiguliuxing.dts.wx.web.vipConfig;

import com.qiguliuxing.dts.core.util.ResponseUtil;
import com.qiguliuxing.dts.db.domain.VipOrder;
import com.qiguliuxing.dts.wx.service.VipOrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wx/vipOrder")
@Validated
public class VipOrderController {
    @Autowired
    private VipOrderService vipOrderService;


    /**
     * 创建订单
     * @param vipOrder
     * @return
     */
    @PostMapping("/createVipOrder")
    @ApiOperation("购买会员创建订单")
    public Object createOrder(@RequestBody VipOrder vipOrder){
        vipOrderService.createOrder(vipOrder);
        return ResponseUtil.ok();
    }

    @GetMapping("/queryAllOrder")
    @ApiOperation("查询所有订单")
    public Object queryAllOrder(){
        List<VipOrder> orderList = vipOrderService.queryAllOrder();
        return ResponseUtil.ok(orderList);
    }


}
