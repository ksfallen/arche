package com.yhml.core.util;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yhml.core.cipher.AESUtil;
import com.yhml.core.cipher.EncodeUtil;
import com.yhml.core.cipher.MD5Util;

/**
 * 功能说明: cookie读写工具类<br>
 * 系统版本: v1.0<br>
 */
public class CookieUtil {
    private static final Logger logger = LoggerFactory.getLogger(CookieUtil.class);

    private static final String cookie_domain;
    private static final String cookie_encryptkey;
    private static final String cookie_encryptkey_md5;

    private static Map<String, Integer> cookies = new ConcurrentHashMap<>();

    static {
        cookie_domain = PropertiesUtil.getString("cookie.domain", "com.yhml");
        cookie_encryptkey = PropertiesUtil.getString("cookie.encryptkey", "yhml");
        cookie_encryptkey_md5 = MD5Util.encode(cookie_encryptkey);
    }

    /**
     * 从所有cookie中找到名为name的cookie，返回其value
     *
     * @param req
     * @param name
     *
     * @return
     */
    public static String getCookie(HttpServletRequest req, String name) {
        Cookie[] cookies = req.getCookies();
        if (ArrayUtils.isEmpty(cookies)) {
            return "";
        }

        Optional<Cookie> first = Arrays.stream(cookies).filter(cookie -> name.equals(cookie.getName())).findFirst();
        return first.isPresent() ? first.get().getValue() : "";
        // for (Cookie cookie : cookies) {
        //     if (name.equals(cookie.getName())) {
        //         return cookie.getValue();
        //     }
        // }
        //
        // return "";
    }


    public static String getCookieByDecode(HttpServletRequest req, String name) {
        try {
            return EncodeUtil.urlDecode(getCookie(req, name));
        } catch (IllegalArgumentException e) {
            logger.error("getCookieByDecode desc", e);
        }

        return "";
    }

    /**
     * cookie 加密
     */
    public static String encryptCookie(String str) {
        try {
            return AESUtil.encryptKey(str, cookie_encryptkey_md5);
        } catch (Exception e) {
            logger.error("加密失败" + e);
            return null;
        }
    }

    /**
     * cookie 解密
     *
     * @param orgcookie
     *
     * @return UserProfile null:解析失败或者密码错误
     */
    public static String decryptCookie(String orgcookie) {
        try {
            return AESUtil.decryptKey(orgcookie, cookie_encryptkey_md5);
        } catch (Exception e) {
            logger.error("解密失败" + e);
            return null;
        }

    }

    /**
     * cookie 设置
     */
    public static void setCookie(HttpServletResponse response, String k, String v, int time) {
        try {
            StringBuilder cookieContent = new StringBuilder();
            // cookie Id
            cookieContent.append(k);
            cookieContent.append("=");
            cookieContent.append(URLEncoder.encode(v, "UTF-8"));
            cookieContent.append(";");
            // cookie path
            cookieContent.append("Path=/;");
            // cookie domain
            cookieContent.append("Domain=");
            cookieContent.append(cookie_domain);
            cookieContent.append(";");
            // 存活时间
            if (time > 0) {
                cookieContent.append("Max-Age=");
                cookieContent.append(time);
                cookieContent.append(";");
            }
            // 防止客户端js 读取cookie值
            cookieContent.append("HTTPOnly");
            response.setHeader("Pragma", "no-core");
            response.addHeader("Cache-Control", "must-revalidate");
            response.addHeader("Cache-Control", "no-core");
            response.addHeader("Cache-Control", "no-store");
            response.setDateHeader("Expires", 0);
            response.addHeader("Set-Cookie", cookieContent.toString());

        } catch (Exception e) {
            logger.error("设置cookie异常：", e);
        }
    }

    /**
     * 读cookie，返回UserProfile对象(或者null)
     * @param request
     * @param key
     * @return
     * @throws Exception
     */
    // public static CookieUserBean readUser(HttpServletRequest request, String key) throws Exception {
    // if (request == null || StringUtils.isEmpty(key)) {
    // return null;
    // }
    // Cookie[] cookies = request.getCookies();
    // if (cookies != null && cookies.length > 0) {
    // // 1.读cookie
    // Map<String, String> params = new HashMap<String, String>();
    // for (Cookie cookie : cookies) {
    // String name = cookie.getName();
    // String value = cookie.getValue();
    // params.put(name, value);
    // }
    // // 2.组装切片
    // String base64String = ParamUtil.mergeParam(key, params);
    // return readUserFromBase64Str(base64String);
    // }
    // return null;
    // }

    /**
     * 从base64字符串中反序列化得到UserProfile
     * @param base64Str
     * @return
     * @throws Exception
     */
    // private static CookieUserBean readUserFromBase64Str(String base64Str) throws Exception {
    // if (StringUtils.isNotEmpty(base64Str)) {
    // // 4.解密
    // String plainStr = decryptCookie(base64Str);
    // try {
    // return FastJsonUtil.toJavaObject(plainStr, CookieUserBean.class);
    // } catch (Exception e) {
    // log.desc("解析失败", e);
    // }
    // }
    // return null;
    // }

    /**
     * 写 cookie
     * @param response
     * @param key
     * @param object
     * @throws IOException
     */
    // public static void writeObject(HttpServletRequest request, HttpServletResponse response, String key, Object
    // object) throws IOException {
    // Map<String, String> tokenMap = split2TokenMap(key, object);
    // // 写cookie
    // if (tokenMap != null && !tokenMap.isEmpty()) {
    // Set<Entry<String, String>> entryMap = tokenMap.entrySet();
    // Iterator<Entry<String, String>> it = entryMap.iterator();
    // while (it.hasNext()) {
    // Entry<String, String> entry = it.next();
    // Cookie tmpCookie = new Cookie(entry.getKey(), entry.getValue());
    // tmpCookie.setPath("/");
    // tmpCookie.setHttpOnly(true);
    // response.addCookie(tmpCookie);
    // }
    // }
    // }

    /**
     * 将对象序列化、加密、做切片，返回map
     * @param key cookie的name("_crh_user")
     * @param object UserProfile对象
     * @return
     * @throws IOException
     */
    // public static Map<String, String> split2TokenMap(String key, Object object) throws IOException {
    // // 1.转换：序列化为二进制
    // String jsonStr = JsonUtil.toJsonString(object);
    // // 2.加密
    // String encStr = encryptCookie(jsonStr);
    // // 4.切片
    // Map<String, String> tokenMap = ParamUtil.splitParam(key, encStr);
    // return tokenMap;
    // }

    /**
     * 清除指定name的cookie
     *
     * @param request
     * @param response
     * @param cookieName
     */
    public static void clearCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (StringUtils.equals(cookieName, cookie.getName())) {
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    // cookie.setValue(null)会被读取出来为"null"，所以这里要设置为空字符串
                    cookie.setValue("");
                    response.addCookie(cookie);
                }
            }
        }
    }

    public static void clearCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(0);
                cookie.setPath("/");
                // cookie.setValue(null)会被读取出来为"null"，所以这里要设置为空字符串
                cookie.setValue("");
                response.addCookie(cookie);
            }
        }
    }
}
