package com.yhml.bd.bd.es;

import lombok.Data;

/**
 * @author: Jfeng
 * @date: 2018/7/5
 */
@Data
public class ESConfig {
    private String addressHostPorts;
    private String clusterName;
    private String indexName;
    private String typeName;
    private String userName;
    private String password;
    private Integer shards = 1;
    private Integer replicas = 0;

}
