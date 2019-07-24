package com.yhml.core.weixin.dto;

import java.util.Date;

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
    private Date createTime = new Date();
}
