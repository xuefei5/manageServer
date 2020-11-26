package com.simplemall.account.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.simplemall.micro.serv.common.bean.account.ZknhVillageConfig;

import java.util.List;

public interface IZknhVillageConfigService extends IService<ZknhVillageConfig> {

    /**
     * 查询镇列表
     * @return
     * @throws Exception
     */
    public List<ZknhVillageConfig> getVillagesList() throws Exception;

    /**
     * 查询村集体列表
     * @return
     * @throws Exception
     */
    public List<ZknhVillageConfig> getVillageList(String villageId) throws Exception;

    /*删除微信模块*/
    public int deleteId(int id);
}
