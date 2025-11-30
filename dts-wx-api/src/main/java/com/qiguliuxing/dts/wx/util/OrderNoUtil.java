package com.qiguliuxing.dts.wx.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderNoUtil {
    // 每秒最多生成 100000 个订单号，可以按需调大
    private static final AtomicInteger SEQ = new AtomicInteger(0);
    private static final int MAX = 99999;

    /**
     * 生成订单号：时间戳（14位） + 5位自增序列
     * 例如：2025113012101598765
     */
    public static String generateOrderNo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = sdf.format(new Date());

        int seq = SEQ.getAndIncrement();
        if (seq > MAX) {
            SEQ.set(0);
            seq = SEQ.getAndIncrement();
        }

        // 保证序列固定 5 位，不足补 0
        return time + String.format("%05d", seq);
    }
}
