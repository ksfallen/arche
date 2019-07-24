package com.yhml.cloud.feign;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author: Jfeng
 * @date: 2019-06-27
 */
@Slf4j
public class HttpLoggingInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        log.info("==> okhttp req:{}, hearder:{}", request, request.headers());

        long t1 = System.currentTimeMillis();
        Response response = chain.proceed(request);
        ResponseBody body = response.peekBody(1024 * 1024);

        log.info("<== okhttp resp, time:{}, body:{}", (System.currentTimeMillis() - t1), body.string());

        return response;
    }
}
