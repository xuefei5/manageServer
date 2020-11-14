package com.simplemall.account.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simplemall.account.dal.ZknhVillageModelMapper;
import com.simplemall.account.service.IZknhVillageModelService;
import com.simplemall.micro.serv.common.bean.account.ZknhVillageModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ZknhVillageModelServiceImpl extends ServiceImpl<ZknhVillageModelMapper, ZknhVillageModel> implements IZknhVillageModelService {

    @Autowired
    ZknhVillageModelMapper zknhVillageModelMapper;

    @Override
    public List<ZknhVillageModel> getVillageModelByVillageId(String villageId) throws Exception {

        return zknhVillageModelMapper.getVillageModelByVillageId(villageId);
    }
}
