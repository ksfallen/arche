package com.yhml.core.cipher;

import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author: Jfeng
 * @date: 2018/5/9
 */
public class DESUtil {

    /**
     * Used building output as Hex
     */
    private static final byte[] keyBytes = {0x02, 0x04, 0x06, 0x08, 0x10, 0x12, 0x14, 0x16, (byte) 0x99, (byte) 0x97, (byte) 0x95, (byte)
            0x93, (byte) 0x91, (byte) 0x89, (byte) 0x87, (byte) 0x85, 0x02, 0x04, 0x06, 0x08, 0x10, 0x12, 0x14, 0x16};
    private static final byte[] IV = {1, 2, 3, 4, 5, 6, 7, 8};

    /**
     * 解密
     */
    public static String decrypt(String src) {
        if (StringUtils.isBlank(src)) {
            return "";
        }
        return decryptMode(keyBytes, src);
    }

    /**
     * 指定密钥解密
     *
     * @param keybyte 密钥
     * @param src     密文
     * @return
     */
    private static String decryptMode(byte[] keybyte, String src) {
        try {
            SecretKey deskey = new SecretKeySpec(keybyte, "DESede");
            Cipher c1 = Cipher.getInstance("DESede/ECB/NoPadding");

            IvParameterSpec iv = new IvParameterSpec(IV);
            // c1.init(Cipher.DECRYPT_MODE, deskey, iv);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            byte[] d = c1.doFinal(hex2Byte(src));

            return (new String(d)).trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static byte[] hex2Byte(String str) {
        if (str == null) {
            return null;
        }

        str = str.trim();
        int len = str.length();
        if (len == 0 || len % 2 == 1) {
            return null;
        }

        byte[] b = new byte[len / 2];
        try {
            for (int i = 0; i < str.length(); i += 2) {
                // b[i / 2] = (byte) Integer
                // .decodeBase64("0x" + str.substring(i, i + 2)).intValue();
                int byteValue = Integer.parseInt(str.substring(i, i + 2), 16);
                b[i / 2] = (byte) byteValue;
            }
            return b;
        } catch (Exception e) {
            return null;
        }
    }
}
