package com.yhml.cache.aop;
//
// import java.lang.reflect.Method;
//
// import org.aspectj.lang.ProceedingJoinPoint;
// import org.aspectj.lang.annotation.Around;
// import org.aspectj.lang.annotation.Aspect;
// import org.aspectj.lang.annotation.Pointcut;
// import org.aspectj.lang.reflect.MethodSignature;
// import org.redisson.api.RLock;
// import org.redisson.api.RedissonClient;
//
// import com.yhml.core.annotaton.CacheLock;
// import com.yhml.core.exception.BaseException;
// import com.yhml.core.util.StringUtil;
//
// import lombok.AllArgsConstructor;
//
// @Aspect
// @AllArgsConstructor
public class RedissonLockAspect {
//
//     private RedissonClient client;
//
//     private AbstractCacheKeyGenerator cacheKeyGenerator;
//
//
//     @Pointcut("@annotation(com.yhml.core.annotaton.CacheLock)")
//     public void pointcut() {
//     }
//
//     @Around("pointcut()")
//     public Object around(ProceedingJoinPoint pjp) throws Throwable {
//         Method method = ((MethodSignature) pjp.getSignature()).getMethod();
//         CacheLock cacheLock = method.getAnnotation(CacheLock.class);
//
//         if (StringUtil.isEmpty(cacheLock.prefix())) {
//             return pjp.proceed();
//         }
//
//         final String lockKey = cacheKeyGenerator.getLockKey(pjp);
//
//         if (StringUtil.isEmpty(lockKey)) {
//             return pjp.proceed();
//         }
//
//         RLock lock = client.getLock(lockKey);
//
//         boolean success = false;
//
//         try {
//             // 加锁以后5秒钟自动解锁
//             success = lock.tryLock(cacheLock.expire(), cacheLock.timeUnit());
//
//             // 采用原生 API 来实现分布式锁
//             // final Boolean success = lockRedisTemplate.execute((RedisCallback<Boolean>) connection -> connection.set(lockKey.getBytes(),
//             //         new byte[0], Expiration.from(cacheLock.expire(), cacheLock.timeUnit()), RedisStringCommands.SetOption
//             // .SET_IF_ABSENT));
//
//             if (!success) {
//                 throw new BaseException("无法获取锁:" + lockKey);
//             }
//
//             return pjp.proceed();
//         } finally {
//             if (success) {
//                 lock.unlock();
//             }
//         }
//     }
}
