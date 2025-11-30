package com.qiguliuxing.dts.wx.service.impl;

import com.qiguliuxing.dts.db.dao.ex.VipConfigMapper;
import com.qiguliuxing.dts.db.domain.VipConfig;
import com.qiguliuxing.dts.wx.service.VipConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class VipConfigServiceImpl implements VipConfigService {
    @Resource
    private VipConfigMapper vipConfigMapper;
    @Override
    public List<VipConfig> queryAll() {
        return vipConfigMapper.queryAll();
    }

    @Override
    public void add(VipConfig vipConfig) {
        vipConfigMapper.add(vipConfig);
    }

    @Override
    public void delete(List<Integer> idList) {
        vipConfigMapper.delete(idList);
    }

    @Override
    public void update(VipConfig vipConfig) {
        vipConfigMapper.update(vipConfig);
    }
}
