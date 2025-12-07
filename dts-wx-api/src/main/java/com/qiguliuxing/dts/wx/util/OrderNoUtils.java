package com.qiguliuxing.dts.wx.util;

public class OrderNoUtils {

    // 生成订单号：TEST + yyyyMMdd + 4位随机数
    public static String generateOrderNo() {
        String prefix = "TEST";

        // 当前日期（年月日）
        String date = new java.text.SimpleDateFormat("yyyyMMdd")
                .format(new java.util.Date());

        // 4 位随机数字，不足补 0
        int randomNum = (int)(Math.random() * 10000);
        String randomStr = String.format("%04d", randomNum);

        return prefix + date + randomStr;
    }

    public static void main(String[] args) {
        System.out.println(generateOrderNo());
    }
}

