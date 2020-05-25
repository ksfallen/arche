package com.yhml.tools.ssr;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author: Jfeng
 * @date: 2019-06-18
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class V2rayBean {
    private String address;

    private String port;

    private String uuid;

    /**
     * 额外ID
     */
    private String alterId;

    /**
     * 加密方式 auto
     */
    private String security = "auto";

    /**
     * 传输协议 ws
     */
    private String network = "ws";

    /**
     * 伪装类型
     */
    private String headerType = "none";

    /**
     * 路径
     */
    private String path;

    /**
     * 底层传输安全
     */
    private String tls = "tls";



    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("address: ").append(address).append("\n");
        sb.append("port: ").append(port).append("\n");
        sb.append("uuid: ").append(uuid).append("\n");
        sb.append("alterId: ").append(alterId).append("\n");
        sb.append("security: ").append(security).append("\n");
        sb.append("network 网络:").append(network).append("\n");
        sb.append("type 类型: ").append(headerType).append("\n");
        sb.append("路径: ").append(path).append("\n");
        sb.append("security: ").append(security);
        return sb.toString();
    }
}
