package com.simplemall.account.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simplemall.account.dal.ZknhVillageConfigMapper;
import com.simplemall.account.service.IZknhVillageConfigService;
import com.simplemall.micro.serv.common.bean.account.ZknhVillageConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class ZknhVillageConfigServiceImpl extends ServiceImpl<ZknhVillageConfigMapper, ZknhVillageConfig> implements IZknhVillageConfigService {

    @Resource
    ZknhVillageConfigMapper zknhVillageConfigMapper;

    @Override
    public List<ZknhVillageConfig> getVillagesList() throws Exception {

        return zknhVillageConfigMapper.getVillagesList();
    }

    @Override
    public List<ZknhVillageConfig> getVillageList(String villageId) throws Exception {

        return zknhVillageConfigMapper.getVillageList(villageId);
    }
    @Override
    /*删除微信模块*/
    public int deleteId(String id)throws Exception{
        return zknhVillageConfigMapper.deleteId(id);
    }
}
