package com.simplemall.account.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simplemall.account.dal.ZknhVillageDetailMapper;
import com.simplemall.account.service.IZknhVillageDetailService;
import com.simplemall.micro.serv.common.bean.account.ZknhVillageDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ZknhVillageDetailServiceImpl extends ServiceImpl<ZknhVillageDetailMapper, ZknhVillageDetail> implements IZknhVillageDetailService {

    @Autowired
    ZknhVillageDetailMapper zknhVillageDetailMapper;
    @Override
    public List<ZknhVillageDetail> getVillageDetailByModelId(String modelId) throws Exception {
        return zknhVillageDetailMapper.getVillageDetailByModelId(modelId);
    }
}
