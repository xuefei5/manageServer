package com.simplemall.offer.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simplemall.micro.serv.common.bean.Result;
import com.simplemall.micro.serv.common.bean.account.SysUser;
import com.simplemall.micro.serv.common.system.query.QueryGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @Author scott
 * @since 2018-12-20
 */
@Slf4j
@RestController
@RequestMapping("/offer")
public class OfferTestController {

    @Value("${service.path.upload}")
    private String upLoadPath;
    /**
     * app获取商品列表数据
     * @return
     */
	@RequestMapping(value = "/phoneList", method = RequestMethod.GET)
	public Result<List> queryPageList(@RequestBody Map params) {
		Result<List> result = new Result<List>();

		Map map = new HashMap();
        map.put("OFFER_ID","111");
        map.put("OFFER_DESC","猕猴桃，产自湖北");
        map.put("OFFER_IMG1","图片地址1");

        Map map2 = new HashMap();
        map2.put("OFFER_ID","222");
        map2.put("OFFER_DESC","西瓜，产自北京");
        map2.put("OFFER_IMG1","图片地址1");

		List<Map> pageList = new ArrayList<Map>();
        pageList.add(map);
        pageList.add(map2);

		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
}
