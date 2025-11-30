package com.qiguliuxing.dts.wx.service;

import com.qiguliuxing.dts.db.domain.VipConfig;

import java.util.List;

public interface VipConfigService {
    List<VipConfig> queryAll();

    void add(VipConfig vipConfig);

    void delete(List<Integer> idList);

    void update(VipConfig vipConfig);
}
