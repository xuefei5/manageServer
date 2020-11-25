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
import com.simplemall.micro.serv.common.bean.offer.PmOffer;
import com.simplemall.micro.serv.common.constant.CommonConstant;
import com.simplemall.micro.serv.common.system.query.QueryGenerator;
import com.simplemall.micro.serv.common.util.JwtUtil;
import com.simplemall.micro.serv.common.util.PriceUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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

    private final String villagesIconUrl = "main_model_default.png";//镇的默认图标地址
    private final String villageBackUrl = "village_model_2.png";//村的默认背景图

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
        String url = String.valueOf(jsonObject.getString("url"));
        if(url =="[]" || "".equals(url)){
            result.error500("未获取到图片/图标");
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
    /**
     * 导航页模块查询设置
     * @return
     * @throws Exception
     * @author wangshun
     */
    @RequestMapping(value = "/queryModule", method = RequestMethod.GET)
    public Result<?> queryModule( @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,ZknhMainConfig zknhMainConfig, HttpServletRequest req){
        Result<Map<String,Object>> result = new Result<Map<String,Object>>();
        List<ZknhMainConfig> pageList = zknhMainConfigService.queryModule(pageNo, pageSize,zknhMainConfig);
        Map<String,Object> pageMap = new HashMap<>();
        pageMap.put("records",pageList);
        pageMap.put("pages",pageNo-1);
        pageMap.put("size",pageSize);
        pageMap.put("current",pageNo );
        pageMap.put("hitCount",false );
        pageMap.put("optimizeCountSql",true );
        pageMap.put("searchCount",true );
        pageMap.put("orders",new ArrayList<>() );
        pageMap.put("total",0 );
        result.setSuccess(true);
        result.setResult(pageMap);
        log.info(pageMap.toString());
        return result;
    }
    /**
     * 导航页模块修改设置
     * @return
     * @throws Exception
     * @author wangshun
     */
    @RequestMapping(value = "/editModule", method = RequestMethod.POST)
    public Result<ZknhMainConfig> editModule(@RequestBody ZknhMainConfig zknhMainConfig){
        Result<ZknhMainConfig> result = new Result<ZknhMainConfig>();
        ZknhMainConfig update = zknhMainConfigService.getById(zknhMainConfig.getId());
        if(update==null) {
            result.error500("未找到对应实体");
        }else {
            boolean ok = zknhMainConfigService.updateById(zknhMainConfig);
            //TODO 返回false说明什么？
            if(ok) {
                result.success("修改成功!");
            }else{
                result.error500("修改失败");
            }
        }
        return result;
    }
    /**
     * 导航页模块删除设置
     * @return
     * @throws Exception
     * @author wangshun
     */
    @RequestMapping(value = "/deleteModule", method = RequestMethod.DELETE)
    public Result<?> deleteModule(@RequestParam(name="id",required=true) int id){
        Result<?> result = new Result<>();
        try {
            int delete = zknhMainConfigService.deleteId(id);
            if (delete > 0) {
                return Result.ok("删除模块成功");
            } else {
                return result.error500("失败");
            }
        }catch (Exception e){
            log.error("失败"+e);
            return result.error500("删除失败");
        }
    }
    /**
     * 导航页模块新增设置
     * @return
     * @throws Exception
     * @author wangshun
     */
    @RequestMapping(value = "/addModule", method = RequestMethod.POST)
    public Result<ZknhMainConfig> addModule(@RequestBody ZknhMainConfig zknhMainConfig,HttpServletRequest request){
        Result<ZknhMainConfig> result = new Result<ZknhMainConfig>();
        try {
            //获取创建人
            String token = request.getHeader(CommonConstant.X_ACCESS_TOKEN);
            String username = JwtUtil.getUsername(token);
            zknhMainConfig.setDoneUserName(username);//最后一次修改人
            zknhMainConfig.setDoneDate(new Date());//最后一次修改时间
            zknhMainConfigService.save(zknhMainConfig);
            result.success("添加成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }
    /**
     * 批量删除商品配置
     */
    //@RequiresRoles({"admin"})
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.DELETE)
    public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
        this.zknhMainConfigService.removeByIds(Collections.singleton(ids));
        return Result.ok("批量删除商品成功");
    }
    /**
     * 村镇查询分页
     * @return
     * @throws Exception
     * @author wangshun
     */
    @RequestMapping(value = "/queryTownsVillages", method = RequestMethod.GET)
    public Result<?> queryTownsVillages( @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,ZknhVillageConfig zknhVillageConfig, HttpServletRequest req){

        Result<IPage<ZknhVillageConfig>> result = new Result<IPage<ZknhVillageConfig>>();
        //1.镇，2.村
        QueryWrapper<ZknhVillageConfig> queryWrapper = QueryGenerator.initQueryWrapper(zknhVillageConfig, req.getParameterMap());
        //TODO 外部模拟登陆临时账号，列表不显示
        //queryWrapper.ne("offer_name","_reserve_user_external");
        Page<ZknhVillageConfig> page = new Page<ZknhVillageConfig>(pageNo, pageSize);
        IPage<ZknhVillageConfig> pageList = zknhVillageConfigService.page(page, queryWrapper);

        result.setSuccess(true);
        result.setResult(pageList);
        log.info(pageList.toString());
        return result;
    }

    /**
     * 添加村或者镇
     * @param reqMap
     * @return
     */
    @RequestMapping(value = "/addVillages", method = RequestMethod.POST)
    public Result<?> addVillages(@RequestBody JSONObject reqMap){
        Result ret = new Result();
        try{
            ZknhVillageConfig zknhVillageConfig = JSON.parseObject(reqMap.toJSONString(),ZknhVillageConfig.class);
            //获取图标路径或者背景图路径
            String iconUrl = MapUtils.getString(reqMap,"fileList");

            //生成主键
            String id = UUID.randomUUID().toString().replace("-", "");

            if(StringUtils.isEmpty(iconUrl)){
                if("1".equals(zknhVillageConfig.getVillageType())){
                    iconUrl = villagesIconUrl;//此处是默认图标图
                }
                if("2".equals(zknhVillageConfig.getVillageType())){
                    iconUrl = villageBackUrl;//此处是默认背景图
                }
            }
            zknhVillageConfig.setVillageBack(iconUrl);
            zknhVillageConfig.setId(id);

            zknhVillageConfigService.save(zknhVillageConfig);
            ret.success("添加成功");
        }catch (Exception e){
            log.error(e.getMessage(), e);
            ret.error500("操作失败");
        }
        return ret;
    }

    /**
     * 修改村或者镇
     * @param reqMap
     * @return
     */
    @RequestMapping(value = "/editVillages", method = RequestMethod.POST)
    public Result<?> editVillages(@RequestBody JSONObject reqMap){
        Result ret = new Result();
        try{
            ZknhVillageConfig zknhVillageConfig = JSON.parseObject(reqMap.toJSONString(),ZknhVillageConfig.class);
            //获取图标路径或者背景图路径
            String iconUrl = MapUtils.getString(reqMap,"fileList");

            if(StringUtils.isEmpty(iconUrl)){
                if("1".equals(zknhVillageConfig.getVillageType())){
                    iconUrl = villagesIconUrl;//此处是默认图标图
                }
                if("2".equals(zknhVillageConfig.getVillageType())){
                    iconUrl = villageBackUrl;//此处是默认背景图
                }
            }
            zknhVillageConfig.setVillageBack(iconUrl);

            zknhVillageConfigService.saveOrUpdate(zknhVillageConfig);
            ret.success("修改成功");
        }catch (Exception e){
            log.error(e.getMessage(), e);
            ret.error500("修改失败");
        }
        return ret;
    }

    /**
     * 删除村镇
     */
    @RequestMapping(value = "/deleteVillages", method = RequestMethod.DELETE)
    public Result<?> delete(@RequestParam(name="id",required=true) String id) {
        this.zknhVillageConfigService.removeById(id);
        return Result.ok("删除信息成功");
    }

    /**
     * 查询村模块
     * @param pageNo
     * @param pageSize
     * @param zknhVillageModel
     * @param req
     * @return
     */
    @RequestMapping(value = "/queryVillageModel", method = RequestMethod.GET)
    public Result<?> queryVillageModel(@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                      @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,ZknhVillageModel zknhVillageModel, HttpServletRequest req){
        Result<IPage<ZknhVillageModel>> result = new Result<IPage<ZknhVillageModel>>();
        //1.镇，2.村
        QueryWrapper<ZknhVillageModel> queryWrapper = QueryGenerator.initQueryWrapper(zknhVillageModel, req.getParameterMap());
        //TODO 外部模拟登陆临时账号，列表不显示
        //queryWrapper.ne("offer_name","_reserve_user_external");
        Page<ZknhVillageModel> page = new Page<ZknhVillageModel>(pageNo, pageSize);
        IPage<ZknhVillageModel> pageList = zknhVillageModelService.page(page, queryWrapper);

        result.setSuccess(true);
        result.setResult(pageList);
        log.info(pageList.toString());
        return result;
    }


    /**
     * 添加模块
     * @param reqMap
     * @return
     */
    @RequestMapping(value = "/addVillageModel", method = RequestMethod.POST)
    public Result<?> addVillageModel(@RequestBody JSONObject reqMap){
        Result ret = new Result();
        try{
            ZknhVillageModel zknhVillageModel = JSON.parseObject(reqMap.toJSONString(),ZknhVillageModel.class);
            //获取背景图路径
            String modelUrl = MapUtils.getString(reqMap,"fileList");

            //生成主键
            String id = UUID.randomUUID().toString().replace("-", "");
            zknhVillageModel.setModelImg(modelUrl);
            zknhVillageModel.setId(id);

            //处理详情
            String detail = reqMap.getString("DETAIL");
            dealModelDetail4Add(zknhVillageModel.getId(),detail);

            zknhVillageModelService.save(zknhVillageModel);
            ret.success("添加成功");
        }catch (Exception e){
            log.error(e.getMessage(), e);
            ret.error500("操作失败");
        }
        return ret;
    }

    /**
     * 处理增加详情
     * @param id
     * @param detail
     */
    private void dealModelDetail4Add(String id, String detail) {
        int length = 1000;
        //定义循环次数(对1000取余，如果余数大于0，则增加1)
        int contentNum = detail.length()%length>0?detail.length()+1:detail.length();
        List<ZknhVillageDetail> zknhVillageDetails = new ArrayList<ZknhVillageDetail>();
        ZknhVillageDetail zknhVillageDetail = null;
        for(int i=0;i<=contentNum;i++){
            //如果是第一次循环或者以后的每十次都增加一行
            if(i/9==0||i==0){
                zknhVillageDetail = new ZknhVillageDetail();
                zknhVillageDetail.setModelId(id);
                zknhVillageDetail.setSort((i+1));

                zknhVillageDetails.add(zknhVillageDetail);
            }
            //截取字符串
            zknhVillageDetail.set((i+1),detail.substring(i*length,(i+1)*length));
        }
        zknhVillageDetailService.saveBatch(zknhVillageDetails);
    }

}
