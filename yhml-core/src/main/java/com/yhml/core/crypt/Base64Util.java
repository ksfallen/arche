package com.yhml.core.crypt;

/**
 * @author: Jfeng
 * @date: 2019-06-18
 */
public class Base64 {

    public String decode(String base64String) {
        byte[] bytes = org.apache.commons.codec.binary.Base64.decodeBase64(base64String);
        return new String(bytes);
    }
}
