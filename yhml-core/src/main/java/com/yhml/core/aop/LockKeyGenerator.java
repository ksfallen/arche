package com.yhml.core.aop;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import com.yhml.core.annotaton.CacheLock;
import com.yhml.core.annotaton.CacheParam;
import com.yhml.core.util.StringUtil;

/**
 * @author: Jfeng
 * @date: 2018/7/26
 */
public class LockKeyGenerator implements AbstractCacheKeyGenerator {

    @Override
    public String getLockKey(ProceedingJoinPoint pjp) {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();

        CacheLock lock = method.getAnnotation(CacheLock.class);

        if (lock == null) {
            return null;
        }

        final Object[] paramValues = pjp.getArgs();
        final Parameter[] parameters = method.getParameters();

        StringBuilder builder = new StringBuilder();

        // 默认解析方法带 CacheParam 注解的属性
        // key prefix:param:paramvalue
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            final CacheParam annotation = parameter.getAnnotation(CacheParam.class);

            if (annotation != null) {
                String paraName = StringUtil.hasText(annotation.name())? annotation.name() : parameter.getName();
                builder.append(lock.delimiter()).append(paraName).append(lock.delimiter()).append(paramValues[i]);
            }

        }

        return lock.prefix() + builder.toString();
    }
}
