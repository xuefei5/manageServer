package com.simplemall.micro.serv.page.api;

import com.simplemall.micro.serv.common.bean.RestAPIResult;
import com.simplemall.micro.serv.common.bean.Result;
import com.simplemall.micro.serv.common.bean.product.PrdInfo;
import com.simplemall.micro.serv.page.client.OfferFeignClient;
import com.simplemall.micro.serv.page.client.ProductFeignClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "商品服务", tags = "商品服务接口")
@RestController
@RequestMapping("/offer")
public class APIOfferController {

    private Logger logger = LoggerFactory.getLogger(APIOfferController.class);

    @Autowired
    OfferFeignClient offerFeignClient;

    @ApiOperation(value = "商品列表展示")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Result<List> list(@RequestParam("offerType") String offerType) {

        Map params = new HashMap();
        params.put("offerType",offerType);
        Result<List> products = offerFeignClient.list(params);
        return products;
    }
}
