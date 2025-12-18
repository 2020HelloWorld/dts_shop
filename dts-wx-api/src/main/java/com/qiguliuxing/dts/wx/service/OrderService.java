package com.qiguliuxing.dts.wx.service;


import com.qiguliuxing.dts.db.domain.DtsOrder;
import com.qiguliuxing.dts.db.domain.Order;

import java.util.List;

public interface OrderService {
    String createOrder(DtsOrder dtsOrder);

    List<DtsOrder> queryAllOrder();

    DtsOrder queryByOrderSn(String orderNo);

    void update(DtsOrder order);

    DtsOrder queryById(Integer id);
}
