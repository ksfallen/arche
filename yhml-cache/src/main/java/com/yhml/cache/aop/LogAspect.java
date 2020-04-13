package com.yhml.cache.aop;

import com.yhml.core.util.JsonUtil;
import com.yhml.core.util.RequestUtil;
import com.yhml.core.util.fastjson.SimplePropertyPreFilter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import cn.hutool.core.date.TimeInterval;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class LogAspect {

    @Getter
    @Setter
    private SimplePropertyPreFilter filter;

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void restController() {
    }

    @Pointcut("@within(com.yhml.cache.annotaton.Log) || @annotation(com.yhml.cache.annotaton.Log)")
    public void logger() {
    }

    /**
     * public 方法
     */
    @Pointcut("execution(public * *(..))")
    public void pointcut3() {
    }

    // @Before("restController() || logger()")
    // public void before(JoinPoint joinPoint) {
    //     HttpServletRequest request = RequestUtil.getRequest();
    //     if (request != null) {
    //         log.info("---> {} {}", request.getMethod(), request.getRequestURI());
    //         log.info("---> {}", RequestUtil.getParams(request));
    //     }
    //
    //     String clazzName = joinPoint.getTarget().getClass().getSimpleName();
    //     String methodName = joinPoint.getSignature().getName();
    //
    //     // 获取请求参数
    //     Object[] paramValues = joinPoint.getArgs();
    //     log.info("---> {}.{}, params:{}", clazzName, methodName, JsonUtil.toJsonString(paramValues));
    //
    // }
    //
    // @AfterReturning(pointcut = "restController() && pointcut3()", returning = "ret")
    // public void after(JoinPoint jp, Object ret) {
    //     log.info("<--- {}", JsonUtil.toJsonString(ret));
    // }

    @Around("restController() || logger()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = RequestUtil.getRequest();
        if (request != null) {
            log.info("---> {} {}", request.getMethod(), request.getRequestURI());
        }

        String clazzName = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] paramValues = joinPoint.getArgs();
        log.info("---> {}.{}, params:{}", clazzName, methodName, JsonUtil.toJsonString(paramValues));

        TimeInterval time = new TimeInterval();
        time.start();

        Object result = null;
        try {
            result = joinPoint.proceed();
        } finally {
            log.info("<-- {} time:{}", toJson(result), time.intervalMs());
        }


        return result;
    }

    // @AfterReturning(returning = "ret", pointcut = "restController()")
    // public void after4(Object ret) {
    //     log.info("<<< http response {}", JsonUtil.toJsonStringWithoutNull(ret));
    // }

    // @AfterThrowing(restController = "restController() && pointcut3()")
    // public void afterThrowing(JoinPoint jp) {
    //     log.info("http response {}", "exception !!!");
    //     log.info("<<<<<< END ");
    // }


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
        return JsonUtil.toJsonString(result);
    }
}
