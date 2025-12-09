package com.qiguliuxing.dts.wx.service;


import com.qiguliuxing.dts.db.domain.Order;

import java.util.List;

public interface OrderService {
    String createOrder(Order order);

    List<Order> queryAllOrder();

    Order queryByOrderNo(String orderNo);

    void update(Order order);

    Order queryById(Integer id);
}
