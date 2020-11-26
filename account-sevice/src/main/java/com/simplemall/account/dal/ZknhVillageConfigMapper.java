package com.simplemall.account.dal;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.simplemall.micro.serv.common.bean.account.ZknhVillageConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ZknhVillageConfigMapper extends BaseMapper<ZknhVillageConfig> {

    /**
     * 查询镇集体列表
     * @return
     */
    public List<ZknhVillageConfig> getVillagesList();
    /**
     * 查询村集体列表
     * @return
     */
    public List<ZknhVillageConfig> getVillageList(@Param("villageId") String villageId);
    /**
     * 删除模块
     * @return
     */
    public int deleteId(@Param("id") int id);
}
