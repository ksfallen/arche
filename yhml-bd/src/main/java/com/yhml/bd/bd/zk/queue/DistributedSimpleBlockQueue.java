package com.yhml.bd.bd.zk.queue;

import java.util.concurrent.CountDownLatch;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

/**
 * @author: Jianfeng.Hu
 * @date: 2017/12/7
 */
public class DistributedSimpleBlockQueue<T> extends DistributedSimpleQueue<T> {


    public DistributedSimpleBlockQueue(ZkClient zkClient, String root) {
        super(zkClient, root);
    }

    @Override
    public T poll() {
        while (true) {
            final CountDownLatch latch = new CountDownLatch(1);
            final IZkChildListener listener = (parentPath, currentChilds) -> latch.countDown();
            zkClient.subscribeChildChanges(root, listener);

            try {
                T node = super.poll();
                if (node != null) {
                    return node;
                } else {
                    latch.await();
                }
            } catch (Exception ignored) {
              
            } finally {
                zkClient.unsubscribeChildChanges(root, listener);
            }
        }
    }
}
