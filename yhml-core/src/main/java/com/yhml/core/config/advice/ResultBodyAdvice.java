package com.yhml.core.config.advice;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Cotroller 响应结果统一处理
 */
// @ControllerAdvice
// @Order(1)
public class ResultBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType mediaType, Class<? extends HttpMessageConverter<?>>
            selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // if (!(body instanceof ResultBean)) {
        //     return ResultBeanBuilder.success().withData(body).createBuilder();
        // }
        return body;
    }
}
