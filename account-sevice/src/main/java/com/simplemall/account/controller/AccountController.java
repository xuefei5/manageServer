package com.simplemall.account.controller;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.simplemall.micro.serv.common.bean.RestAPIResult;
import com.simplemall.micro.serv.common.bean.Result;
import com.simplemall.micro.serv.common.bean.account.model.SysLoginModel;
import com.simplemall.micro.serv.common.constant.CommonConstant;
import com.simplemall.micro.serv.common.service.JedisUtil;
import com.simplemall.micro.serv.common.util.JwtUtil;
import com.simplemall.micro.serv.common.util.MD5Util;
import com.simplemall.micro.serv.common.util.RandImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.simplemall.account.service.IAccountService;
import com.simplemall.micro.serv.common.bean.account.Account;
import com.simplemall.micro.serv.common.constant.SystemConstants;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 个人账户
 *
 * @author guooo
 *
 */
@RestController
@RequestMapping("/loginService")
public class AccountController {

    private static final String BASE_CHECK_CODES = "qwertyuiplkjhgfdsazxcvbnmQWERTYUPLKJHGFDSAZXCVBNM1234567890";

	@Autowired
	IAccountService accountService;

	/**
	 * 登陆
	 *
	 * @param phone
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "login", method = {RequestMethod.GET,RequestMethod.POST})
	public Result<JSONObject> login(@RequestBody SysLoginModel sysLoginModel) {
		Result<JSONObject> result = new Result<JSONObject>();
		String username = sysLoginModel.getUsername();
		String password = sysLoginModel.getPassword();
		String captcha = sysLoginModel.getCaptcha();
		//校验验证码
		if(captcha==null){
			result.error500("验证码无效");
			return result;
		}
		String lowerCaseCaptcha = captcha.toLowerCase();
		String realKey = MD5Util.MD5Encode(lowerCaseCaptcha+sysLoginModel.getCheckKey(), "utf-8");
		Object checkCode = JedisUtil.STRINGS.get(realKey);
		//当进入登录页时，有一定几率出现验证码错误 #1714
		if(checkCode==null || !checkCode.toString().equals(lowerCaseCaptcha)) {
			result.error500("验证码错误");
			return result;
		}

		Account accInfo = accountService.login(username, password);
		if(null == accInfo){
			result.error500("用户名或密码错误");
			return result;
		}

		//用户登录信息
		userInfo(accInfo, result);
		return result;
	}

	/**
	 * 用户信息
	 *
	 * @param sysUser
	 * @param result
	 * @return
	 */
	private Result<JSONObject> userInfo(Account sysUser, Result<JSONObject> result) {
		String syspassword = sysUser.getPassword();
		String username = sysUser.getPhone();
		// 生成token
		String token = JwtUtil.sign(username, syspassword);
		// 设置token缓存有效时间
		//JedisUtil.STRINGS.set(CommonConstant.PREFIX_USER_TOKEN + token, token);
		JedisUtil.STRINGS.setEx(CommonConstant.PREFIX_USER_TOKEN + token, (int) (JwtUtil.EXPIRE_TIME*2 / 1000),token);

		// 获取用户部门信息
		JSONObject obj = new JSONObject();
		/*List<SysDepart> departs = sysDepartService.queryUserDeparts(sysUser.getId());
		obj.put("departs", departs);
		if (departs == null || departs.size() == 0) {
			obj.put("multi_depart", 0);
		} else if (departs.size() == 1) {
			sysUserService.updateUserDepart(username, departs.get(0).getOrgCode());
			obj.put("multi_depart", 1);
		} else {
			//查询当前是否有登录部门
			// update-begin--Author:wangshuai Date:20200805 for：如果用戶为选择部门，数据库为存在上一次登录部门，则取一条存进去
			SysUser sysUserById = sysUserService.getById(sysUser.getId());
			if(oConvertUtils.isEmpty(sysUserById.getOrgCode())){
				sysUserService.updateUserDepart(username, departs.get(0).getOrgCode());
			}
			// update-end--Author:wangshuai Date:20200805 for：如果用戶为选择部门，数据库为存在上一次登录部门，则取一条存进去
			obj.put("multi_depart", 2);
		}*/
		obj.put("token", token);
		obj.put("userInfo", sysUser);
		//obj.put("sysAllDictItems", sysDictService.queryAllDictItems());
		result.setResult(obj);
		result.success("登录成功");
		return result;
	}

	/**
	 * 注册
	 *
	 * @param phone
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "signup", method = RequestMethod.GET)
	public String signup(String phone, String password) {
		boolean result = accountService.signup(phone, password);
		return result ? SystemConstants.Code.SUCCESS : SystemConstants.Code.FAIL;
	}


    /**
     * 后台生成图形验证码 ：有效
     * @param response
     * @param key
     */
    @GetMapping(value = "/randomImage/{key}")
    public Result<String> randomImage(HttpServletResponse response, @PathVariable String key){
		Result<String> res = new Result<String>();
        try {
            String code = RandomUtil.randomString(BASE_CHECK_CODES,4);
            String lowerCaseCode = code.toLowerCase();
            String realKey = MD5Util.MD5Encode(lowerCaseCode+key, "utf-8");
            JedisUtil.STRINGS.set(realKey, lowerCaseCode);
            String base64 = RandImageUtil.generate(code);
            res.setSuccess(true);
            res.setResult(base64);
        } catch (Exception e) {
            res.error("获取验证码出错"+e.getMessage());
            e.printStackTrace();
        }
        return res;
    }
}
