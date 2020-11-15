package com.simplemall.account.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.simplemall.micro.serv.common.bean.account.ZknhMainConfig;

import java.util.List;

public interface IZknhMainConfigService extends IService<ZknhMainConfig> {

    /**
     * 查询微信主页背景图
     * @return
     */
    public ZknhMainConfig getWechatMainBack();

    /**
     * 查询微信主页左上角图片
     * @return
     */
    public ZknhMainConfig getWeChatMainLeftImg();

    /**
     * 查询微信主页模块
     * @return
     */
    public List<ZknhMainConfig> getWeChatMainModel();

    /* 修改微信背景图*/
    public int updateModify(String url);
}
