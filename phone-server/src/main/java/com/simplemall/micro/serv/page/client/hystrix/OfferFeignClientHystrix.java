package com.simplemall.micro.serv.page.client.hystrix;

import com.simplemall.micro.serv.common.bean.Result;
import com.simplemall.micro.serv.page.client.OfferFeignClient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 生成空对象，防止出现Null，对调用方造成其他问题
 *
 * @author xuefei 2020年10月28日 下午10:02:55
 */
@Component
public class OfferFeignClientHystrix implements OfferFeignClient {
	@Override
	public Result<List> list(Map params) {
		return new Result<List>();
	}

}
