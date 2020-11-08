package com.simplemall.offer.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.simplemall.micro.serv.common.bean.Result;
import com.simplemall.micro.serv.common.bean.offer.PmOffer;

/**
 * 商品服务
 */
public interface IPmOfferService extends IService<PmOffer> {

    /**
     * 查询全部商品信息
     * @return
     */
    public IPage<PmOffer> queryAllOfferInfo();
}
