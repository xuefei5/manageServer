package com.simplemall.account.dal;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.simplemall.micro.serv.common.bean.account.ZknhMainConfig;

public interface ZknhMainConfigMapper extends BaseMapper<ZknhMainConfig> {

    /**
     * 查询主页背景图
     * @return
     */
    public ZknhMainConfig getWechatMainBack();


}
