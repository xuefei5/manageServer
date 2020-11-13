package com.simplemall.account.dal;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.simplemall.micro.serv.common.bean.account.ZknhVillageConfig;

import java.util.List;

public interface ZknhVillageConfigMapper extends BaseMapper<ZknhVillageConfig> {

    /**
     * 查询村集体列表
     * @return
     */
    public List<ZknhVillageConfig> getVillageList();
}
