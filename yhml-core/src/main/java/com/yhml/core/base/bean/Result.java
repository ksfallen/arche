package com.yhml.core.base.bean;


import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.yhml.core.base.ErrorMessge;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> extends BaseBean {
    public static final String SUCCESS = "success";
    public static final String ERROR = "error";
    public static final String OK = "ok";

    private String code;

    private String msg;

    private String extMsg;

    private T data;

    public Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(String msg) {
        this.code = ErrorMessge.ERROR_SYS.getCode();
        this.msg = msg;
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public boolean isSuccess() {
        return ErrorMessge.SUCCESS.getCode().equals(this.code);
    }

    public static Result ok() {
        return createBuilder(ErrorMessge.SUCCESS).build();
    }

    public static Result ok(Object data) {
        return createBuilder(ErrorMessge.SUCCESS).data(data).build();
    }

    public static Result error() {
        return createBuilder(ErrorMessge.ERROR_SYS).build();
    }

    public static Result error(ErrorMessge errorMessge) {
        return createBuilder(errorMessge).build();
    }

    public static Result error(String code, String msg) {
        return new Result(code, msg);
    }

    public static ResultBuilder<Object> createBuilder(ErrorMessge messge) {
        return Result.builder().msg(messge.getMsg()).code(messge.getCode());
    }

}
