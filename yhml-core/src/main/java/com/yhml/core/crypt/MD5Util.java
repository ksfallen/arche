package com.yhml.core.crypt;

import java.io.UnsupportedEncodingException;

import org.apache.commons.io.Charsets;

import lombok.extern.slf4j.Slf4j;

/**
 * MD5加密算法
 */
@Slf4j
public class MD5Util {

    public static String encode(String str) {
        byte[] data = str.getBytes(Charsets.UTF_8);
        byte[] bytes = DigestUtil.md5(data);
        return EncodeUtil.encodeHex(bytes);
    }

    public static String encode(String str, String charset) {
        try {
            byte[] data = str.getBytes(charset);
            byte[] bytes = DigestUtil.md5(data);
            return EncodeUtil.encodeHex(bytes);
        } catch (UnsupportedEncodingException e) {
            log.error("Unsupported EncodingException.", e);
        }
        return "";
    }

    // private static String encrypt(byte[] data) {

    // StringBuilder sb = new StringBuilder();
    // // byte2hex
    // for (int i = 0; i < bytes.length; i++) {
    //     String hex = Integer.toHexString(bytes[i] & 0xff);
    //     if (hex.length() == 1) {
    //         sb.append("0");
    //     }
    //     sb.append(hex);
    // }
    //
    // return sb.toString();
    // }


}
