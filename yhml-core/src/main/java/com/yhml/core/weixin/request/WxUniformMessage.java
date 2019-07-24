package com.yhml.core.weixin.request;


import lombok.Data;

/**
 * @author congzhang
 */
@Data
public class ReqUniformMessage {
    /**
     * 用户openid
     */
    private String touser;

    private WeappTemplateMsg weapp_template_msg;

}
