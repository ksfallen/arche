package com.yhml.core.util;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import cn.hutool.core.date.TimeInterval;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: Jfeng
 * @date: 2018/4/25
 */
@Slf4j
public class RestTemplateUtil {

    @Setter
    private RestTemplate restTemplate = new RestTemplate();


    public ResponseEntity doPost(String url, Object params, Class responseType) {
        String json = JsonUtil.toJsonString(params);
        return exchange(HttpMethod.POST, url, json, null, responseType);
    }

    public String doPost(String url, Map<String, ?> params) {
        String json = JsonUtil.toJsonString(params);
        return exchange(HttpMethod.POST, url, json, null);
    }

    public String doGet(String url) {
        return doGet(url, null);
    }

    public String doGet(String url, Object params) {
        return doGet(url, params, null);
    }

    public String doGet(String url, Object params, HttpHeaders headers) {
        return exchange(HttpMethod.GET, url, params, headers);
    }

    private String exchange(HttpMethod method, String url, Object params, HttpHeaders headers) {
        ResponseEntity<String> entity = exchange(method, url, params, headers, String.class);
        if (entity != null) {
            log.info("<== ret:{}", entity.getBody());
            return entity.getBody();
        }

        return "";
    }

    private ResponseEntity exchange(HttpMethod method, String url, Object params, HttpHeaders headers, Class responseType) {
        if (headers == null) {
            headers = new HttpHeaders();
        }

        HttpEntity request = new HttpEntity<>(headers);
        if (method.equals(HttpMethod.GET)) {
            url = getUrl(url, params);
        }

        if (method.equals(HttpMethod.POST)) {
            request = new HttpEntity<>(params, headers);
        }

        log.info("==> url:{} params:{} header:{}", url, params, headers);

        TimeInterval time = new TimeInterval();
        time.start();

        ResponseEntity<String> entity = null;

        try {
            entity = restTemplate.exchange(url, method, request, responseType);
        } catch (RestClientException e) {
            log.error("", e);
        }

        log.info("<== time:{}ms, code:{}", time.intervalMs(), entity.getStatusCode());

        return entity;
    }

    private static String getUrl(String url, Object params) {
        Map<String, String> map = BeanUtil.toStringMap(params);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        if (!CollectionUtils.isEmpty(map)) {
            map.forEach((key, value) -> builder.queryParam(key, value));
        }

        return builder.build().encode().toUri().toString();
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


    // public  String postForObject(String url, Object params) {
    //     MultiValueMap<String, String> body = BeanUtil.toMultiValueMap(params);
    //     HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
    //     return restTemplate.postForObject(url, request, String.class);
    // }

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
