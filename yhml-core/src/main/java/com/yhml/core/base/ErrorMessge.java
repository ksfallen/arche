package com.yhml.core.base;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 返回结果信息枚举
 */
@Getter
@AllArgsConstructor
public enum ErrorMessge {

    SUCCESS("0", "success"),
    ERROR_SYS("-1", "system error"),

    ERROR_DB("1000", "db error"),
    SERVICE_NOT_AVAILABLE("1001", "service not available"),
    ERROR_REPEAT_SUBMIT("1002", "重复提交"),
    ERROR_ARGS("1003", "参数错误"),

    /**
     * 缺少必选参数
     */
    MISS_PARAMETER("40001", "Miss Parameter"),
    /**
     * 非法参数
     */
    ILLEGAL_PARAMETER("40002", "Illegal Parameter"),

    /**
     * 业务处理失败
     */
    FAILED_BUSINESS("40004", "Business Failed");


	public static String getMessage(String code) {
        String result = "";
        for(ErrorMessge temp: ErrorMessge.values()){
            if(StringUtils.equals(code, temp.code)){
                result =  temp.getMessage();
                break;
            }
        }

        return result;
	}

    private String code;
    private String message;
}
