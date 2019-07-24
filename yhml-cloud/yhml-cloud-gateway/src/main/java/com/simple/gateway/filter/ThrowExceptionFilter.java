package com.simple.gateway.filter;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import lombok.extern.slf4j.Slf4j;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;

/**
 * @author: Jianfeng.Hu
 * @date: 2017/10/25
 */
@Slf4j
// @Component
public class ThrowExceptionFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return POST_TYPE;
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
        log.info("This is a pre filter,");
        RequestContext ctx = RequestContext.getCurrentContext();
        // try {
        // } catch (Exception e) {
        //     throw new ZuulRuntimeException(e);
            // e.printStackTrace();
            // ctx.set("desc.status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            // ctx.set("desc.exception", e);
        // }
        return null;
    }

    private void doSomething() {
        throw new RuntimeException("Exist some errors...");
    }

}
