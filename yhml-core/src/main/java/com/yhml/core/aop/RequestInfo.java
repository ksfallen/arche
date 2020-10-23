package com.yhml.core.aop;

import lombok.Data;

/**
 * @author Jfeng
 * @date 2020/10/23
 */

/**
 * 请求日志对象
 */
@Data
public class RequestInfo {
    private String ip;
    private String url;
    private String httpMethod;
    private String classMethod;
    private Object requestParams;
    private Object result;
    private Long spendTime;
}
