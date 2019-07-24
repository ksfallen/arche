package com.yhml.core.weixin.request;

import lombok.Data;

/**
 * @author: Jfeng
 * @date: 2019-07-05
 */
@Data
public class WxAppInfo {
    private String grant_type = "client_credential";
    private String appid = "wxf4593c9f2c83e9d4";
    private String secret = "a35ab754bb7d0e5aea3894596fb07595";
}
