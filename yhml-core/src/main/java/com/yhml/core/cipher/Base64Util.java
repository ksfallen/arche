package com.yhml.core.cipher;

/**
 * @author: Jfeng
 * @date: 2019-06-18
 */
public class Base64Util {

    public static String decodeBase64(String base64String) {
        byte[] bytes = org.apache.commons.codec.binary.Base64.decodeBase64(base64String);
        return new String(bytes);
    }

    public static String encodeBase64(String str) {
        byte[] binaryData = str.getBytes();
        byte[] bytes = org.apache.commons.codec.binary.Base64.encodeBase64(binaryData);
        return new String(bytes);
    }

    public static String encodeBase64(byte[] binaryData) {
        byte[] bytes = org.apache.commons.codec.binary.Base64.encodeBase64(binaryData);
        return new String(bytes);
    }
}
