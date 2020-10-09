package com.yhml.core.aop;

import com.yhml.core.util.JsonUtil;
import com.yhml.core.util.RequestUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import cn.hutool.core.date.TimeInterval;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LogAspect {

    /**
     * 属性过滤器对象
     */
    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void restController() {
    }

    @Pointcut("@within(com.yhml.core.annotaton.Log) || @annotation(com.yhml.core.annotaton.Log)")
    public void logger() {
    }

    /**
     * public 方法
     */
    @Pointcut("execution(public * *(..))")
    public void pointcut3() {
    }

    @Around("restController() || logger()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = RequestUtil.getRequest();
        if (request != null) {
            log.info("请求地址:{}, IP:{} ", request.getRequestURI(), RequestUtil.getIpAddress(request));
        }

        String clazzName = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        log.info("==> {}.{}, params:{}", clazzName, methodName, toJson(joinPoint.getArgs()));
        TimeInterval time = new TimeInterval();
        time.start();
        Object result = null;

        try {
            result = joinPoint.proceed();
        } finally {
            log.info("<== 耗时:{}ms 返回值:{}", time.intervalMs(), toJson(result));
        }

        return result;
    }


    // 获取注解中的值
    // private String getValue(JoinPoint joinPoint) {
    //     try {
    //         Method[] methods = joinPoint.getTarget().getClass().getMethods();
    //         Optional<Method> optional =
    //                 Arrays.stream(methods).filter(method -> method.getName().equals(joinPoint.getSignature().getName())).findFirst();
    //
    //         if (optional.isPresent()) {
    //             Method method = optional.get();
    //             if (method.getParameterTypes().length == joinPoint.getArgs().length) {
    //                 return method.getAnnotation(LogAction.class).value();
    //             }
    //         }
    //     } catch (Exception e) {
    //         log.error("getSource error", e);
    //     }
    //
    //     return null;
    // }

    protected String toJson(Object result) {
        return JsonUtil.toJson(result);
    }
}
