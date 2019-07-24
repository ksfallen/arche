package com.yhml.core.weixin.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: Jfeng
 * @date: 2019-07-05
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WxQrCodeReq extends WxAppInfo {
    private Integer width = 280;
    private String page;
    private String scene;
}
