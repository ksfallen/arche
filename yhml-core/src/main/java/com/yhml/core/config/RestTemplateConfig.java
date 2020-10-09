package com.yhml.core.config;

import org.apache.http.client.HttpClient;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: Jianfeng.Hu
 * @date: 2017/10/17
 */
@Getter
@Setter
@Configuration
// @ConfigurationProperties("rest")
// @AutoConfigureAfter(SimpleConfiguration.class)
public class RestTemplateConfig {

    /**
     * 整个连接池最大连接数
     */
    private int maxTotal = 10;

    /**
     * 路由的默认最大连接（该值默认为2），限制数量实际使用DefaultMaxPerRoute并非MaxTotal
     */
    private int defaultMaxPerRoute = 2;

    /**
     * 读写超时时间，毫秒
     */
    private int readTimeout = 10000;

    /**
     * 连接超时时间，毫秒
     */
    private int connectTimeout = 10000;

    // public RestTemplateBuilder builder() {
    //     RestTemplate restTemplate = new RestTemplate(requestFactory());
    //     List<HttpMessageConverter<?>> list = Lists.newArrayList(stringHttpMessageConverter(), fastJsonHttpMessageConverter());
    //     restTemplate.setMessageConverters(list);
    //
    //     RestTemplateBuilder source = new RestTemplateBuilder();
    //     source.messageConverters(list).requestFactory(requestFactory());
    //
    //     return source;
    // }

    // public RestTemplateBuilder create2() {
    //     List<HttpMessageConverter<?>> list = Lists.newArrayList(stringHttpMessageConverter(), fastJsonHttpMessageConverter());
    //
    //     RestTemplateCustomizer customizer = restTemplate -> {
    //         restTemplate.setRequestFactory(requestFactory());
    //         restTemplate.setMessageConverters(list);
    //     };
    //
    //    return new RestTemplateBuilder(customizer);
    // }

    // @Autowired
    // private HttpMessageConverter fastJsonConverter;

    @Bean("restTemplate")
    RestTemplate restTemplate() {
        return create().build();
    }


    public RestTemplateBuilder create() {
        return new RestTemplateBuilder().requestFactory(() -> requestFactory())
                // .messageConverters()
                // .additionalMessageConverters(stringHttpMessageConverter())
                // .additionalMessageConverters(fastJsonHttpMessageConverter())
                // .additionalMessageConverters(fastJsonConverter)
                // .additionalMessageConverters(formHttpMessageConverter())
                .errorHandler(new DefaultResponseErrorHandler());
    }


    private PoolingHttpClientConnectionManager pollingConnectionManager() {
        PoolingHttpClientConnectionManager pool = new PoolingHttpClientConnectionManager();
        pool.setMaxTotal(maxTotal);
        pool.setDefaultMaxPerRoute(defaultMaxPerRoute);
        return pool;
    }

    private HttpComponentsClientHttpRequestFactory requestFactory() {
        HttpComponentsClientHttpRequestFactory factory = null;

        try {
            HttpClientBuilder builder = HttpClientBuilder.create();
            builder.setConnectionManager(pollingConnectionManager());

            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                public boolean isTrusted(X509Certificate[] arg0, String arg1) {
                    return true;
                }
            }).build();

            builder.setSSLContext(sslContext);

            HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;

            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);

            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .register("https", sslConnectionSocketFactory).build();// 注册http和https请求

            //  开启重试
            // requestSentRetryEnabled 开启
            // retryCount 充实次数
            // DefaultHttpRequestRetryHandler retryHandler = new DefaultHttpRequestRetryHandler(1, true);
            // source.setRetryHandler(retryHandler);
            builder.setRetryHandler(new DefaultHttpRequestRetryHandler(1, false)); // 不重试

            HttpClient client = builder.build();

            factory = new HttpComponentsClientHttpRequestFactory(client);
            factory.setConnectTimeout(connectTimeout);
            factory.setReadTimeout(readTimeout);
            factory.setConnectionRequestTimeout(20000); // 连接不够用的等待时间
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException ignored) {
        }

        return factory;
    }


}
