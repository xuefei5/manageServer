package com.simplemall.offer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simplemall.micro.serv.common.bean.Result;
import com.simplemall.micro.serv.common.bean.account.SysUser;
import com.simplemall.micro.serv.common.bean.offer.PmOffer;
import com.simplemall.micro.serv.common.constant.CommonConstant;
import com.simplemall.micro.serv.common.system.query.QueryGenerator;
import com.simplemall.micro.serv.common.util.JwtUtil;
import com.simplemall.micro.serv.common.util.PasswordUtil;
import com.simplemall.micro.serv.common.util.PriceUtils;
import com.simplemall.micro.serv.common.util.oConvertUtils;
import com.simplemall.offer.service.IPmOfferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/offer")
public class PmOfferController {

    @Autowired
    private IPmOfferService pmOfferService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<IPage<PmOffer>> queryOfferInfo(PmOffer pmOffer, @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                                   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize, HttpServletRequest req){

        Result<IPage<PmOffer>> result = new Result<IPage<PmOffer>>();
        QueryWrapper<PmOffer> queryWrapper = QueryGenerator.initQueryWrapper(pmOffer, req.getParameterMap());
        //TODO 外部模拟登陆临时账号，列表不显示
        //queryWrapper.ne("offer_name","_reserve_user_external");
        Page<PmOffer> page = new Page<PmOffer>(pageNo, pageSize);
        IPage<PmOffer> pageList = pmOfferService.page(page, queryWrapper);

        //设置金额元
        List<PmOffer> pageRecords = pageList.getRecords();
        for(PmOffer pageRecord : pageRecords){
            pageRecord.setOfferPrice(PriceUtils.changeF2Y(pageRecord.getOfferPrice()));
        }
        pageList.setRecords(pageRecords);


        result.setSuccess(true);
        result.setResult(pageList);
        log.info(pageList.toString());
        return result;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<PmOffer> add(@RequestBody JSONObject jsonObject,HttpServletRequest request) {
        Result<PmOffer> result = new Result<PmOffer>();
        try {
            PmOffer pmOffer = JSON.parseObject(jsonObject.toJSONString(), PmOffer.class);

            //获取创建人
            String token = request.getHeader(CommonConstant.X_ACCESS_TOKEN);
            String username = JwtUtil.getUsername(token);

            //生成offer_id
            String offerId = UUID.randomUUID().toString().replace("-", "");

            pmOffer.setCreateTime(new Date());//设置创建时间
            pmOffer.setStatus(1);//默认生效
            pmOffer.setOfferId(offerId);//
            pmOffer.setCreateUserName(username);
            pmOffer.setOfferPrice(pmOffer.getOfferPrice() * 100);

            pmOfferService.save(pmOffer);
            result.success("添加成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public Result<PmOffer> edit(@RequestBody JSONObject jsonObject) {
        Result<PmOffer> result = new Result<PmOffer>();
        try {
            PmOffer pmOffer = pmOfferService.getById(jsonObject.getString("id"));
            if(pmOffer==null) {
                result.error500("未找到对应实体");
            }else {
                PmOffer pmOffer1 = JSON.parseObject(jsonObject.toJSONString(), PmOffer.class);
                pmOfferService.saveOrUpdate(pmOffer1);
                result.success("修改成功!");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }

    /**
     * 删除用户
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Result<?> delete(@RequestParam(name="id",required=true) String id) {
        this.pmOfferService.removeById(id);
        return Result.ok("删除商品成功");
    }

    /**
     * 批量删除用户
     */
    //@RequiresRoles({"admin"})
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.DELETE)
    public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
        this.pmOfferService.removeByIds(Collections.singleton(ids));
        return Result.ok("批量删除商品成功");
    }

    /**
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/frozenBatch", method = RequestMethod.PUT)
    public Result<PmOffer> frozenBatch(@RequestBody JSONObject jsonObject) {
        Result<PmOffer> result = new Result<PmOffer>();
        try {
            String ids = jsonObject.getString("ids");
            String status = jsonObject.getString("status");
            String[] arr = ids.split(",");
            for (String id : arr) {
                if(oConvertUtils.isNotEmpty(id)) {
                    this.pmOfferService.update(new PmOffer().setStatus(Integer.parseInt(status)),
                            new UpdateWrapper<PmOffer>().lambda().eq(PmOffer::getOfferId,id));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败"+e.getMessage());
        }
        result.success("操作成功!");
        return result;

    }
}
