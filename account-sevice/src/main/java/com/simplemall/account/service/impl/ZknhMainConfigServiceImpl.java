package com.simplemall.account.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simplemall.account.dal.ZknhMainConfigMapper;
import com.simplemall.account.service.IZknhMainConfigService;
import com.simplemall.micro.serv.common.bean.account.ZknhMainConfig;
import org.springframework.beans.factory.annotation.Autowired;

public class ZknhMainConfigServiceImpl extends ServiceImpl<ZknhMainConfigMapper, ZknhMainConfig> implements IZknhMainConfigService {

    @Autowired
    ZknhMainConfigMapper zknhMainConfigMapper;
    @Override
    public ZknhMainConfig getWechatMainBack() {

        return zknhMainConfigMapper.getWechatMainBack();
    }
}
