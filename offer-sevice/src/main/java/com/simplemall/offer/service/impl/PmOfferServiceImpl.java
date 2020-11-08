package com.simplemall.offer.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simplemall.micro.serv.common.bean.Result;
import com.simplemall.micro.serv.common.bean.offer.PmOffer;
import com.simplemall.offer.dal.PmOfferMapper;
import com.simplemall.offer.service.IPmOfferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PmOfferServiceImpl extends ServiceImpl<PmOfferMapper, PmOffer> implements IPmOfferService {

    @Autowired
    private PmOfferMapper pmOfferMapper;

    @Override
    public IPage<PmOffer> queryAllOfferInfo() {
        return pmOfferMapper.getAllOfferInfo();
    }
}
