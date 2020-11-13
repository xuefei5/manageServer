package com.simplemall.account.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.simplemall.micro.serv.common.bean.account.ZknhVillageConfig;

import java.util.List;

public interface IZknhVillageConfigService extends IService<ZknhVillageConfig> {

    /**
     * 查询村集体列表
     * @return
     * @throws Exception
     */
    public List<ZknhVillageConfig> getVillageList() throws Exception;
}
