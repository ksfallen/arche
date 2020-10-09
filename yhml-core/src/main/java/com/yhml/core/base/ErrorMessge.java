package com.yhml.core.base;

import java.util.Arrays;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessge {

    SUCCESS("0", "success"),
    ERROR_SYS("500", "system error"),
    ERROR_SUBMIT_INTIME("500", "请求过于频繁,请稍后重试"),

    UNAUTHORIZED("401", "Unauthorized"),

    ERROR_DB("1000", "db error"),
    SERVICE_NOT_AVAILABLE("1001", "service not available"),
    ERROR_REPEAT_SUBMIT("1002", "重复提交"),
    ERROR_ARGS("1003", "参数错误"),


    /**
     * 业务处理失败
     */
    FAILED_BUSINESS("4000", "Business Failed");

	public static String getMessage(String code) {
        Optional<ErrorMessge> first = Arrays.stream(ErrorMessge.values()).filter(a -> a.code.equals(code)).findFirst();
        if (first.isPresent()) {
            return first.get().getMsg();
        }
        return "";
	}

    private String code;
    private String msg;
}
