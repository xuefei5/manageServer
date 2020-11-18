package com.simplemall.micro.serv.page.api;

import com.simplemall.micro.serv.common.bean.Result;
import com.simplemall.micro.serv.common.bean.account.ZknhMainConfig;
import com.simplemall.micro.serv.common.bean.account.ZknhVillageConfig;
import com.simplemall.micro.serv.common.bean.account.ZknhVillageDetail;
import com.simplemall.micro.serv.common.bean.account.ZknhVillageModel;
import com.simplemall.micro.serv.page.client.ZknhWeChatConFeignClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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

    @ApiOperation(value = "获取微信公众号首页模块")
    @RequestMapping(value = "getWeChatMainModel", method = RequestMethod.GET)
    public Result<List<ZknhMainConfig>> getWeChatMainModel() {

        return zknhWeChatConFeignClient.getWeChatMainModel();
    }

    @ApiOperation(value = "获取微信公众号首页左上角图片")
    @RequestMapping(value = "getWeChatMainLeftImg", method = RequestMethod.GET)
    public Result<String> getWeChatMainLeftImg() {
        return zknhWeChatConFeignClient.getWeChatMainLeftImg();
    }

    @ApiOperation(value = "获取村集体列表")
    @RequestMapping(value = "queryVillageList", method = RequestMethod.GET)
    public Result<List<ZknhVillageConfig>> queryVillageList(@RequestParam(name="villageId") String villageId){

        return zknhWeChatConFeignClient.queryVillageList(villageId);
    }

    @ApiOperation(value = "获取村庄详情")
    @RequestMapping(value = "getVillageDeail", method = RequestMethod.GET)
    public Result<List<Map>> getVillageDeail(@RequestParam(name="villageId") String villageId){

        //获取村庄模块
        Result<List<ZknhVillageModel>> retModel = zknhWeChatConFeignClient.queryVillageModelById(villageId);

        List<ZknhVillageModel> modelList = retModel.getResult();

        List<Map> retList = new ArrayList<Map>();
        //拼接详情介绍
        for(ZknhVillageModel model : modelList){
            //查询模块详情
            Result<List<ZknhVillageDetail>> retDetail = zknhWeChatConFeignClient.queryVillageDetailByModelId(model.getId());
            List<ZknhVillageDetail> detailList = retDetail.getResult();

            Map retMap = new HashMap();
            retMap.put("modelName",model.getModelName());
            retMap.put("modelImg",model.getModelImg());
            retMap.put("modelContent",dealDetail(detailList));
            retList.add(retMap);
        }

        return Result.OK(retList);
    }

    /**
     * 处理详情，并且返回一个字符串
     * @return
     * @throws Exception
     */
    public String dealDetail(List<ZknhVillageDetail> detailList){
        StringBuffer sb = new StringBuffer();

        for(ZknhVillageDetail detail : detailList){
            //循环字段获取完整的详情
            for(int i=1 ; i<11 ; i++){
                String content = String.valueOf(detail.get(i)==null?"":detail.get(i));
                //如果一个为空说明后面的都为空，没必要过滤了
                if(StringUtils.isEmpty(content)){
                    break;
                }
                sb.append(detail.get(i));
            }
        }
        return sb.toString();
    }
}

