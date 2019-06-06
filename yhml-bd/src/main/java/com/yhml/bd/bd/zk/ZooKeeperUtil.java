package com.yhml.bd.bd.zk;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

/**
 * @author: Jianfeng.Hu
 * @date: 2017/11/6
 */
public class ZooKeeperUtil {
    private static final String Connecnt = "127.0.0.1:2181";
    private static final int session_timeout = 5000;
    private static CountDownLatch latch = new CountDownLatch(1);

    private static ZooKeeper zk;

    public static void main(String[] args) throws Exception {
        zk = new ZooKeeper(Connecnt, session_timeout, event -> {
            if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                latch.countDown();
            }
        });

        latch.await();
        System.out.println(zk);
        getChildren("/");
    }

    public static List<String> getChildren(String path) throws KeeperException, InterruptedException {
        List<String> children = zk.getChildren(path, null);
        children.forEach(s -> System.out.println("s = " + s));
        return children;
    }

}
