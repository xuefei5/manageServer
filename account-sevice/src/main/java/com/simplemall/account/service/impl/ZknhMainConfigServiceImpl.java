package com.simplemall.account.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simplemall.account.dal.ZknhMainConfigMapper;
import com.simplemall.account.service.IZknhMainConfigService;
import com.simplemall.micro.serv.common.bean.account.ZknhMainConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class ZknhMainConfigServiceImpl extends ServiceImpl<ZknhMainConfigMapper, ZknhMainConfig> implements IZknhMainConfigService {

    @Resource
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
    /*修改微信背景图*/
    public int updateModify(String url) {

        return zknhMainConfigMapper.updateModify(url);
    }
    /* 查询微信模块*/
    public List<ZknhMainConfig> queryModule(Integer pageNo,Integer pageSize,ZknhMainConfig zknhMainConfig,String order) {
        //分页
        Integer pageNoOne = (pageNo-1)*pageSize;
        return zknhMainConfigMapper.queryModule(pageNoOne,pageSize,zknhMainConfig,order);
    }
    /*删除微信模块*/
    public int deleteId(int id){
        return zknhMainConfigMapper.deleteId(id);
    }
}
