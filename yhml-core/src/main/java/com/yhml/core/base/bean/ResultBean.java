package com.yhml.core.base.bean;


import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.yhml.core.base.ErrorMessge;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultBean<T> extends BaseBean {

    public static final String SUCCESS = "success";
    public static final String ERROR = "error";
    public static final String OK = "ok";

    private String code;

    private String message;

    private T data;

    @JsonIgnore
    @JSONField(serialize = false)
    public boolean isSuccess() {
        return ErrorMessge.SUCCESS.getCode().equals(this.code);
    }

    public static ResultBean ok() {
        return build(ErrorMessge.SUCCESS).build();
    }

    public static ResultBean ok(Object data) {
        return build(ErrorMessge.SUCCESS).data(data).build();
    }

    public static ResultBean error() {
        return build(ErrorMessge.ERROR_SYS).build();
    }

    private static ResultBeanBuilder<Object> build(ErrorMessge messge) {
        return ResultBean.builder().message(messge.getMessage()).code(messge.getCode());
    }

}
