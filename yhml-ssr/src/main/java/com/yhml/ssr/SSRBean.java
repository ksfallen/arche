package com.yhml.ssr;

import com.baidu.aip.util.Base64Util;
import org.jsoup.helper.StringUtil;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author: Jfeng
 * @date: 2019-06-18
 */
@Data
@AllArgsConstructor
@Accessors(chain = true)
public class SSRBean {
    private String server;
    private String port;
    private String password;

    /**
     * 加密方式 aes-256-cfb
     */
    private String method;

    /**
     * 协议 origin
     */
    private String protocol = "origin";

    /**
     * 混淆 plain
     */
    private String obfs = "plain";

    /**
     * 备注
     */
    private String remarks = "free";
    /**
     * 分组
     */
    private String group;

    /**
     * false
     */
    private Boolean auth;

    public SSRBean() {

    }

    public SSRBean(String ssr) {
        if (ssr.startsWith("ssr://")) {
            parseSSRLink(ssr);
        } else if (ssr.startsWith("ss://")) {
            parseSSLink(ssr);
        }
    }

    /**
     * ss://method:password@server:port
     *
     * ss://YWVzLTI1Ni1jZmI6ZG9uZ3RhaXdhZ24uY29tQDE2Mi4yNDUuMjM5Ljc0OjM0NTY3
     * aes-256-cfb:dongtaiwagn.com@162.245.239.74:34567
     *
     * @return
     */
    public String toSSLink() {
        List<String> list = new ArrayList<>();
        list.add(this.method);
        list.add(this.password + "@" + this.server);
        list.add(this.port);
        String ss = join(":", list);
        String decode = encodeBase64(ss);
        return "ss://".concat(decode);
    }

    /**
     * ss://YWVzLTI1Ni1jZmI6ZG9uZ3RhaXdhZ24uY29tQDE2Mi4yNDUuMjM5Ljc0OjM0NTY3
     * 字符串中有包含 – 和 _ 的字符，要先分别替换为 + 和 /
     *
     * ss://method:password@server:port
     * aes-256-cfb:dongtaiwagn.com@162.245.239.74:34567
     */
    public void parseSSLink(String ss) {
        ss = ss.replace("ss://", "").replace("–", "+").replace("_", "/");
        String decode = decodeBase64(ss);
        String[] val = decode.split(":");

        Arrays.stream(val).forEach(s -> System.out.println(s));

        int i = 0;
        for (String value : val) {
            // @formatter:off
            switch (i++) {
                case 0: this.method = value; break;
                case 1:
                    this.password = value.split("@")[0];
                    this.server = value.split("@")[1];
                    break;
                case 2: this.port = value; break;
                case 4: this.obfs = value; break;
                case 5: this.password = value.split("/")[0]; break;
                default:break;
            }
            // @formatter:on
        }
    }

    /**
     * https://coderschool.cn/2498.html
     * ssr://server:port:protocol:method:obfs:password_base64/?params_base64
     *
     * password_base64 就是密码被 base64编码 后的字符串，
     * params_base64 则是协议参数、混淆参数、备注及Group对应的参数值被 base64编码 后拼接而成的字符串。
     *
     * 184.170.208.44:16902:auth_aes128_sha1:chacha20:tls1.2_ticket_auth:MTk1MDQ5ODAwMA/?o
     * server:port:protocol:method:obfs:MTk1MDQ5ODAwMA/?obfsparam=&protoparam=&remarks=&group=
     *
     * @return
     */
    public String toSSRLink() {
        if (StringUtil.isBlank(this.server)) {
            return "";
        }

        List<String> list = new ArrayList<>();
        list.add(this.server);
        list.add(this.port);
        list.add(this.protocol);
        list.add(this.method);
        list.add(this.obfs);
        list.add(encodeBase64(this.password));

        String ssr = join(":", list);
        // String params = "remarks=" + Base64Util.encodeBase64(this.remarks);
        // ssr = ssr + "/?" + params;
        return "ssr://" + encodeBase64(ssr);
    }

    /**
     * ssr://server:port:protocol:method:obfs:password_base64/?params_base64
     *
     * @param ssr
     */
    public void parseSSRLink(String ssr) {
        ssr = ssr.replace("ssr://", "").replace("–", "+").replace("_", "/");
        String decode = decodeBase64(ssr);
        String[] val = decode.split(":");

        Arrays.stream(val).forEach(s -> System.out.println(s));

        int i = 0;
        for (String value : val) {
            // @formatter:off
            switch (i++) {
                case 0: this.server = value; break;
                case 1: this.port = value; break;
                case 2: this.protocol = value; break;
                case 3: this.method = value; break;
                case 4: this.obfs = value; break;
                case 5:
                    String password = value.split("/")[0];
                    this.password = decodeBase64(password);
                    break;
                default: break;
            }
            // @formatter:on
        }
    }

    public static void main(String[] args) {
        // 184.170.208.44:16902:auth_aes128_sha1:chacha20:tls1.2_ticket_auth:MTk1MDQ5ODAwMA/?o
        String str = "ssr" +
                "://MTg0LjE3MC4yMDguNDQ6MTY5MDI6YXV0aF9hZXMxMjhfc2hhMTpjaGFjaGEyMDp0bHMxLjJfdGlja2V0X2F1dGg6TVRrMU1EUTVPREF3TUEvP29";

        System.out.println("===== ss ====");
        SSRBean bean = new SSRBean();
        String ss = "ss://YWVzLTI1Ni1jZmI6ZG9uZ3RhaXdhZ24uY29tQDE2Mi4yNDUuMjM5Ljc0OjM0NTY3";
        bean.parseSSLink(ss);
        System.out.println(bean);
        System.out.println(bean.toSSLink());
        System.out.println(bean.toSSLink().equals(ss));

        System.out.println("===== ssr ====");
        str = "ssr://NjQuMTM3LjIwMS4yNDY6Mjk4NzphdXRoX3NoYTFfdjQ6Y2hhY2hhMjA6dGxzMS4yX3RpY2tldF9hdXRoOlpHOTFZaTVwYnk5emMzcG9abmd2S21SdmRXSXVZbWxrTDNOemVtaG1lQzhxTWprNE53PT0vP3JlbWFya3M9Wm5KbFpRPT0=";
        bean = new SSRBean();
        bean.parseSSRLink(str);
        System.out.println(bean);
        System.out.println(bean.toSSRLink());
        System.out.println(bean.toSSRLink().equals(str));

    }


    public static String join(String separator, Iterable<? extends CharSequence> elements) {
        if (elements == null) {
            return "";
        }

        StringJoiner joiner = new StringJoiner(separator);
        elements.forEach(c -> {
            if (c != null && c.length() > 0) {
                joiner.add(c);
            }
        });

        return joiner.toString();
    }



    public static String decodeBase64(String base64String) {
        byte[] bytes = Base64Util.decode(base64String);
        return new String(bytes);
    }

    public static String encodeBase64(String str) {
        byte[] binaryData = str.getBytes();
        return Base64Util.encode(binaryData);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("账号: ").append(server).append("\n");
        sb.append("端口: ").append(port).append("\n");
        sb.append("密码: ").append(password).append("\n");
        sb.append("加密方式:").append(method).append("\n");
        sb.append("协议: ").append(protocol).append("\n");
        sb.append("混淆: ").append(obfs);
        return sb.toString();
    }
}
