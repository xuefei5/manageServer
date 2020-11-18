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
import com.simplemall.micro.serv.common.bean.account.ZknhVillageConfig;
import com.simplemall.micro.serv.common.bean.account.ZknhVillageDetail;
import com.simplemall.micro.serv.common.bean.account.ZknhVillageModel;
import com.simplemall.micro.serv.common.constant.CommonConstant;
import com.simplemall.micro.serv.common.system.query.QueryGenerator;
import com.simplemall.micro.serv.common.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
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
     * 微信端-查询主页左上角图片服务
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getWeChatMainLeftImg", method = RequestMethod.GET)
    public Result<String> queryWeChatMainLeftImg() throws Exception{

        ZknhMainConfig zknhMainConfig = zknhMainConfigService.getWeChatMainLeftImg();

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
     * 村集体列表查询
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryVillageList", method = RequestMethod.GET)
    public Result<List<ZknhVillageConfig>> queryVillageList(@RequestParam(name="id",defaultValue = "") String villageId) throws Exception{

        //如果传入id，则是查镇下面的村列表，反之，查所有的镇
        List<ZknhVillageConfig> retList= null;
        if(StringUtils.isEmpty(villageId)){
            retList = zknhVillageConfigService.getVillagesList();
        }
        if(StringUtils.isNotEmpty(villageId)){
             retList = zknhVillageConfigService.getVillageList(villageId);
        }


        return Result.OK(retList);

    }

    /**
     * 根据村庄ID查询模块
     * @param villageId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryVillageModelById", method = RequestMethod.GET)
    public Result<List<ZknhVillageModel>> queryVillageModelById(@RequestParam(name="id",defaultValue = "") String villageId) throws Exception{

        List<ZknhVillageModel> retList = zknhVillageModelService.getVillageModelByVillageId(villageId);

        return Result.OK(retList);
    }

    /**
     * 根据模块ID查询模块详细信息
     * @param modelId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryVillageDetailByModelId", method = RequestMethod.GET)
    public Result<List<ZknhVillageDetail>> queryVillageDetailByModelId(@RequestParam(name="modelId",defaultValue = "") String modelId) throws Exception{

        List<ZknhVillageDetail> retList = zknhVillageDetailService.getVillageDetailByModelId(modelId);

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

    /**
     * 导航页背景图设置
     * @return
     * @throws Exception
     * @author wangshun
     */
    @RequestMapping(value = "/wechatMainUpdate", method = RequestMethod.POST)
    public Result<Object> updateModify(@RequestBody JSONObject jsonObject) throws Exception{
        Result<Object> result = new Result<>();
        String url = jsonObject.getString("url");
        if(url == null || "".equals(url)){
            result.error500("获取url为空");
            return result;
        }
        int i = zknhMainConfigService.updateModify(url);
        if(i==1){
            result.success("成功");
            result.setResult("0");
        }else {
            result.error500("失败");
            result.setResult("1");
        }
        return result;
    }
}
