package com.demo.encrypt.util.aes;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Created by WANGMAO872 on  2019/5/9.
 */
public class PingAnAesUtil {
    private static final String[] HEXDIGITS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
            "e", "f" };

    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * AES加密操作
     *
     * @param content 待加密内容
     * @param key     秘钥
     * @return 返回Base64转码后的加密数据
     */
    public static String encrypt(String content, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
        byte[] byteContent = content.getBytes(StandardCharsets.UTF_8);
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(key));
        byte[] result = cipher.doFinal(byteContent);
        return Base64.getEncoder().encodeToString(result);
    }

    /**
     * AES解密操作
     *
     * @param content
     * @param key
     * @return
     */
    public static String decrypt(String content, String key) throws Exception {
        //实例化
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
        //使用密钥初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(key));
        //执行操作
        byte[] result = cipher.doFinal(Base64.getDecoder().decode(content));
        return new String(result, StandardCharsets.UTF_8);
    }

    /**
     * 生成加密秘钥
     *
     * @return
     */
    private static SecretKeySpec getSecretKey(final String key) throws Exception {
        // 返回生成指定算法密钥生成器的 KeyGenerator 对象
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        // 通过这种方式不会限制key的长度，通过init时指定秘钥长度，否则key只能是16、24、32个字符
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(key.getBytes());
        // 密钥长度为 128
        kg.init(128, secureRandom);
        // 生成一个密钥
        SecretKey secretKey = kg.generateKey();
        return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);

    }
    public static void example() throws Exception {
        String data = "{\"TES\":\"1343432432\"}";
        System.out.println("待加密数据：" + data);
        // 加密密钥
        String key = "0123456789abcdef0123456789abcdef\\/";
        // 加密数据
        String encryptData = PingAnAesUtil.encrypt(data, key);
        System.out.println("加密后数据 ：" + encryptData);
        // 解密数据
        String decryptData = PingAnAesUtil.decrypt(encryptData, key);
        System.out.println("解密后数据 ：" + decryptData);
    }

    public static void main(String[] args) throws Exception {
        example();
    }

}
