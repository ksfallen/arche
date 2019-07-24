package com.yhml.cloud.feign;

import java.io.IOException;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import com.netflix.hystrix.exception.HystrixBadRequestException;

import feign.Response;
import feign.Util;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class BizExceptionFeignErrorDecoder implements feign.codec.ErrorDecoder{

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            String body = Util.toString(response.body().asReader());
            // 400-499的respone都不算熔断
            if(response.status() >= HttpStatus.BAD_REQUEST.value() && response.status() < HttpStatus.INTERNAL_SERVER_ERROR.value()){
                return new HystrixBadRequestException(body);
            }
        } catch (IOException e) {
            log.error("", e);
        }

        return feign.FeignException.errorStatus(methodKey, response);
    }
}
