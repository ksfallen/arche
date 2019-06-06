package com.yhml.bd.bd.lock.redis;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.FactoryBean;

import lombok.Setter;

/**
 * @author: Jianfeng.Hu
 * @date: 2017/12/7
 */
public class DistributedLockFactoryBean implements FactoryBean<DistributedLockTemplate> {

    private LockInstanceMode mode;

    private DistributedLockTemplate distributedLockTemplate;

    @Setter
    private RedissonClient redisson;

    @Override
    public DistributedLockTemplate getObject() {
        switch (mode) {
            case SINGLE:
                distributedLockTemplate = new SingleDistributedLockTemplate(redisson);
                break;
        }

        return distributedLockTemplate;
    }

    @Override
    public Class<?> getObjectType() {
        return DistributedLockTemplate.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public void setMode(String mode) {
        if (mode == null || mode.length() <= 0 || mode.equals("")) {
            throw new IllegalArgumentException("未找到dlm.redisson.mode配置项");
        }
        this.mode = LockInstanceMode.parse(mode);
        if (this.mode == null) {
            throw new IllegalArgumentException("不支持的分布式锁模式");
        }
    }

    private enum LockInstanceMode {
        SINGLE;

        public static LockInstanceMode parse(String name) {
            for (LockInstanceMode modeIns : LockInstanceMode.values()) {
                if (modeIns.name().equals(name.toUpperCase())) {
                    return modeIns;
                }
            }
            return null;
        }
    }

    /**
     * 释放分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    // public static boolean releaseDistributedLock(Jedis jedis, String lockKey, String requestId) {
    //
    //     String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
    //     Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
    //
    //     if (RELEASE_SUCCESS.equals(result)) {
    //         return true;
    //     }
    //     return false;
    //
    // }

    /**
     * 尝试获取分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    // public static boolean tryGetDistributedLock(Jedis jedis, String lockKey, String requestId, int expireTime) {
    //
    //     String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
    //
    //     if (LOCK_SUCCESS.equals(result)) {
    //         return true;
    //     }
    //     return false;
    //
    // }

}
