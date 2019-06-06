package com.yhml.core.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.yhml.core.util.JsonUtil;
import com.yhml.core.util.RequestUtil;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LogAspect {

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void restController() {
    }

    @Pointcut("@within(com.yhml.core.annotaton.Logger) || @annotation(com.yhml.core.annotaton.Logger)")
    public void logger() {
    }

    @Pointcut("execution(public * *(..))")
    public void pointcut3() {
    }

    // @Before("restController() || logger()")
    // public void before() {
    //     HttpServletRequest request = RequestUtil.getRequest();
    //     if (request != null) {
    //         log.info("---> {} {}", request.getMethod(), request.getRequestURI());
    //         log.info("---> {}", RequestUtil.getParams(request));
    //     }
    // }
    //
    // @AfterReturning(pointcut = "restController() && pointcut3()", returning = "ret")
    // public void after(JoinPoint jp, Object ret) {
    //     log.info("<--- {}", JsonUtil.toJsonString(ret));
    // }

    @Around("restController() && pointcut3()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long t = System.currentTimeMillis();

        try {
            String clazzName = joinPoint.getTarget().getClass().getSimpleName();
            String methodName = joinPoint.getSignature().getName();

            // 获取请求参数
            Object[] paramValues = joinPoint.getArgs();


            HttpServletRequest request = RequestUtil.getRequest();
            if (request != null) {
                log.info("---> {} {}", request.getMethod(), request.getRequestURI());
            }

            // 参数
            log.info("---> {}#{}, params:{}", clazzName, methodName, JsonUtil.toJsonString(paramValues));
        } catch (Exception ignored) {
        }

        Object result = joinPoint.proceed();

        try {
            log.info("<--- ret:{}, time:{}", JsonUtil.toJsonString(result), System.currentTimeMillis() - t);
        } catch (Exception ignored) {

        }

        return result;
    }

    // @AfterReturning(value = "@within(org.springframework.web.bind.annotation.ControllerAdvice)", returning = "ret")
    // public void after4(JoinPoint jp, Object ret) {
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
    //         for (Method method : methods) {
    //             if (method.getName().equals(joinPoint.getSignature().getName())) {
    //                 if (method.getParameterTypes().length == joinPoint.getArgs().length) {
    //                     return method.getAnnotation(Log.class).value();
    //                 }
    //             }
    //         }
    //     } catch (Exception e) {
    //         log.error("getSource error", e);
    //     }
    //
    //     return null;
    // }
}
