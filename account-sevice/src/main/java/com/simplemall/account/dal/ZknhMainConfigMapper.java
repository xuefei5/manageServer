package com.simplemall.account.dal;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.simplemall.micro.serv.common.bean.account.ZknhMainConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ZknhMainConfigMapper extends BaseMapper<ZknhMainConfig> {

    /**
     * 查询主页背景图
     * @return
     */
    public ZknhMainConfig getWechatMainBack();

    /**
     * 查询左上角背景图
     * @return
     */
    public ZknhMainConfig getWeChatMainLeftImg();

    public List<ZknhMainConfig> getWeChatMainModel();

    /**
     * 修改主页背景图
     * @return
     */
    public int updateModify(@Param("url") String url);
}

