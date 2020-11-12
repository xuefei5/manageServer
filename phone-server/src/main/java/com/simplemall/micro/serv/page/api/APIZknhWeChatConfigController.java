package com.simplemall.micro.serv.page.api;

import com.simplemall.micro.serv.common.bean.Result;
import com.simplemall.micro.serv.common.bean.account.ZknhMainConfig;
import com.simplemall.micro.serv.page.client.ZknhWeChatConFeignClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "微信配置服务", tags = "微信配置")
@RestController
@RequestMapping("/api_zknh_wechat_config")
public class APIZknhWeChatConfigController {

    private Logger logger = LoggerFactory.getLogger(APIZknhWeChatConfigController.class);

    @Autowired
    ZknhWeChatConFeignClient zknhWeChatConFeignClient;

    @ApiOperation(value = "获取微信公众号首页背景图")
    @RequestMapping(value = "getWeChatMainBack", method = RequestMethod.GET)
    public Result<String> getWeChatMainBack() {
        return zknhWeChatConFeignClient.getWeChatMainBack();
    }

    @ApiOperation(value = "获取微信公众号首页背景图")
    @RequestMapping(value = "getWeChatMainModel", method = RequestMethod.GET)
    public Result<List<ZknhMainConfig>> getWeChatMainModel() {

        return zknhWeChatConFeignClient.getWeChatMainModel();
    }

}
