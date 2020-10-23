package com.yhml.core.aop;

import lombok.Data;

/**
 * 请求异常日志对象
 * @author Jfeng
 * @date 2020/10/23
 */
@Data
public class RequestErrorInfo {
    private String ip;
    private String url;
    private String httpMethod;
    private String classMethod;
    private Object requestParams;
    private RuntimeException exception;
}
