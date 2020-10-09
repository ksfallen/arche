package com.yhml.core.util;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestUtil {

    /**
     * 静态取request
     * 本地junit非容器运行时无法使用，服务未启动完成无法使用
     */
    public static HttpServletRequest getRequest() {
        try {
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            if (attr != null) {
                return attr.getRequest();
            }
        } catch (Exception ignored) {

        }
        return null;
    }

    /**
     * 取重定向完整根路径，防止重复contextpath
     */
    public static String getRootRedirectURL(HttpServletRequest request, String page) {
        StringBuffer url = request.getRequestURL();
        String redirect = url.substring(0, url.indexOf(request.getRequestURI()));
        if (StringUtils.isEmpty(page)) {
            return "redirect:" + redirect;
        }
        if (!redirect.endsWith("/") && !page.startsWith("/")) {
            redirect = redirect + "/";
        }
        return "redirect:" + redirect + page;
    }

    /**
     * 取IP地址
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        /*Proxy-Client-IP 字段和 WL-Proxy-Client-IP 字段只在 Apache（Weblogic Plug-In Enable）+WebLogic 搭配下出现，其中“WL” 就是 WebLogic 的缩写*/

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteHost();
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();

            //若是本地,则根据网卡取本机配置的IP
            if (ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1"))
                try {
                    InetAddress inet = InetAddress.getLocalHost();
                    ip = inet.getHostAddress();
                } catch (UnknownHostException ignored) {
                }
        }

        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 0 && ip.indexOf(",") > 0) {
            ip = ip.substring(0, ip.indexOf(","));
        }

        return ip;
    }

    /**
     * @category 获取request参数
     */
    public static Map<String, String> getParams(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        Enumeration<String> enums = request.getParameterNames();

        while (enums.hasMoreElements()) {
            String name = enums.nextElement();
            String value = request.getParameter(name);
            if (request.getParameterValues(name) != null) {
                value = StringUtils.join(request.getParameterValues(name), ",");
            }
            map.put(name, value);
        }

        return map;
    }

    public static void sendResponse(HttpServletResponse response, Object body) {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        try {
            response.getWriter().print(JsonUtil.toJson(body));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException ignored) {
        }
    }

    /**
     * 根据Ip返回省市区地址
     */
    public static Address getAddressByIp(String ipaddr) {
        // String urlStr = "http://ip.taobao.com/service/getIpInfo.php";
        String url = "http://ip.taobao.com/service/getIpInfo2.php?ip=" + ipaddr;
        String result = HttpClientUtil.getInstance().httpGet(url);
        log.debug("getAddressByIp ip={}, result={}", ipaddr, result);

        Map<String, Object> object = JsonUtil.toMap(result);
        if (object.containsKey("code") && object.get("code").toString().equals("0")) {
            String data = (String) object.get("data");
            Address address = JsonUtil.parse(data, Address.class);
            System.out.println("address = " + address);
            return address;
        }

        return null;
    }

    /*地址类,包含省市区*/
    @Data
    public static class Address {
        private String countryId;
        private String country;
        private String area;
        @JSONField(name = "region")
        private String province;
        @JSONField(name = "regionId")
        private String provinceId;
        private String cityId;
        private String city;
        private String countyId;
        private String county;
        private String ispId;
        private String isp;
    }
}
