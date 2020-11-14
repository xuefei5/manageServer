package com.simplemall.micro.serv.page.client;

import com.simplemall.micro.serv.common.bean.Result;
import com.simplemall.micro.serv.common.bean.account.ZknhMainConfig;
import com.simplemall.micro.serv.common.bean.account.ZknhVillageConfig;
import com.simplemall.micro.serv.common.bean.account.ZknhVillageDetail;
import com.simplemall.micro.serv.common.bean.account.ZknhVillageModel;
import com.simplemall.micro.serv.page.client.hystrix.ZknhWeChatConFeignClientHystrix;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * get product detail info
 *
 * @author xuefei
 *
 */
@FeignClient(name = "ACCOUNT-SERVICE",fallback = ZknhWeChatConFeignClientHystrix.class)
public interface ZknhWeChatConFeignClient {

	@RequestMapping(value = "/zknh_wechat_config/getWeChatMainBack",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	public Result<String> getWeChatMainBack();

	@RequestMapping(value = "/zknh_wechat_config/getWeChatMainModel",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	public Result<List<ZknhMainConfig>> getWeChatMainModel();

	@RequestMapping(value = "/zknh_wechat_config/getWeChatMainLeftImg",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	public Result<String> getWeChatMainLeftImg();

	@RequestMapping(value = "/zknh_wechat_config/queryVillageList",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	public Result<List<ZknhVillageConfig>> queryVillageList();

	@RequestMapping(value = "/zknh_wechat_config/queryVillageModelById",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	public Result<List<ZknhVillageModel>> queryVillageModelById(@RequestParam(name="id")String villageId);

	@RequestMapping(value = "/zknh_wechat_config/queryVillageDetailByModelId",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	public Result<List<ZknhVillageDetail>> queryVillageDetailByModelId(@RequestParam(name="modelId")String modelId);
}
