/*
 * Copyright(c)2018.客如云 All Rights Reserved.FileName: AccessFilter.java@date: 18-5-26 下午4:13@version: 1.0
 */

package com.sinosoft.gateway.filter;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * 访问鉴权过滤器
 */
public class AccessFilter extends ZuulFilter {
    
    @Override
    public String filterType() {
        return "pre";
    }
    @Override
    public int filterOrder() {
        return 0;
    }
    @Override
    public boolean shouldFilter() {
        return true;
    }
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        //防止中文乱码
        ctx.getResponse().setContentType("application/json;charset=utf-8");
        HttpServletRequest request = ctx.getRequest();
        
        Object accessToken = request.getParameter("accessToken");
        //下面的提示信息需要我们定义的统一异常编码结合起来
        if(accessToken == null ) {
            System.out.println("没有acceeToken参数！");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            ctx.setResponseBody("{\"code\":401,\"message\":\"没有accessToken\"}");
            return null;
        }else{
            String token=accessToken.toString().trim();
            if("".equals(token)){
                System.out.println("accessToken为空");
                ctx.setSendZuulResponse(false);
                ctx.setResponseStatusCode(401);
                ctx.setResponseBody("{\"code\":401,\"message\":\"没有accessToken\"}");
                return null;
            }
        }
        return null;
    }

}