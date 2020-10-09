package com.yhml.core.exception;

import com.yhml.core.base.ErrorMessge;

/**
 * @author Jfeng
 * @date 2020/7/14
 */
public class AuthException extends BaseException {
    private static final long serialVersionUID = 6384896247497400703L;

    public AuthException() {
        super(ErrorMessge.UNAUTHORIZED);
    }
}
