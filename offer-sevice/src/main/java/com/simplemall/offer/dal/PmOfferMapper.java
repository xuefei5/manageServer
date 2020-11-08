package com.simplemall.offer.dal;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.simplemall.micro.serv.common.bean.offer.PmOffer;

public interface PmOfferMapper extends BaseMapper<PmOffer> {

    /**
     * 查询全部商品信息
     * @return
     */
    public IPage<PmOffer> getAllOfferInfo();
}
