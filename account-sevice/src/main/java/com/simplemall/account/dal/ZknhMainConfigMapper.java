package com.simplemall.account.dal;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.simplemall.micro.serv.common.bean.account.ZknhMainConfig;

import java.util.List;

public interface ZknhMainConfigMapper extends BaseMapper<ZknhMainConfig> {

    /**
     * 查询主页背景图
     * @return
     */
    public ZknhMainConfig getWechatMainBack();

    public List<ZknhMainConfig> getWeChatMainModel();


}
