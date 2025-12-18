package com.qiguliuxing.dts.wx.service.impl;

import com.qiguliuxing.dts.db.dao.DtsOrderMapper;
import com.qiguliuxing.dts.db.dao.DtsUserMapper;
import com.qiguliuxing.dts.db.dao.ex.VipOrderMapper;
import com.qiguliuxing.dts.db.domain.DtsOrder;
import com.qiguliuxing.dts.db.domain.DtsUser;
import com.qiguliuxing.dts.db.domain.Order;
import com.qiguliuxing.dts.db.service.DtsUserService;
import com.qiguliuxing.dts.wx.service.OrderService;
import com.qiguliuxing.dts.wx.util.OrderNoUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private DtsUserService dtsUserService;

    @Resource
    private DtsOrderMapper dtsOrderMapper;

    @Resource
    private VipOrderMapper vipOrderMapper;


    @Override
    public String createOrder(DtsOrder dtsOrder) {
        List<DtsUser> dtsUsers = dtsUserService.queryByMobile(dtsOrder.getMobile());//先根据手机号查询用户信息
        DtsOrder newOrder = new DtsOrder();
        BeanUtils.copyProperties(dtsOrder,newOrder);
        //1.生成随机订单号
        String orderNo = OrderNoUtils.generateOrderNo();
        newOrder.setOrderSn(orderNo);//订单编号
        //创建时间和更新时间的时区不对
        newOrder.setAddTime(LocalDateTime.now());//订单创建时间
        newOrder.setUpdateTime(LocalDateTime.now());//订单更新时间
        newOrder.setConsignee(dtsUsers.get(0).getNickname() );

        //2.插入数据
        dtsOrderMapper.insertSelective(newOrder);
        return orderNo;
    }

    @Override
    public List<DtsOrder> queryAllOrder() {
        return dtsOrderMapper.queryAllOrder();
    }

    @Override
    public DtsOrder queryByOrderSn(String orderSn) {
        DtsOrder order = dtsOrderMapper.queryByOrderSn(orderSn);
        if (Objects.isNull(order)){
            return new DtsOrder();
        }else {
            return order;
        }

    }

    @Override
    public void update(DtsOrder order) {
        dtsOrderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public DtsOrder queryById(Integer id) {
        return dtsOrderMapper.selectByPrimaryKey(id);
    }
}
