package com.yhml.cloud.feign;

import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.commons.httpclient.OkHttpClientConnectionPoolFactory;
import org.springframework.cloud.commons.httpclient.OkHttpClientFactory;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.cloud.openfeign.support.FeignHttpClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

/**
 * Created by shade on 2018/1/24.
 */
@Configuration
@ConditionalOnClass(OkHttpClient.class)
@AutoConfigureBefore(FeignAutoConfiguration.class)
@ConditionalOnProperty(value = {"feign.okhttp.enabled"})
public class FeignOkHttpConfig {

    private okhttp3.OkHttpClient okHttpClient;

    @Autowired
    private FeignHttpClientProperties httpClientProperties;

    @Autowired
    private FeignClientProperties clientProperties;

    @Value("${feign.client.config.default.writeTimeout:10000}")
    private int writeTimeout;

    @Bean
    @ConditionalOnMissingBean({ConnectionPool.class})
    public ConnectionPool httpClientConnectionPool(OkHttpClientConnectionPoolFactory factory) {
        int maxTotalConnections = httpClientProperties.getMaxConnections();
        long timeToLive = httpClientProperties.getTimeToLive();
        TimeUnit ttlUnit = httpClientProperties.getTimeToLiveUnit();
        return factory.create(maxTotalConnections, timeToLive, ttlUnit);
    }

    @Bean
    public OkHttpClient okHttpClient(OkHttpClientFactory factory, ConnectionPool connectionPool) {
        FeignClientProperties.FeignClientConfiguration config = clientProperties.getConfig().get(clientProperties.getDefaultConfig());

        boolean followRedirects = httpClientProperties.isFollowRedirects();
        boolean disableSslValidation = httpClientProperties.isDisableSslValidation();
        int connectTimeout = config != null ? config.getConnectTimeout() : 10000;
        int readTimeout = config != null ? config.getReadTimeout() : 10000;

        // @formatter:off
        this.okHttpClient = factory.createBuilder(disableSslValidation)
                .connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                .readTimeout(readTimeout, TimeUnit.MILLISECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                .followRedirects(followRedirects)
                .connectionPool(connectionPool)
                .addInterceptor(new HttpLoggingInterceptor())
                .build();
        // @formatter:on

        return okHttpClient;
    }

    @PreDestroy
    public void destroy() {
        if (this.okHttpClient != null) {
            this.okHttpClient.dispatcher().executorService().shutdown();
            this.okHttpClient.connectionPool().evictAll();
        }
    }
}
