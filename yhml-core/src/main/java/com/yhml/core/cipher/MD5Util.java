package com.yhml.core.cipher;

import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.io.Charsets;

import java.io.UnsupportedEncodingException;

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

    public static String encodeWithSalt(String str, String salt) {
        try {
            return  Md5Crypt.md5Crypt(str.getBytes(Charsets.UTF_8), salt);
        } catch (Exception e) {
            log.error("Unsupported EncodingException.", e);
        }
        return "";
    }

}
