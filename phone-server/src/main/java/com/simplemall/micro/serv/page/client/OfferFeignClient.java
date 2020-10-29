package com.simplemall.micro.serv.page.client;

import com.simplemall.micro.serv.common.bean.Result;
import com.simplemall.micro.serv.page.client.hystrix.OfferFeignClientHystrix;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * get product detail info
 *
 * @author xuefei
 *
 */
@FeignClient(name = "OFFER-SERVICE",fallback = OfferFeignClientHystrix.class)
public interface OfferFeignClient {

	@RequestMapping(value = "/offer/phoneList",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	public Result<List> list(@RequestBody Map params);
}
