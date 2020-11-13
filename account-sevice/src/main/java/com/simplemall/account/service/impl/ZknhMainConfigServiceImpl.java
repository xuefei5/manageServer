package com.simplemall.account.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simplemall.account.dal.ZknhMainConfigMapper;
import com.simplemall.account.service.IZknhMainConfigService;
import com.simplemall.micro.serv.common.bean.account.ZknhMainConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ZknhMainConfigServiceImpl extends ServiceImpl<ZknhMainConfigMapper, ZknhMainConfig> implements IZknhMainConfigService {

    @Autowired
    ZknhMainConfigMapper zknhMainConfigMapper;
    @Override
    public ZknhMainConfig getWechatMainBack() {

        return zknhMainConfigMapper.getWechatMainBack();
    }

    @Override
    public ZknhMainConfig getWeChatMainLeftImg() {

        return zknhMainConfigMapper.getWeChatMainLeftImg();
    }

    @Override
    public List<ZknhMainConfig> getWeChatMainModel() {

        return zknhMainConfigMapper.getWeChatMainModel();
    }
}
