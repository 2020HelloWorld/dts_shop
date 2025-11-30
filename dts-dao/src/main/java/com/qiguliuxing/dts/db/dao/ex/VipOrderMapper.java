package com.qiguliuxing.dts.db.dao.ex;

import com.qiguliuxing.dts.db.domain.VipOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface VipOrderMapper {
    void insert(VipOrder order);

    @Select(value = "select * from dts_order")
    List<VipOrder> queryAllOrder();

    @Select(value = "select * from dts_order where order_no = #{orderNo}")
    VipOrder queryByOrderNo(String orderNo);
}
