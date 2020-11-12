package com.simplemall.account.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simplemall.account.service.IZknhMainConfigService;
import com.simplemall.account.service.IZknhVillageConfigService;
import com.simplemall.account.service.IZknhVillageDetailService;
import com.simplemall.account.service.IZknhVillageModelService;
import com.simplemall.micro.serv.common.bean.Result;
import com.simplemall.micro.serv.common.bean.account.ZknhMainConfig;
import com.simplemall.micro.serv.common.constant.CommonConstant;
import com.simplemall.micro.serv.common.system.query.QueryGenerator;
import com.simplemall.micro.serv.common.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 准康农惠微信配置服务
 * @author xuefei
 */
@Slf4j
@RestController
@RequestMapping("/zknh_wechat_config")
public class ZknhWeChatConfigController {

    @Autowired
    private IZknhMainConfigService zknhMainConfigService;

    @Autowired
    private IZknhVillageConfigService zknhVillageConfigService;

    @Autowired
    private IZknhVillageDetailService zknhVillageDetailService;

    @Autowired
    private IZknhVillageModelService zknhVillageModelService;

    /**
     * 微信端-查询主页背景图片服务
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getWeChatMainBack", method = RequestMethod.GET)
    public Result<String> queryWeChatMainBack() throws Exception{

        ZknhMainConfig zknhMainConfig = zknhMainConfigService.getWechatMainBack();

        return Result.OK(zknhMainConfig.getModalIcon());

    }

    /**
     * 微信端-查询主页模块
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getWeChatMainModel", method = RequestMethod.GET)
    public Result<List<ZknhMainConfig>> queryWeChatMainModel() throws Exception{

        List<ZknhMainConfig> retList =zknhMainConfigService.getWeChatMainModel();

        return Result.OK(retList);

    }
    /**
     * 后台管理系统查询-分页
     * @param zknhMainConfig
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     * @throws Exception
     * @author xuefei
     */
    @RequestMapping(value = "/wechatMainList", method = RequestMethod.GET)
    public Result<IPage<ZknhMainConfig>> queryWechatMainCon(ZknhMainConfig zknhMainConfig, @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                                            @RequestParam(name="pageSize", defaultValue="10") Integer pageSize, HttpServletRequest req) throws Exception{
        Result<IPage<ZknhMainConfig>> result = new Result<IPage<ZknhMainConfig>>();
        QueryWrapper<ZknhMainConfig> queryWrapper = QueryGenerator.initQueryWrapper(zknhMainConfig, req.getParameterMap());


        Page<ZknhMainConfig> page = new Page<ZknhMainConfig>(pageNo, pageSize);
        IPage<ZknhMainConfig> pageList = zknhMainConfigService.page(page, queryWrapper);

        result.setSuccess(true);
        result.setResult(pageList);
        log.info(pageList.toString());
        return result;
    }

    /**
     * 前端微信查询-全部
     * @return
     * @throws Exception
     * @author xuefei
     */
    @RequestMapping(value = "/wechatMainListAll", method = RequestMethod.GET)
    public Result<List<ZknhMainConfig>> queryWechatMainCon() throws Exception{

        List<ZknhMainConfig> list = zknhMainConfigService.list();

        Result<List<ZknhMainConfig>> result = new Result<>();

        result.setSuccess(true);
        result.setResult(list);
        return result;
    }

    /**
     * 主页配置增加
     * @param jsonObject
     * @param request
     * @return
     * @throws Exception
     */

    @RequestMapping(value = "/wechatMainConAdd", method = RequestMethod.POST)
    public Result<?> weChatMainConfigAdd(@RequestBody JSONObject jsonObject, HttpServletRequest request) throws Exception{
        Result<?> result = new Result<>();
        try{
            ZknhMainConfig zknhMainConfig = JSON.parseObject(jsonObject.toJSONString(), ZknhMainConfig.class);

            //获取创建人
            String token = request.getHeader(CommonConstant.X_ACCESS_TOKEN);
            String username = JwtUtil.getUsername(token);

            zknhMainConfig.setDoneUserName(username);
            zknhMainConfig.setDoneDate(new Date());

            zknhMainConfigService.save(zknhMainConfig);

            result.success("添加成功！");
        }catch (Exception e){
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }


}
