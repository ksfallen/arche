package com.yhml.core.crypt;

import java.io.*;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import static com.yhml.core.crypt.EncodeUtil.*;

/**
 * 默认使用 base64 编码
 *
 * RSA加密工具类
 */
@Slf4j
@NoArgsConstructor
public class RSAUtil {
    private static final String RSA_ALGORITHM = "RSA";

    /**
     * 貌似默认是RSA/NONE/PKCS1Padding
     */
    private static final int PUBLIC_MODE = 1;
    private static final int PRIVATE_MODE = 2;

    /**
     * RSA密钥长度必须是64的倍数，在512~65536之间。默认是1024
     */
    @Setter
    private int keySize = 1024;

    private PublicKey publicKey;
    private PrivateKey privateKey;

    public RSAUtil(int keySize) {
        this.keySize = keySize;
    }

    public static void main(String[] args) {
        RSAUtil rsa = new RSAUtil();
        rsa.generateKey();

        // System.out.println(rsa.getRSAPublicKey());
        System.out.println("====================================");
        // System.out.println(rsa.getPrivateKey());

        String pk = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlLQRXT9wYRnmtScCqAW5tanweW1eAYwUWWg" +
                "/FbuhTJoKsD6CowNBYoBiRUUsvoHBXNAOtDABY7CSkRChS/j6PQS" +
                "/Hf75Zp5MO1ahYnZDBUSyDR0IoewYDbfcMNLvbJeQiEJneXhYsUAGtST1rV3nygJLEBi45IpsrTUKlud9pvKJzJEGXm2k7Oqv7PD7H+rVnhrdSnP" +
                "/SseoBpx0aFhz2Yl4dPwHmBKyTN5CD/5OHChhQWYJsYUQ1DsjnhYFN0fiA7Ovgzlf+JdhsmSMJjAlIll1hiB51AU/1q1" +
                "/b1zW8vIA53Dd3isVm75CMx43Vt7FY3bo+GTw2JcwA9cGAdEdeQIDAQAB";
        String privateKey =
                "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCUtBFdP3BhGea1JwKoBbm1qfB5bV4BjBRZaD8Vu6FMmgqwPoKjA0FigGJFRSy" +
                        "+gcFc0A60MAFjsJKREKFL+Po9BL8d/vlmnkw7VqFidkMFRLINHQih7BgNt9ww0u9sl5CIQmd5eFixQAa1JPWtXefKAksQGLjkimytNQqW532m8onMkQZebaTs6q/s8Psf6tWeGt1Kc/9Kx6gGnHRoWHPZiXh0/AeYErJM3kIP/k4cKGFBZgmxhRDUOyOeFgU3R+IDs6+DOV/4l2GyZIwmMCUiWXWGIHnUBT/WrX9vXNby8gDncN3eKxWbvkIzHjdW3sVjduj4ZPDYlzAD1wYB0R15AgMBAAECggEAQPKk4uViYAbADT8MmfZmNkITFfX+qQ5VlPdDFmrs+FgPcwraY/h4Bl2sjjS4ZjymB7OXuHt/H9tvKIzq5hPIt/3qu18x7vAUu5YKxsaAXzyxx6h2rMJBfzxen7SBfhx0tA7MwvEdS817IIMUCrSJGGIIBu/FUYxkCwKvrVlOOCvsIcu7kr0tKV7KgL+fWb2y/68LORLykUMuFhyTcwAqorkCV7MPdvDtdcqtqArYNZn/lWUWaeKz5BqhG3nP0m2V1duVafRNpBmtFqQhwBsXCED3FR6QHNdyGYC4H7a0AYxXHd84HsYUTBugjw0i6XfvaHWUFXRIc/DY2SPxUOfNQQKBgQDI2znprBU1eHgXGLtFXwYcDjUf7BZtzL69RItQUsBbs2nDXZjQcNDZ+JjfqSj9XaRCjIQrfXiyWv+5x8m4E5MuvtyeCO2GqTCEo3mUDusvgR55sRArovq6YftPDmmSUD+V80H5GC9kxivR6BVlDjAO+mGrmKQG1uowQrT5OKO86wKBgQC9h2Ddv26dwni3K2iCJ6nMVMe8JredeYwkfG01sGR1IszPIO09XDMRFBdGwntyzBctTtAEtJxYHc54YNxxFyL0NDprb57G6ysuejq99KsRExBYymyOVwmHc2IKT6Vj5mDrE8A57xFp+1mouIYoFTa31oHhjOW1aqAmKHo/QXCmKwKBgAqInL/pe29DcyDa9i9MLXjZMeYLrp1xiGtKpfe/b0Ef5qMNTI9Z60oTJIlOSM0I1S18Sw4w1VydMx4eITEbLbPc5JsRIsvWIapDHIQsSB9EqUF+jLeNI5MUwmZB/j1jIgKOMF6M6ydg0Tl/72dOWCzg6rBiH/AP41ZGmVEcrFGjAoGAUttbarYk+s0pDxLoFnaWkeDCjSvz++FGdjD7YYxi7p6vISJI9RlYre+1mVaut2on+8PHxzbaXt9xA0l9NeeifVZT7+IVbOskrqX7Bk5vdwB5lgew262LWe8EfnOBX6I43qx8zhcI6udatBsvc1iK2mXp1BxP1DbwGp55iyvlvo8CgYEAoNj+YVQaRure36lhPI53Pm0HtJGPL9CAu944udW3kq1g3eL+FvuhMSuiCf94MrZjybYWGwflz0FcPzxrS6nmMNpVHsiG3lxRufPhKApKRpGGiI94MMkO0E94MxYSTnlDwjYSrRr3Fqe1YoFdQm5ZxXLZwl+NRMlu3RUNMzSM90A=";

        String hello = RSAUtil.encryptWithPublicKey("HELLO", pk);
        System.out.println(hello);
        System.out.println("====================================");
        System.out.println(RSAUtil.decryptWithPrivateKey(hello, privateKey));
    }

