package com.qiguliuxing.dts.wx.config;

import com.qiguliuxing.dts.core.storage.AliyunStorage;
import com.qiguliuxing.dts.wx.util.AliOSSUtil;
import org.springframework.stereotype.Component;

@Component
public class AliOSSBridge {

    public AliOSSBridge(AliyunStorage aliyunStorage) {
        // 关键一行
        AliOSSUtil.setAliyunStorage(aliyunStorage);
    }
}

