package com.yhml.core.wx;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: Jfeng
 * @date: 2019-07-05
 */
@Slf4j
public class WeixinApi {

    private static final String url_accesstoken = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID" +
            "&secret=APPSECRET";
}