    /**
     * 生成公钥和私钥
     */
    public void generateKey() {
        try {
            log.debug("Generating a pair of RSA key ... ");

            //RSA加密算法：创建密钥对8
            KeyPairGenerator rsaKeyGen = KeyPairGenerator.getInstance(RSA_ALGORITHM);
            SecureRandom random = new SecureRandom();
            random.setSeed(System.currentTimeMillis());
            rsaKeyGen.initialize(keySize, random);

            //分别得到公钥和私钥
            KeyPair keypair = rsaKeyGen.generateKeyPair();
            publicKey = keypair.getPublic();
            privateKey = keypair.getPrivate();

            if (log.isDebugEnabled()) {
                log.debug("pubKey:" + getRSAPublicKey());
                log.debug("priKey:" + getPrivateKey());
            }
            log.debug("1024-bit RSA key GENERATED.");

        } catch (NoSuchAlgorithmException ignored) {
        }
    }

    public String getRSAPublicKey() {
        return encodeBase64(publicKey.getEncoded());
    }

    public String getPrivateKey() {
        return encodeBase64(privateKey.getEncoded());
    }


    /**
     * Base64 编码的 数据RSA加密
     *
     * @param origin    明文
     * @param publicKey base64编码的公钥
     * @return 密文
     */
    public static String encryptWithPublicKey(String origin, String publicKey) {
        try {
            byte[] keyByte = decodeBase64(publicKey.trim());
            byte[] bytes = encrypt(origin.getBytes(), getRSAPublicKey(keyByte));
            return encodeBase64(bytes);
        } catch (Exception e) {
            log.error("RSA 加密失败", e);
        }

        return "";
    }

    /**
     * 数据RSA解密
     *
     * @param enc        密文
     * @param privateKey 私钥
     * @return 明文
     */
    public static String decryptWithPrivateKey(String enc, String privateKey) {
        try {
            byte[] keyByte = decodeBase64(privateKey.trim());
            return decrypt(decodeBase64(enc), getPrivateKey(keyByte));
        } catch (Exception e) {
            log.error("RSA 解密失败", e);
        }

        return "";
    }


    /**
     * hex 编码
     *
     * @param origin
     * @param publicKey hex编码
     * @return
     */
    // public static String encryptWithPublicKeyWithHex(String origin, String publicKey) {
    //     try {
    //         byte[] keyByte = decodeHex(publicKey);
    //         byte[] bytes = encrypt(origin.getBytes(), getRSAPublicKey(keyByte));
    //         return encodeHex(bytes);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    //     return null;
    // }

    /**
     * hex 编码
     *
     * @param enc
     * @param privateKey
     * @return
     */
    // public static String decryptWithPrivateKeyWithHex(String enc, String privateKey) {
    //     try {
    //         byte[] pribyte = decodeHex(privateKey.trim());
    //         return decrypt(decodeHex(enc), getPrivateKey(pribyte));
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    //     return null;
    // }

