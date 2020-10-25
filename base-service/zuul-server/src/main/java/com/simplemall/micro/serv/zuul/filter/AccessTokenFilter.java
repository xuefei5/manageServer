package com.simplemall.micro.serv.zuul.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.simplemall.micro.serv.zuul.util.ServletRequestUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import java.util.HashMap;

/**
 * token过滤器，校验token必输项方法，token不能为空
 *
 * @author guooo
 *
 */
@Component
public class AccessTokenFilter extends ZuulFilter {

	private static Logger log = LoggerFactory.getLogger(AccessTokenFilter.class);

	/*
	 * 过滤器的具体逻辑。可用很复杂，包括查sql，nosql去判断该请求到底有没有权限访问。
	 *
	 * @see com.netflix.zuul.IZuulFilter#run()
	 */
	@Override
	public Object run() {
		//TODO 可将Front-app服务中的APISecurityCheck中针对accessToken的校验迁移至此，提前验证
		RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

		HttpServletResponse response = ctx.getResponse();

		// add by xuefei5 对跨域支持
		response.setHeader("Access-control-Allow-Origin", request.getHeader("Origin"));
		response.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
		response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
		//update-begin-author:scott date:20200907 for:issues/I1TAAP 前后端分离，shiro过滤器配置引起的跨域问题
		// 是否允许发送Cookie，默认Cookie不包括在CORS请求之中。设为true时，表示服务器允许Cookie包含在请求中。
		response.setHeader("Access-Control-Allow-Credentials", "true");
		//update-end-author:scott date:20200907 for:issues/I1TAAP 前后端分离，shiro过滤器配置引起的跨域问题
        String method = request.getMethod();
        if("OPTIONS".equalsIgnoreCase(method)){
			ctx.setSendZuulResponse(false); //验证请求不进行路由
			ctx.setResponseStatusCode(HttpStatus.OK.value());//返回验证成功的状态码
			ctx.set("isSuccess", true);
			return null;
		}
		//如果是登录的服务则不校验token，add by xufei5
		String reqUrl = request.getRequestURL().toString();
		if(reqUrl.contains("loginService")){
			log.info("登录参数不校验token");
			return null;
		}
        log.info(String.format("%s >>> %s", method, reqUrl));
		//modif xuefei 获取token方式改变
        Object accessToken = request.getParameter("token");
        if(null == accessToken) {
			try {
				HashMap reqMap = ServletRequestUtil.getJson(request.getInputStream());
				accessToken = reqMap.get("token");
			}catch(Exception e){
					log.error("获取token异常" + e.getMessage());
			}
		}
		if(accessToken == null) {
			log.warn("token is empty");
			ctx.setSendZuulResponse(false);
			ctx.setResponseStatusCode(500);
			try {
				ctx.getResponse().getWriter().write("token不允许为空!");
			}catch (Exception e){}

			return null;
		}

        log.info("ok");
        return null;
	}

	/*
	 * 这里可以写逻辑判断，是否要过滤，本文true,永远过滤。
	 *
	 * @see com.netflix.zuul.IZuulFilter#shouldFilter()
	 */
	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	/*
	 * (non-Javadoc) pre：路由之前 routing：路由之时 post： 路由之后 error：发送错误调用
	 *
	 * @see com.netflix.zuul.ZuulFilter#filterType()
	 */
	@Override
	public String filterType() {
		return "pre";
	}

}
