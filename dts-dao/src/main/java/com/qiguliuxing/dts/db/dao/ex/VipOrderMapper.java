package com.qiguliuxing.dts.db.dao.ex;

import com.qiguliuxing.dts.db.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface VipOrderMapper {
    void insert(Order order);

    @Select(value = "select * from dts_order")
    List<Order> queryAllOrder();

    @Select(value = "select * from dts_order where order_no = #{orderNo}")
    Order queryByOrderNo(String orderNo);

    void update(Order order);

    @Select(value = "select * from dts_order where id = #{id}")
    Order queryById(Integer id);
}
