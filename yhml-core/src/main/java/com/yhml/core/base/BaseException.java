package com.yhml.core.base;

import com.yhml.core.util.JsonUtil;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 4595060073570956480L;

    private String code;
    private String msg;

    public BaseException(ErrorMessge messge) {
        this.code = messge.getCode();
        this.msg = messge.getMsg();
    }

    public BaseException(String msg) {
        this.code = ErrorMessge.ERROR_SYS.getCode();
        this.msg = msg;
    }

    @Override
    public String toString() {
        return JsonUtil.toJsonString(this);
    }
}
