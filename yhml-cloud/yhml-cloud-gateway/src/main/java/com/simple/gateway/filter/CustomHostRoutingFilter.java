package com.simple.gateway.filter;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpContext;
import org.springframework.cloud.commons.httpclient.ApacheHttpClientConnectionManagerFactory;
import org.springframework.cloud.commons.httpclient.ApacheHttpClientFactory;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.route.SimpleHostRoutingFilter;

/**
 * @author: Jianfeng.Hu
 * @date: 2017/11/16
 */
public class CustomHostRoutingFilter extends SimpleHostRoutingFilter {

    private ZuulProperties.Host hostProperties;

    private boolean sslHostnameValidationEnabled;

    public CustomHostRoutingFilter(ProxyRequestHelper helper, ZuulProperties properties, ApacheHttpClientConnectionManagerFactory
            connectionManagerFactory, ApacheHttpClientFactory httpClientFactory) {

        super(helper, properties, connectionManagerFactory, httpClientFactory);
        this.hostProperties = properties.getHost();
        this.sslHostnameValidationEnabled = properties.isSslHostnameValidationEnabled();
    }

    // @Override
    @Override
    protected CloseableHttpClient newClient() {

        final RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(this.hostProperties.getSocketTimeoutMillis())
                .setConnectTimeout(this.hostProperties.getConnectTimeoutMillis())
                .setCookieSpec(CookieSpecs.IGNORE_COOKIES).build();

        // 默认keepAliveTimeout为20s
        // ConnectionKeepAliveStrategy connectionKeepAliveStrategy = (httpResponse, httpContext) -> 20 * 1000;

        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        if (!this.sslHostnameValidationEnabled) {
            httpClientBuilder.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE);
        }

        return httpClientBuilder.setConnectionManager(getConnectionManager()).disableContentCompression().useSystemProperties()
                .setDefaultRequestConfig(requestConfig)
                .setRetryHandler(new DefaultHttpRequestRetryHandler(1, false)) // 重试策略
                .setKeepAliveStrategy((response, context) -> 20 * 1000)  // 默认keepAliveTimeout为20s
                .setRedirectStrategy(new RedirectStrategy() {
                    @Override
                    public boolean isRedirected(HttpRequest request, HttpResponse response, HttpContext context) throws ProtocolException {
                        return false;
                    }

                    @Override
                    public HttpUriRequest getRedirect(HttpRequest request, HttpResponse response, HttpContext context) throws
                            ProtocolException {
                        return null;
                    }
                }).build();
    }

}
