package com.simplemall.account.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.simplemall.micro.serv.common.bean.account.ZknhVillageModel;

import java.util.List;

public interface IZknhVillageModelService extends IService<ZknhVillageModel> {

    public List<ZknhVillageModel> getVillageModelByVillageId(String villageId) throws Exception;
}
