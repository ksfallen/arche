package com.yhml.core.cipher;


/**
 * 密码加密工具类
 */
public class CipherUtil {

    public static final int HASH_INTERATIONS = 1024;
    public static final int SALT_SIZE = 8;

    /**
     * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
     */
    public static String entryptPassword(String plainPassword) {
        byte[] salt = DigestUtil.generateSalt(SALT_SIZE);
        byte[] hashPassword = DigestUtil.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
        return EncodeUtil.encodeHex(salt) + EncodeUtil.encodeHex(hashPassword);
    }

    /**
     * 验证密码
     *
     * @param plainPassword 明文密码
     * @param password      密文密码
     * @return 验证成功返回true
     */
    public static boolean validatePassword(String plainPassword, String password) {
        try {
            byte[] salt = EncodeUtil.decodeHex(password.substring(0, 16));
            byte[] hashPassword = DigestUtil.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
            return password.equals(EncodeUtil.encodeHex(salt) + EncodeUtil.encodeHex(hashPassword));
        } catch (Exception e) {
            return false;
        }
    }
}
