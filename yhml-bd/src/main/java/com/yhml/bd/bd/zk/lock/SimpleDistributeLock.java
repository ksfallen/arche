package com.yhml.bd.bd.zk.lock;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.I0Itec.zkclient.ZkClient;

/**
 * @author: Jianfeng.Hu
 * @date: 2017/11/30
 */
public class SimpleDistributeLock extends BaseDistributedLock implements DistributedLock {

    /**
     * 锁节点前缀, 顺序节点:lock-0000000000,lock-0000000001
     */
    private static final String LOCK_NAME = "lock-";

    /**
     * zk 中lock节点的路径,路:/lock
     */
    private String basePath;

    /**
     * 自己创建的顺序节点路径
     */
    private String ourLockPath;

    public SimpleDistributeLock(ZkClient client, String basePath) {
        super(client, basePath, LOCK_NAME);
        this.basePath = basePath;
    }

    private boolean internaLock(long time, TimeUnit unit) throws Exception {
        ourLockPath = attemptLock(time, unit);
        return ourLockPath != null;
    }


    @Override
    public void acquire() throws Exception {
        if (!internaLock(-1, null)) {
            throw new IOException("can not get lcok at basePath:" + basePath);
        }
    }

    @Override
    public boolean acquire(long time, TimeUnit timeUnit) throws Exception {
        return internaLock(time, timeUnit);
    }

    @Override
    public void release() throws Exception {
        releaseLock(ourLockPath);
    }
}
