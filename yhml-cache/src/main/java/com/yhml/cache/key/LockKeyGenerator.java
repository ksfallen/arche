package com.yhml.cache.key;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import com.yhml.cache.annotaton.Lock;


public class LockKeyGenerator extends AbstractKeyGenerator {
    private static final ParameterNameDiscoverer NAME_DISCOVERER = new DefaultParameterNameDiscoverer();
    private static final ExpressionParser PARSER = new SpelExpressionParser();

    public String getKey(MethodInvocation invocation, Lock lock) {
        if (lock == null) {
            return null;
        }

        return getKey(invocation.getMethod(), invocation.getArguments(), lock.prefix(), lock.keys(), lock.delimiter());
    }


    // public String getKey(ProceedingJoinPoint pjp) {
        // Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        //
        // CacheLock lock = method.getAnnotation(CacheLock.class);
        //
        // if (lock == null) {
        //     return null;
        // }
        //
        // final Object[] paramValues = pjp.getArgs();
        // final Parameter[] parameters = method.getParameters();
        //
        // StringBuilder builder = new StringBuilder();
        //
        // // 默认解析方法带 CacheParam 注解的属性
        // // key prefix:param:paramvalue
        // for (int i = 0; i < parameters.length; i++) {
        //     Parameter parameter = parameters[i];
        //     final CacheParam annotation = parameter.getAnnotation(CacheParam.class);
        //
        //     if (annotation != null) {
        //         String paraName = StringUtil.hasText(annotation.name())? annotation.name() : parameter.getName();
        //         builder.append(lock.delimiter()).append(paraName).append(lock.delimiter()).append(paramValues[i]);
        //     }
        //
        // }
        //
        // return lock.prefix() + builder.toString();
        // return null;
    // }
}
