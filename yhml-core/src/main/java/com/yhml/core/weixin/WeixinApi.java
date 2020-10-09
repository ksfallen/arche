package com.yhml.core.weixin;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.yhml.core.cipher.Base64Util;
import com.yhml.core.util.JsonUtil;
import com.yhml.core.util.RestTemplateUtil;
import com.yhml.core.weixin.request.WxAppInfo;
import com.yhml.core.weixin.request.WxQrCodeReq;
import com.yhml.core.weixin.response.WxAccessToken;
import com.yhml.core.weixin.response.WxResp;
import com.yhml.core.weixin.response.WxUserInfo;

import cn.hutool.core.net.URLEncoder;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * https://developers.weixin.qq.com/miniprogram/dev/api-backend/
 *
 * @author: Jfeng
 * @date: 2019-07-05
 */
@Slf4j
public class WeixinApi {

    private static final String URL_USER_INFO = "https://api.weixin.qq.com/sns/jscode2session";
    private static final String URL_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token";

    /**
     * 调用分钟频率受限(5000次/分钟) 生成小程序码，可接受页面参数较短，生成个数不受限
     */
    private static final String URL_QRCODE_B = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=";

    /**
     * 生成二维码，可接受 path 参数较长，生成个数受限
     */
    private static final String URL_QRCODE_C = "https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=";

    private static Cache<String, String> cache = CacheBuilder.newBuilder().maximumSize(100) // 设置缓存的最大容量
            .expireAfterWrite(7000, TimeUnit.SECONDS).concurrencyLevel(10) // 设置并发级别为10
            .build();

    @Autowired
    @Setter
    private RestTemplateUtil restTemplate;


    public static void main(String[] args) {
        WeixinApi api = new WeixinApi();
        api.setRestTemplate(new RestTemplateUtil());

        WxQrCodeReq req = new WxQrCodeReq();
        // api.getAccessToken(req);
        // api.createQRCode(req);

    }

    public String getAccessToken(WxAppInfo appInfo) {
        return getAccessToken(appInfo.getAppid(), appInfo.getSecret());
    }

    /**
     * 获得用户accessToken
     */
    public String getAccessToken(String appid, String secret) {
        Map<String, String> map = new HashMap<>();
        map.put("grant_type", "client_credential");
        map.put("appid", appid);
        map.put("secret", secret);
        String resp = restTemplate.doGet(URL_ACCESS_TOKEN, map);
        WxAccessToken token = JsonUtil.parse(resp, WxAccessToken.class);

        if (WxResp.error(token)) {
            log.info("小程序查询用户token失败. {}", resp);
            return null;
        }

        // String key = appid + ":" + secret;
        // cache.put(key, token.getAccess_token());

        return token.getAccess_token();
    }

    // public String getAccessToken(String appid, String secret) {
    //     String key = appid + ":" + secret;
    //     String token = null;
    //     try {
    //         token = cache.get(key, new Callable<String>() {
    //             @Override
    //             public String call() {
    //                 WxAccessToken accessToken = getWxAccessToken(appid, secret);
    //                 return accessToken.getAccess_token();
    //             }
    //         });
    //     } catch (ExecutionException e) {
    //         log.error("", e);
    //     }
    //
    //     return token;
    // }

    /**
     * 小程序生成二维码方式，有次数限制，无限制
     *
     * @param req
     * @return
     */
    public String createQRCode(WxQrCodeReq req) {
        // String token = getAccessToken(req.getAppid(), req.getSecret());
        String token = "23_HZZjH8viA-bImVqUcpNDCeWpUivKjps4UXL_MbbrvCzsITb8XEcwwydqvuWfC0n-QJc9fPnDaOx" +
                "-FQupQLb5B9cJCw0zG78bzZTEc6GWDBFUKMdZiI-POhV7kR0PMKbAFAURP";

        // Map<String, Object> param = new HashMap<>(16);
        // param.put("scene", URLEncoder.QUERY.encode(req.getScene(), StandardCharsets.UTF_8));
        // param.put("path", "?userId=123");
        // param.put("page", req.getPage());
        // param.put("width", req.getWidth());

        ResponseEntity resp = restTemplate.doPost(URL_QRCODE_B + token, req, byte[].class);

        if (null != resp && HttpStatus.OK.equals(resp.getStatusCode())) {
            byte[] body = (byte[]) resp.getBody();

            WxResp data = JsonUtil.parse(new String(body), WxResp.class);
            if (WxResp.success(data)) {
                String base64 = Base64Util.encodeBase64(body);
                log.info("QRCode:{}", base64);
                return base64;
            }

            log.info("createQRCode error, {}", data);
        }

        return null;
    }

    public String requestPayment(WxQrCodeReq req) {
        // String token = getAccessToken(req.getAppid(), req.getSecret());
        String token = "23_HZZjH8viA-bImVqUcpNDCeWpUivKjps4UXL_MbbrvCzsITb8XEcwwydqvuWfC0n-QJc9fPnDaOx" +
                "-FQupQLb5B9cJCw0zG78bzZTEc6GWDBFUKMdZiI-POhV7kR0PMKbAFAURP";

        Map<String, Object> param = new HashMap<>(16);
        param.put("appId", URLEncoder.QUERY.encode(req.getScene(), StandardCharsets.UTF_8));
        param.put("timeStamp", "?userId=123");
        param.put("nonceStr", req.getPage());
        param.put("package", req.getWidth());
        param.put("signType", req.getWidth());

        ResponseEntity resp = restTemplate.doPost(URL_QRCODE_B + token, req, byte[].class);

        if (null != resp && HttpStatus.OK.equals(resp.getStatusCode())) {
            byte[] body = (byte[]) resp.getBody();

            WxResp data = JsonUtil.parse(new String(body), WxResp.class);
            if (WxResp.success(data)) {
                String base64 = Base64Util.encodeBase64(body);
                log.info("QRCode:{}", base64);
                return base64;
            }

            log.info("createQRCode error, {}", data);
        }

        return null;
    }

    /**
     * 小程序推送服务通知
     *
     * @param accessToken
     * @param req
     * @return
     */
    // public Boolean sendUniformMessage(String accessToken, ReqUniformMessage req){
    //https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token=ACCESS_TOKEN
    // ResponseEntity<Object> resp = null;
    // try {
    //     resp = restTemplate.postForEntity(url, req, Object.class);
    //     if(null != resp && HttpStatus.OK.equals(resp.getStatusCode())){
    //         Object result = resp.getBody();
    //         log.info("wx push message success. {}", result);
    //         return true;
    //     }
    //     log.info("wx push message fail. {}", resp);
    // } catch (Exception e) {
    //     log.error("wx push message err. {}. {}. {}.", url, resp, e.getMessage());
    //     return false;
    // }
    // return false;
    // }


    /**
     * 小程序登录 查询用户信息成功
     */
    public WxUserInfo queryUserInfoByJscode(String appid, String secret, String jsCode) {
        Map<String, String> map = new HashMap<>();
        map.put("grant_type", "authorization_code");
        map.put("appid", appid);
        map.put("secret", secret);
        map.put("js_code", jsCode);

        String resp = restTemplate.doGet(URL_USER_INFO, map);

        WxUserInfo userInfo = JsonUtil.parse(resp, WxUserInfo.class);

        if (WxResp.error(userInfo)) {
            log.info("小程序登录 查询用户信息失败. {}", resp);
            return null;
        }

        return userInfo;
    }
}
