package com.qiguliuxing.dts.wx.service;


import com.qiguliuxing.dts.db.domain.VipOrder;

import java.util.List;

public interface VipOrderService {
    void createOrder(VipOrder vipOrder);

    List<VipOrder> queryAllOrder();

    VipOrder queryByOrderNo(String orderNo);
}
