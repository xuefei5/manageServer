package com.simplemall.micro.serv.page.client.hystrix;

import com.simplemall.micro.serv.common.bean.Result;
import com.simplemall.micro.serv.common.bean.account.ZknhMainConfig;
import com.simplemall.micro.serv.common.bean.account.ZknhVillageConfig;
import com.simplemall.micro.serv.common.bean.account.ZknhVillageDetail;
import com.simplemall.micro.serv.common.bean.account.ZknhVillageModel;
import com.simplemall.micro.serv.page.client.ZknhWeChatConFeignClient;
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
public class ZknhWeChatConFeignClientHystrix implements ZknhWeChatConFeignClient {
	@Override
	public Result<String> getWeChatMainBack() {

		return new Result<String>();
	}

	@Override
	public Result<List<ZknhMainConfig>> getWeChatMainModel() {

		return  Result.OK();
	}

	@Override
	public Result<String> getWeChatMainLeftImg() {

		return new Result<String>();
	}

	@Override
	public Result<List<ZknhVillageConfig>> queryVillageList(String villageId) {

		return  Result.OK();
	}

	@Override
	public Result<List<ZknhVillageModel>> queryVillageModelById(String villageId) {

		return  Result.OK();
	}

	@Override
	public Result<List<ZknhVillageDetail>> queryVillageDetailByModelId(String modelId) {

		return  Result.OK();
	}

}
