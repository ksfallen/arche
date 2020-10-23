package com.yhml.core.aop;

import com.yhml.core.util.JsonUtil;
import com.yhml.core.util.RequestUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.Clock;

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
        String clazzName = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        // log.info("==> {}.{}, params:{}", clazzName, methodName, JsonUtil.toJson(joinPoint.getArgs()));

        long time = Clock.systemDefaultZone().millis();
        Object result = joinPoint.proceed();

        RequestInfo info = new RequestInfo();
        HttpServletRequest request = RequestUtil.getRequest();
        if (request != null) {
            info.setIp(request.getRemoteAddr());
            info.setUrl(request.getRequestURL().toString());
            info.setHttpMethod(request.getMethod());
        }
        info.setClassMethod(String.format("%s.%s", clazzName, methodName));
        info.setRequestParams(joinPoint.getArgs());
        info.setResult(result);
        info.setSpendTime(Clock.systemDefaultZone().millis() - time);
        log.info("==> 请求日志 {}", JsonUtil.toJson(joinPoint.getArgs()));

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

    @AfterThrowing(pointcut = "restController() || logger()", throwing = "e")
    public void doAfterThrow(JoinPoint joinPoint, RuntimeException e) {
        HttpServletRequest request = RequestUtil.getRequest();
        RequestErrorInfo info = new RequestErrorInfo();
        if (request != null) {
            info.setIp(request.getRemoteAddr());
            info.setUrl(request.getRequestURL().toString());
            info.setHttpMethod(request.getMethod());
        }
        info.setClassMethod(String.format("%s.%s", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName()));
        info.setRequestParams(joinPoint.getArgs());
        info.setException(e);
        log.info("==> 请求异常 {}", JsonUtil.toJson(joinPoint.getArgs()));
    }

}
