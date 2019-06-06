package com.yhml.core.util;

import java.net.URI;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.yhml.core.context.SpringContext;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: Jfeng
 * @date: 2018/4/25
 */
@Slf4j
public class RestTemplateUtil {

    private static RestTemplate restTemplate;

    private static HttpHeaders headers = new HttpHeaders();

    // private static HttpEntity<String> request;

    static {
        restTemplate = SpringContext.getBean(RestTemplate.class);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // headers.setAccept(Lists.newArrayList(MediaType.APPLICATION_JSON_UTF8));
        // request = new HttpEntity<>(null, headers);
    }


    public String doPost(String url, Map<String, ?> params) {
        return doPost(url, params, null);
    }

    public String doGet(String url, Map<String, ?> params) {
        return doGet(url, params, null);
    }

    public String doPost(String url, Map<String, ?> params, Map<String, String> headers) {
        ResponseEntity<String> rss = exchange(url, HttpMethod.POST, params, headers);
        return rss.getBody();
    }

    public String doGet(String url, Map<String, ?> params, Map<String, String> headers) {
        ResponseEntity<String> rss = exchange(url, HttpMethod.GET, params, headers);
        return rss.getBody();
    }

    private ResponseEntity<String> exchange(HttpServletRequest request, HttpMethod method, String url, Map<String, ?> params) {
        //获取header信息
        HttpHeaders httpHeaders = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            httpHeaders.add(key, value);
        }
        //获取parameter信息
        if (params == null) {
            params = request.getParameterMap();
        }

        HttpEntity<String> requestEntity = new HttpEntity<>(null, httpHeaders);
        return restTemplate.exchange(url, method, requestEntity, String.class, params);
    }

    private ResponseEntity<String> exchange(String url, HttpMethod method, Map<String, ?> params, Map<String, String> headers) {

        //获取header信息
        HttpHeaders httpHeaders = new HttpHeaders();
        if (MapUtils.isNotEmpty(headers)) {
            headers.forEach((k, v) -> httpHeaders.add(k, v));
        }

        HttpEntity<String> requestEntity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> rss = restTemplate.exchange(url, method, requestEntity, String.class, params);
        return rss;
    }

    private static URI getUrl(String url, Object params) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

        Map<String, Object> map = BeanUtil.toMap(params);
        map.forEach((key, value) -> builder.queryParam(key, value));

        return builder.build().encode().toUri();
    }



    // public static String getForObject(String url) {
    // String ret = "";
    // try {
    //     ret = restTemplate.getForObject(url, String.class);
    // } catch (RestClientException e) {
    //     log.desc("http getString desc, url {}", url, e);
    // }
    //
    // return ret;
    // return restTemplate.getForObject(url, String.class);
    // }

  /*  private static HttpEntity request() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Lists.newArrayList(MediaType.APPLICATION_JSON_UTF8));
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestJson = JsonUtil.toJsonStringWithoutNull(body);
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        return request;
    }*/


    public static String postForObject(String url, Object params) {
        // String ret = "";
        // try {
        //     MultiValueMap<String, String> body = BeanUtil.toMultiValueMap(params);
        //     HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        //     ret = restTemplate.postForObject(url, request, String.class);
        //
        //     // ret = restTemplate.postForObject(getUrl(url, params), request, String.class);
        // } catch (RestClientException e) {
        //     log.desc("http post desc, url:{}, params:{}", url, params, e);
        // }
        //
        // return ret;

        MultiValueMap<String, String> body = BeanUtil.toMultiValueMap(params);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        return restTemplate.postForObject(url, request, String.class);
    }

    public static String getForObject(String url, Object params) {
        // String ret = "";
        // try {
        //     ret = restTemplate.getForObject(getUrl(url, params), String.class);
        // } catch (RestClientException e) {
        //     log.desc("http getString desc, url{}, params{}", url, params, e);
        // }
        //
        // return ret;
        return restTemplate.getForObject(getUrl(url, params), String.class);
    }

    public static String getForObject(String url) {
        // String ret = "";
        // try {
        //     ret = restTemplate.getForObject(url, String.class);
        // } catch (RestClientException e) {
        //     log.desc("http getString desc, url {}", url, e);
        // }
        //
        // return ret;
        return restTemplate.getForObject(url, String.class);
    }

  /*  private static HttpEntity request() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Lists.newArrayList(MediaType.APPLICATION_JSON_UTF8));
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestJson = JsonUtil.toJsonStringWithoutNull(body);
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        return request;
    }*/


    // private static String getUrl2(String url, Object params) {
    //     Map<String, Object> map = BeanUtil.toMap(params);
    //     String ret = url + "?" + map.entrySet().stream().map(k -> String.format("%s=%s", k.getKey(), k.getValue())).collect(Collectors
    // .joining("&"));
    //     return ret;
    // }
}