    public static String encryptLongText(String src, String publicKey) {
        final int ENCRYPT_LENGTH = 117;
        if (src.length() <= ENCRYPT_LENGTH) {
            return encryptWithPublicKey(src, publicKey);
        }

        StringBuilder sb = new StringBuilder();
        int idx = 0;
        while (idx < src.length()) {
            int end = idx + ENCRYPT_LENGTH > src.length() ? src.length() : idx + ENCRYPT_LENGTH;
            String sub = src.substring(idx, end);
            String encSub = encryptWithPublicKey(sub, publicKey);
            sb.append(encSub);
            idx += ENCRYPT_LENGTH;
        }

        return sb.toString();
    }


    /**
     * 数据RSA加密
     *
     * @param origin    明文
     * @param publicKey 公钥
     * @return 密文
     */
    private static byte[] encrypt(byte[] origin, Key publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(origin);
    }

    /**
     * 数据RSA解密
     *
     * @param enc        密文
     * @param privateKey 私钥
     * @return 明文
     */
    private static String decrypt(byte[] enc, Key privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] bytes = cipher.doFinal(enc);
        return new String(bytes);
    }

    /**
     * 将公钥保存至文件
     *
     * @param file 待写入的文件
     * @return true 写入成功;false 写入失败
     */
    public boolean savePublicKey(File file, RSAPublicKey publicKey) {
        return saveKey(publicKey, file);
    }

    /**
     * 将私钥保持至文件
     *
     * @param file 待写入的文件
     * @return true 写入成功;false 写入失败
     */
    public boolean savePrivateKey(File file, RSAPrivateKey privateKey) {
        return saveKey(privateKey, file);
    }

    //将Key文件保持到文件中
    private boolean saveKey(Key key, File file) {
        boolean result = false;

        try (FileOutputStream fos = new FileOutputStream(file); ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            //公钥默认使用的是X.509编码，私钥默认采用的是PKCS #8编码
            byte[] encode = key.getEncoded();

            //注意，此处采用writeObject方法，读取时也要采用readObject方法
            oos.writeObject(encode);
            result = true;
        } catch (IOException e) {
            log.error("save key to file{} desc", file, e);
        }

        return result;
    }

    /**
     * 由文件获取密钥
     */
    public Key getPublic(File file) {
        try (FileInputStream fis = new FileInputStream(file); ObjectInputStream ois = new ObjectInputStream(fis)) {
            byte[] keybyte = (byte[]) ois.readObject();
            return getRSAPublicKey(keybyte);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 由文件获取密钥
     */
    public Key getPrivate(File file) {
        try (FileInputStream fis = new FileInputStream(file); ObjectInputStream ois = new ObjectInputStream(fis)) {
            byte[] keybyte = (byte[]) ois.readObject();
            return getPrivateKey(keybyte);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 签名
     *
     * @param src
     * @param privateKey
     * @return
     */
    public String generateSHA1withRSASigature(String src, String privateKey) {
        try {
            byte[] pribyte = decodeHex(privateKey.trim());
            PrivateKey key = getPrivateKey(pribyte);
            Signature instance = Signature.getInstance("SHA1withRSA");
            instance.initSign(key);
            instance.update(src.getBytes());
            byte[] signature = instance.sign();
            return encodeHex(signature);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    /**
     * 校验签名
     *
     * @param sign
     * @param src
     * @param publicKey
     * @return
     */
    public static boolean verifySHA1withRSASigature(String sign, String src, String publicKey) {
        try {
            byte[] bytes = decodeHex(publicKey.trim());

            RSAPublicKey pubKey = getRSAPublicKey(bytes);
            Signature sigEng = Signature.getInstance("SHA1withRSA");
            sigEng.initVerify(pubKey);
            sigEng.update(src.getBytes());

            byte[] sign1 = decodeHex(sign);
            return sigEng.verify(sign1);

        } catch (Exception e) {
            log.error("", e);
        }

        return false;
    }

    private static RSAPrivateKey getPrivateKey(byte[] encodedKey) {
        return (RSAPrivateKey) restoreKey(encodedKey, PRIVATE_MODE);
    }


    private static RSAPublicKey getRSAPublicKey(byte[] encodedKey) {
        return (RSAPublicKey) restoreKey(encodedKey, PUBLIC_MODE);
    }

    /**
     * 通过密钥 还原 key
     *
     * @param mode 0：公钥，1：私钥
     */
    private static Key restoreKey(byte[] keyByte, int mode) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);

            if (mode == PUBLIC_MODE) {
                X509EncodedKeySpec x509ek = new X509EncodedKeySpec(keyByte);
                return keyFactory.generatePublic(x509ek);
            } else if (mode == PRIVATE_MODE) {
                PKCS8EncodedKeySpec pkcs8eks = new PKCS8EncodedKeySpec(keyByte);
                return keyFactory.generatePrivate(pkcs8eks);
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return null;
    }

}
