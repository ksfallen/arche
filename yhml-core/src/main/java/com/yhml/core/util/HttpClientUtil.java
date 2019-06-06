package com.yhml.core.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 功能说明: HttpClient工具类；支持http/https、单向/双向认证；默认执行后自动关闭
 */
@Slf4j
// @Getter
// @Setter
public class HttpClientUtil {

    private static int DEFLUT_POOL_SIZE = 200;
    private static int DEFAULT_MAX_PER_ROUTE = 10;

    private HttpClientBuilder httpBuilder;
    private CloseableHttpClient httpclient;

    /**
     * 设置连接自动关闭，默认为true；
     *
     * 若设置为false，则连续调用可保持会话，使用后显示调用close方法；
     *
     * 保持会话时传入header不会生效
     */
    private boolean automaticClose = true;

    /**
     * 构造器
     */
    private HttpClientUtil() {
    }


    public static HttpClientUtil getInstance() {
        return getInstance(0, 0);
    }

    public static HttpClientUtil getInstance(int poolSize, int maxPerRoute) {
        if (poolSize == 0) {
            poolSize = HttpClientUtil.DEFLUT_POOL_SIZE;
        }

        if (maxPerRoute == 0) {
            maxPerRoute = HttpClientUtil.DEFAULT_MAX_PER_ROUTE;
        }

        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        connManager.setMaxTotal(poolSize);
        connManager.setDefaultMaxPerRoute(maxPerRoute);

        HttpClientBuilder httpBuilder = HttpClients.custom();
        httpBuilder.setConnectionManager(connManager).build();

        RequestConfig config = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(1000).build();
        HttpClientBuilder builder = HttpClients.custom().setConnectionManager(connManager).setDefaultRequestConfig(config);
        HttpClientUtil instance = new HttpClientUtil();
        instance.httpBuilder = builder;
        instance.httpclient = builder.build();
        return instance;
    }

    public void setProxyHost(String hostname, int port) {
        HttpHost host = new HttpHost(hostname, port);
        this.httpclient = this.httpBuilder.setProxy(host).build();
    }


    public String httpPost(String url, Object param) {
        try {
            HttpPost post = new HttpPost(url);
            post.setEntity(buildStringHttpEntry(param));
            return execute(post);
        } catch (Exception e) {
            log.error("http request POST desc, url：{}, param:{}", url, param, e);
        }

        return null;
    }

    public String httpPost(String url, Object param, Map<String, String> headers) {
        try {
            HttpPost post = new HttpPost(url);
            addHeader(post, headers);
            post.setEntity(buildStringHttpEntry(param));
            return execute(post);
        } catch (Exception e) {
            log.error("http request POST desc, url：{}, param:{}", url, param, e);
        }

        return null;
    }

    public String httpGet(String url) {
        return httpGet(url, null);
    }

    public String httpGet(String url, Object object) {
        Map<String, String> param = BeanUtil.toStringMap(object);

        // 设置参数
        if (param != null && param.size() > 0) {
            if (!url.contains("?")) {
                url = url + "?";
            }

            StringBuilder sb = new StringBuilder();
            for (String key : param.keySet()) {
                sb.append(key);
                sb.append("=");
                sb.append(param.get(key));
                sb.append("&");
            }
            url = url + sb.toString();
        }

        try {
            HttpGet request = new HttpGet(url);
            return execute(request);
        } catch (Exception e) {
            log.error("http request GET desc, url：{}, param:{}", url, param, e);
        }

        return null;
    }


    private String execute(HttpUriRequest request) throws Exception {
        try {
            HttpResponse response = httpclient.execute(request);

            // if (request instanceof HttpPost) {
            // for (Header header : response.getAllHeaders()) {
            //
            // // 回写返回的cookie
            // if ("Set-Cookie".equalsIgnoreCase(header.getName())) {
            // headers.add(header);
            // }
            // }
            // }

            HttpEntity entity = response.getEntity();
            String data = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            EntityUtils.consume(entity);
            return data;
        } finally {
            if (automaticClose) {
                close();
            }
        }
    }

    public void addHeader(HttpUriRequest request, Map<String, String> headers) {
        // 设置消息头
        if (!MapUtils.isEmpty(headers)) {
            headers.forEach((k, v) -> request.setHeader(k, v));
        }
    }

    private void addHeader(HttpUriRequest request, List<Header> headers) {
        // 如果入参的headers为null,后面headers.add(header)是返回不出去的。这一句只是为了避免空指针异常
        if (CollectionUtils.isEmpty(headers)) {
            headers = new ArrayList<>();
        }

        // 添加请求头
        headers.forEach(header -> request.addHeader(header));
    }


    /**
     * 构建http请求实体，支持字符串及文件
     *
     * @param params
     *
     * @return
     */
    private HttpEntity buildStringHttpEntry(Object bean) {
        Map<String, String> map = BeanUtil.toStringMap(bean);

        List<NameValuePair> list = new ArrayList<>();

        for (String key : map.keySet()) {
            list.add(new BasicNameValuePair(key, map.get(key)));
        }

        return new UrlEncodedFormEntity(list, StandardCharsets.UTF_8);
    }

    @Override
    protected void finalize() {
        close();
    }

    public void close() {
        try {
            if (httpclient != null)
                httpclient.close();
        } catch (IOException e) {
            log.error("", e);
        }
    }
}
