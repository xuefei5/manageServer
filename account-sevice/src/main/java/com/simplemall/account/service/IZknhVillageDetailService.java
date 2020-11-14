package com.simplemall.account.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.simplemall.micro.serv.common.bean.account.ZknhVillageDetail;

import java.util.List;

public interface IZknhVillageDetailService extends IService<ZknhVillageDetail> {

    public List<ZknhVillageDetail> getVillageDetailByModelId(String modelId) throws Exception;
}
