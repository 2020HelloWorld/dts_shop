package com.qiguliuxing.dts.wx.web.vipConfig;

import com.qiguliuxing.dts.core.util.ResponseUtil;
import com.qiguliuxing.dts.db.domain.VipConfig;
import com.qiguliuxing.dts.wx.service.VipConfigService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wx/vipConfig")
@Validated
public class VipConfigController {

    @Autowired
    private VipConfigService vipConfigService;

    /**
     * 查询所有会员信息
     * @return
     */
    @GetMapping("/queryAll")
    @ApiOperation("查询所有会员配置信息")
    public Object queryAll(){
        List<VipConfig> vipConfigList = vipConfigService.queryAll();
        return ResponseUtil.ok(vipConfigList);
    }

    /**
     * 添加会员信息
     * @param vipConfig
     * @return
     */
    @PostMapping("/add")
    @ApiOperation("添加会员信息")
    public Object add(@RequestBody VipConfig vipConfig){
        vipConfigService.add(vipConfig);
        return ResponseUtil.ok();
    }

    /**
     * 通过会员id删除或批量删除会员配置信息
     * @param idList  会员id，可以传多个
     * @return
     */
    @PostMapping("/delete")
    @ApiOperation("删除会员配置信息")
    public Object delete(@RequestBody List<Integer> idList){
        vipConfigService.delete(idList);
        return ResponseUtil.ok();
    }

    /**
     * 根据id更改会员配置信息
     * @param vipConfig
     * @return
     */
    @PostMapping("/update")
    @ApiOperation("更新会员配置信息")
    public Object update(@RequestBody VipConfig vipConfig){
        vipConfigService.update(vipConfig);
        return ResponseUtil.ok();
    }


}
