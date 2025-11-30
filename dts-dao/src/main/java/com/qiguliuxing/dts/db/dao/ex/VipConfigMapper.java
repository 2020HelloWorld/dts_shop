package com.qiguliuxing.dts.db.dao.ex;

import com.qiguliuxing.dts.db.domain.VipConfig;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface VipConfigMapper {
    @Select(value = "select * from dts_vip_config")
    List<VipConfig> queryAll();

    @Insert(value = "insert into dts_vip_config (name,level,sort,description,price)" +
            "values (#{name},#{level},#{sort},#{description},#{price})")
    void add(VipConfig vipConfig);

    void delete(@Param("idList") List<Integer> idList);

    void update(VipConfig vipConfig);
}
