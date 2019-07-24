package com.yhml.lock.aop;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.util.Assert;

import com.yhml.lock.annotation.LockAction;

import lombok.NonNull;

/**
 * @author: Jfeng
 * @date: 2019-07-15
 */
public class LockAnnotationAdvisor extends AbstractPointcutAdvisor implements BeanFactoryAware {
    private static final long serialVersionUID = 769342592356574475L;
    private Advice advice;
    private Pointcut pointcut;

    public LockAnnotationAdvisor(@NonNull LockInterceptor lockInterceptor) {
        Assert.notNull(lockInterceptor, "lockInterceptor must not nulll");
        this.advice = lockInterceptor;
        this.pointcut = this.buildPointcut();
    }

    public Pointcut getPointcut() {
        return this.pointcut;
    }

    public Advice getAdvice() {
        return this.advice;
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (this.advice instanceof BeanFactoryAware) {
            ((BeanFactoryAware) this.advice).setBeanFactory(beanFactory);
        }

    }

    private Pointcut buildPointcut() {
        return AnnotationMatchingPointcut.forMethodAnnotation(LockAction.class);
    }
}
