package com.yhml.bd.bd.zk.queue;

import java.util.List;

import org.I0Itec.zkclient.ExceptionUtil;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.apache.commons.collections.CollectionUtils;

import lombok.AllArgsConstructor;

/**
 * @author: Jianfeng.Hu
 * @date: 2017/12/7
 */
@AllArgsConstructor
public class DistributedSimpleQueue<T> {
    private static final String NODE_NAME = "n_";
    protected ZkClient zkClient;
    protected String root;

    public int size() {
        return zkClient.getChildren(root).size();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean offer(T element) {
        String path = root.concat("/").concat(NODE_NAME);
        try {
            zkClient.createPersistentSequential(path, element);
        } catch (ZkNoNodeException e) {
            zkClient.createPersistent(root);
            offer(element);
        } catch (Exception ex) {
            throw ExceptionUtil.convertToRuntimeException(ex);
        }

        return true;
    }

    public T poll(){
        try {
            List<String> children = zkClient.getChildren(root);
            if (CollectionUtils.isEmpty(children)) {
                return null;
            }

            children.sort((o1, o2) -> getNodeNumber(o1, NODE_NAME).compareTo(getNodeNumber(o2, NODE_NAME)));

            for (String name : children) {
                try {
                    String path = root.concat("/").concat(name);
                    T data = (T) zkClient.readData(path);
                    zkClient.delete(path);
                    return data;
                } catch (ZkNoNodeException ignored) {
                }
            }

            return null;

        } catch (Exception e) {
            throw  ExceptionUtil.convertToRuntimeException(e);
        }
    }

    private String getNodeNumber(String str, String lockName) {
        int index = str.lastIndexOf(lockName);
        if (index >= 0) {
            index += lockName.length();
            return index <= str.length() ? str.substring(index) : "";
        }
        return str;
    }

}
