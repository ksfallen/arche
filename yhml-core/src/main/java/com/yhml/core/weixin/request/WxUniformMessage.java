package com.yhml.core.weixin.request;


import java.util.Map;

import lombok.Data;

@Data
public class WxUniformMessage {
    /**
     * 用户openid
     */
    private String touser;

    private WeappTemplateMsg weapp_template_msg;


    public static class WeappTemplateMsg {

        /**
         * 小程序模板ID
         */
        private String template_id;
        /**
         * 小程序页面路径
         */
        private String page;
        /**
         * 小程序模板消息formid
         */
        private String form_id;
        /**
         * 小程序模板数据
         */
        private Map data;
        /**
         * 小程序模板放大关键词
         */
        private String emphasis_keyword;
    }

}
