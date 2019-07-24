package com.yhml.core.weixin.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WxErrorCode {

    WX_SAMPLE_TEMPLATE_ERROR("900031", "消息模板不存在"),
    WX_QR_CODE_ERROR("900041", "获取小程序二维码失败"),
    WX_PARAM_ERROR("900051", "参数错误")
    ;



    private String code;
    private String msg;
}
