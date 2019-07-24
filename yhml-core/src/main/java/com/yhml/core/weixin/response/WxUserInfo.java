package com.yhml.core.weixin.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author : Liw
 * @description :
 * @date : 2018/5/11 19:39
 */
@Getter
@Setter
@ToString
public class XcxUserInfo {
    private String openid;

    private String session_key;

    private String unionid;

    private Integer errcode;

    private String errmsg;
}
