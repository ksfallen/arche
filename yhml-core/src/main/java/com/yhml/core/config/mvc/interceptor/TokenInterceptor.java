package com.yhml.core.config.mvc.interceptor;

import com.yhml.core.util.RequestUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * 日志拦截器
 * User: Jfeng
 * Date: 2017/3/7
 */
@Slf4j
public class TokenInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getRequestURI() != null && handler instanceof HandlerMethod) {

            log.info("req -> host:{} prot:{} sessionid:{}, url:{}, {}",
                    request.getRemoteHost(),
                    request.getRemotePort(),
                    request.getRequestedSessionId(),
                    request.getRequestURI(),
                    RequestUtil.getParams(request));
        }

        return true;
    }

}
