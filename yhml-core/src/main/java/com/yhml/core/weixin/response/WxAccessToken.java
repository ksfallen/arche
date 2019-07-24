package com.yhml.core.weixin.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: Jfeng
 * @date: 2019-07-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WxAccessToken extends WxResp {
    private String access_token;
    private Long expires_in;
}
