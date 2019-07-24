package com.yhml.lock.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.yhml.core.base.BaseException;
import com.yhml.core.base.ErrorMessge;
import com.yhml.lock.LockInfo;
import com.yhml.lock.LockKeyGenerator;
import com.yhml.lock.LockTemplate;
import com.yhml.lock.annotation.LockAction;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: Jfeng
 * @date: 2019-07-15
 */
@Slf4j
public class LockInterceptor implements MethodInterceptor {
    private LockTemplate lockTemplate;
    private LockKeyGenerator lockKeyGenerator = new LockKeyGenerator();


    public Object invoke(MethodInvocation invocation) throws Throwable {
        LockInfo lockInfo = null;

        Object result;
        try {
            LockAction lock = invocation.getMethod().getAnnotation(LockAction.class);
            String keyName = this.lockKeyGenerator.getKeyName(invocation, lock);
            lockInfo = this.lockTemplate.lock(keyName, lock.expire(), lock.timeout());

            if (null == lockInfo) {
                log.info("锁资源获取失败");
                throw new BaseException(ErrorMessge.ERROR_SUBMIT_INTIME);
            }

            result = invocation.proceed();
        } finally {
            if (null != lockInfo) {
                this.lockTemplate.unLock(lockInfo);
            }

        }

        return result;
    }

    public void setLockTemplate(LockTemplate lockTemplate) {
        this.lockTemplate = lockTemplate;
    }
}
