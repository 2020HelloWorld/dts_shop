package com.qiguliuxing.dts.wx.service.impl;

import com.qiguliuxing.dts.db.dao.ex.VipOrderMapper;
import com.qiguliuxing.dts.db.domain.VipOrder;
import com.qiguliuxing.dts.wx.service.VipOrderService;
import com.qiguliuxing.dts.wx.util.OrderNoUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class VipOrderServiceImpl implements VipOrderService {
    @Autowired
    private VipOrderMapper vipOrderMapper;

    @Override
    public void createOrder(VipOrder vipOrder) {
        VipOrder order = new VipOrder();
        BeanUtils.copyProperties(vipOrder,order);
        //1.生成随机订单号
        order.setOrderNo(OrderNoUtil.generateOrderNo());
        //2.插入数据
        vipOrderMapper.insert(order);
    }

    @Override
    public List<VipOrder> queryAllOrder() {
        return vipOrderMapper.queryAllOrder();
    }

    @Override
    public VipOrder queryByOrderNo(String orderNo) {
        VipOrder vipOrder = vipOrderMapper.queryByOrderNo(orderNo);
        if (Objects.isNull(vipOrder)){
            return new VipOrder();
        }else {
            return vipOrder;
        }

    }
}
