package com.qiguliuxing.dts.wx.service.impl;

import com.qiguliuxing.dts.db.dao.ex.VipOrderMapper;
import com.qiguliuxing.dts.db.domain.Order;
import com.qiguliuxing.dts.wx.service.OrderService;
import com.qiguliuxing.dts.wx.util.OrderNoUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private VipOrderMapper vipOrderMapper;

    @Override
    public String createOrder(Order order) {
        Order newOrder = new Order();
        BeanUtils.copyProperties(order,newOrder);
        //1.生成随机订单号
        String orderNo = OrderNoUtils.generateOrderNo();
        newOrder.setOrderNo(orderNo);
        //2.插入数据
        vipOrderMapper.insert(newOrder);
        return orderNo;
    }

    @Override
    public List<Order> queryAllOrder() {
        return vipOrderMapper.queryAllOrder();
    }

    @Override
    public Order queryByOrderNo(String orderNo) {
        Order order = vipOrderMapper.queryByOrderNo(orderNo);
        if (Objects.isNull(order)){
            return new Order();
        }else {
            return order;
        }

    }

    @Override
    public void update(Order order) {
        vipOrderMapper.update(order);
    }

    @Override
    public Order queryById(Integer id) {
        return vipOrderMapper.queryById(id);
    }
}
