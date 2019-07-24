package com.yhml.core.weixin.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class WxUserInfo extends WxResp {
    private String openid;

    private String session_key;

    private String unionid;

}
