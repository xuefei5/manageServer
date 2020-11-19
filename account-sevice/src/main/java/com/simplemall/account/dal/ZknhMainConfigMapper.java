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
    /**
     * 查询微信模块
     * @return
     */
    public List<ZknhMainConfig> queryModule(@Param("pageNoOne") Integer pageNo,@Param("pageSize") Integer pageSize,@Param("zknhMainConfig") ZknhMainConfig zknhMainConfig);

    /**
     * 删除模块
     * @return
     */
    public int deleteId(@Param("id") int id);
}

