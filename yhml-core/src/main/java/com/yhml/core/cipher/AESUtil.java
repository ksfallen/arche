package com.yhml.core.cipher;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class AESUtil {

    /**
     * AES加密
     *
     * @return
     */
    public static byte[] encrypt(byte[] content, byte[] password) {
        try {
            SecretKeySpec key = new SecretKeySpec(password, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");// 创建密码器
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(password));// 初始化
            int plaintextLength = content.length;
            if (plaintextLength % 16 != 0) {
                plaintextLength = plaintextLength + (16 - (plaintextLength % 16));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(content, 0, plaintext, 0, content.length);
            return cipher.doFinal(plaintext);
        } catch (Exception e) {
        }

        return null;
    }

    /**
     * AES加密
     *
     * @param content
     * @param password
     * @return
     */
    public static byte[] encrypt(byte[] content, byte[] password, byte[] iv) {
        try {
            SecretKeySpec key = new SecretKeySpec(password, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");// 创建密码器
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));// 初始化
            int plaintextLength = content.length;
            if (plaintextLength % 16 != 0) {
                plaintextLength = plaintextLength + (16 - (plaintextLength % 16));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(content, 0, plaintext, 0, content.length);
            byte[] result = cipher.doFinal(plaintext);
            return result; // 加密
        } catch (Exception e) {
        }

        return null;
    }

    /**
     * AES解密
     *
     * @param content
     * @param password
     * @return
     */
    public static byte[] decrypt(byte[] content, byte[] password) {
        try {
            SecretKeySpec key = new SecretKeySpec(password, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(password));// 初始化
            int plaintextLength = content.length;
            if (plaintextLength % 16 != 0) {
                plaintextLength = plaintextLength + (16 - (plaintextLength % 16));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(content, 0, plaintext, 0, content.length);

            byte[] result = cipher.doFinal(plaintext);
            return result; // 加密
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * AES解密
     *
     * @param content
     * @param password
     * @return
     */
    public static byte[] decrypt(byte[] content, byte[] password, byte[] iv) {
        try {
            SecretKeySpec key = new SecretKeySpec(password, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));// 初始化
            int plaintextLength = content.length;
            if (plaintextLength % 16 != 0) {
                plaintextLength = plaintextLength + (16 - (plaintextLength % 16));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(content, 0, plaintext, 0, content.length);

            byte[] result = cipher.doFinal(plaintext);
            return result; // 加密
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 补全域名密钥至16位
     *
     * @param password 密钥 使用域名
     * @return
     */
    public static byte[] passwordLengthTo16(String password) {
        return (password + "cairenhuicairenhui").substring(0, 16).getBytes();
    }

    /**
     * 对传入的base64key解码
     *
     * @param content base64key
     * @param domain  域名
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String decryptKey(String content, String domain) throws UnsupportedEncodingException {
        byte[] password = AESUtil.passwordLengthTo16(domain);
        byte[] keyAfterBase64 = Base64.decodeBase64(content.getBytes());
        byte[] keyAfter = AESUtil.decrypt(keyAfterBase64, password);
        return new String(keyAfter, "UTF-8").trim();
    }

    public static String encryptKey(String content, String domain) throws IOException {
        byte[] jsonBeforeEncrypt = content.getBytes("UTF-8");
        byte[] password = AESUtil.passwordLengthTo16(domain);
        byte[] jsonAfterEncrypt = AESUtil.encrypt(jsonBeforeEncrypt, password);
        byte[] jsonAfterEncryptAndBase64 = Base64.encodeBase64(jsonAfterEncrypt);
        return new String(jsonAfterEncryptAndBase64);
    }

}
