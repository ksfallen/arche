package com.yhml.core.weixin.response;

import lombok.Data;

/**
 * @author: Jfeng
 * @date: 2019-07-05
 */
@Data
public class WxResp {
    private int errcode;
    private String errmsg;

    public static boolean success(WxResp resp) {
        return resp == null || resp.getErrcode() == 0;
    }

    public static boolean error(WxResp resp) {
        return !success(resp);
    }
}
