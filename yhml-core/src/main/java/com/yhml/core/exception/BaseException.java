package com.yhml.core.exception;

import com.yhml.core.base.ErrorMessge;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 4595060073570956480L;

    private String code = ErrorMessge.ERROR_SYS.getCode();

    public BaseException(String msg) {
        super(msg);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(String code, String msg, Throwable cause) {
        super(msg, cause);
        this.code = code;
    }

    public BaseException(ErrorMessge messge) {
        super(messge.getMsg());
        this.code = messge.getCode();
    }

    public BaseException(ErrorMessge messge, Throwable cause) {
        super(messge.getMsg(), cause);
        this.code = messge.getCode();
    }


    @Override
    public String toString() {
        return "BaseException{" + "code=" + code + ", message=" + getMessage() + '}';
    }
}
