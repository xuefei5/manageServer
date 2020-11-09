package com.simplemall.account.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.simplemall.micro.serv.common.bean.account.ZknhMainConfig;

public interface IZknhMainConfigService extends IService<ZknhMainConfig> {

    /**
     * 查询微信主页背景图
     * @return
     */
    public ZknhMainConfig getWechatMainBack();
}
