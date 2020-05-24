package com.demo.encrypt.util.aes;


import com.demo.encrypt.util.base64.Base64Util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.UUID;

/**
 * AES工具类，密钥必须是16位字符串
 *
 * @author zhangchangyong
 * @date 2020-01-13 17:59
 */
public class AesUtil {

    private static final String CHARSET_UTF8 = StandardCharsets.UTF_8.name();
    private static final String AES_ALGORITHM = "AES";

    /**
     * 不带模式和填充获取 AES 算法，默认使用 AES/ECB/PKCS5Padding
     * 除 ECB 模式以外的其它模式都需要设置偏移量，用以增加加密算法的强度。
     */
    private static final String CIPHER_ALGORITHM_CBC = "AES/CBC/PKCS5Padding";
    private static final String CIPHER_ALGORITHM_ECB = "AES/ECB/PKCS5Padding";

    /**
     * 偏移量，必须是16位字符串
     * 除 ECB 模式以外的其它模式都需要设置偏移量，用以增加加密算法的强度。
     * 偏移量 iv 长度必须为密钥位数一致，否则给出错误提示
     * java.security.InvalidAlgorithmParameterException: Wrong IV length: must be 16 bytes long
     */
//    private static final String OFFSET_STRING = "16-Bytes--String";
    private static final String OFFSET_STRING = "ivLenMust16Bytes";

    /**
     * 默认的密钥
     */
    private static final String DEFAULT_KEY = "gomeHandy2019";

    /**
     * @param
     * @return {@link String}
     * @throws
     * @author zhangchangyong
     * @date 2020/1/14 16:08
     */
    public static String generateKey() {
        // AES 的区块长度固定为 128 比特，每个加密块大小为 128 位。密钥长度则可以是 128，192 或 256 比特。
        // 此处substring()的长度 * 8 对应AES密钥的位数：128、192、256
        return UUID.randomUUID().toString().replace("-", "").substring(0, 32);
    }

    /**
     * AES加密
     *
     * @param key     加密密钥
     * @param content 待加密内容
     * @return {@link String} 加密后内容
     * @throws Exception 加密异常
     * @author zhangchangyong
     * @date 2020/1/14 18:21
     */
    public static String encryptWithCbc(String key, String content) throws Exception {
        try {
            if (Objects.isNull(content)) {
                return null;
            }
            byte[] keyBytes = key.getBytes(CHARSET_UTF8);
            // 偏移量
            IvParameterSpec ivParameterSpec = new IvParameterSpec(key.substring(0, 16).getBytes(CHARSET_UTF8));
            // 根据指定的key生成AES密钥
            // 注意，为了能与 iOS 统一，这里不可以使用 KeyGenerator、SecureRandom、SecretKey 进行生成
            // KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, AES_ALGORITHM);
            // 创建密码器并指定加密算法/工作模式/填充方式
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] contentBytes = content.getBytes(CHARSET_UTF8);
            byte[] encryptedBytes = cipher.doFinal(contentBytes);
            // 可以将加密后的字节数组转换为Base64或16进制字符串，以免加解密乱码
            return Base64Util.encode(encryptedBytes);
        } catch (NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * AES解密
     *
     * @param key     解密密钥
     * @param content 待解密内容
     * @return {@link String} 解密后内容
     * @throws Exception 解密异常
     * @author zhangchangyong
     * @date 2020/1/14 18:18
     */
    public static String decryptWithCbc(String key, String content) throws Exception {
        try {
            byte[] encryptedBytes = Base64Util.decode(content);
            byte[] keyBytes = key.getBytes(CHARSET_UTF8);
            // 生成 AES 专用密钥
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, AES_ALGORITHM);
            // byte[] offsetBytes = OFFSET_STRING.getBytes();
            IvParameterSpec ivParameterSpec = new IvParameterSpec(key.substring(0, 16).getBytes(CHARSET_UTF8));
            // 创建密码器并指定加密算法/工作模式/填充方式
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
            // 初始化密码器模式
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * AES加密
     *
     * @param key     加密密钥
     * @param content 待加密内容
     * @return {@link String} 加密后内容
     * @throws Exception 加密异常
     * @author zhangchangyong
     * @date 2020/1/14 18:21
     */
    public static String encrypt(String key, String content) throws Exception {
        try {
            byte[] keyBytes = key.getBytes();
            // 根据指定的key生成AES密钥
            // 注意，为了能与 iOS 统一，这里不可以使用 KeyGenerator、SecureRandom、SecretKey 进行生成
            // KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, AES_ALGORITHM);
            // 创建密码器并指定加密算法/工作模式/填充方式
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] contentBytes = content.getBytes(CHARSET_UTF8);
            byte[] encryptedBytes = cipher.doFinal(contentBytes);
            return Base64Util.encode(encryptedBytes);
        } catch (NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException | InvalidKeyException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * AES解密
     *
     * @param key     解密密钥
     * @param content 待解密内容
     * @return {@link String} 解密后内容
     * @throws Exception 解密异常
     * @author zhangchangyong
     * @date 2020/1/14 18:18
     */
    public static String decrypt(String key, String content) throws Exception {
        try {
            byte[] keyBytes = key.getBytes();
            // 生成 AES 专用密钥
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, AES_ALGORITHM);
            // 创建密码器并指定加密算法/工作模式/填充方式
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB);
            // 初始化密码器模式
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] encryptedBytes = Base64Util.decode(content);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String[] args) throws Exception {
        // 通过密钥长度控制密钥长度 = key.length() * 8
        String randomKey = generateKey();
        System.out.println("randomKey：" + randomKey);
        String content = "我是待加密信息222222";
        System.out.println("aes加密前: " + content);
        String encryptData = AesUtil.encryptWithCbc(randomKey, content);
        System.out.println("aes加密后: " + encryptData);
        String decryptData = AesUtil.decryptWithCbc(randomKey, encryptData);
        System.out.println("aes解密后：" + decryptData);
        String encrypt = AesUtil.encrypt(randomKey, content);
        System.out.println("aes加密后ebc: " + encrypt);
        String decrypt = AesUtil.decrypt(randomKey, encrypt);
        System.out.println("aes解密后ebc：" + decrypt);
    }
}
