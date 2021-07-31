package com.demo.encrypt.util;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class SignatureUtil {

    // private static final String MD5_WITH_RSA = "MD5WithRSA";
    // private static final String MD5_WITH_RSA = "SHA1WithRSA";
    private static final String MD5_WITH_RSA = "SHA256WithRSA";
    private static final String ALGORITHM_RSA = "RSA";
    private static final int KEY_SIZE = 2048;

    /**
     * 得到产生的私钥/公钥对
     *
     * @return KeyPair
     */
    public static KeyPair getKeypair() {
        //产生RSA密钥对(myKeyPair)
        KeyPairGenerator myKeyGen = null;
        try {
            myKeyGen = KeyPairGenerator.getInstance(ALGORITHM_RSA);
            myKeyGen.initialize(KEY_SIZE);
            return myKeyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("生成密钥异常", e);
        }
    }

    /**
     * 根据私钥和信息生成签名
     *
     * @param privateKeyStr
     * @param data
     * @return 签名的Base64编码
     */
    public static String sign(String privateKeyStr, String data) {
        try {
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) getPrivateKey(privateKeyStr);
            Signature sign = Signature.getInstance(MD5_WITH_RSA);
            sign.initSign(rsaPrivateKey);
            sign.update(data.getBytes(StandardCharsets.UTF_8));
            byte[] signBytes = sign.sign();
            return Base64.getEncoder().encodeToString(signBytes).replaceAll("\r\n", "");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("签名异常");
    }

    /**
     * 验证签名
     *
     * @param publicKey 公钥的Base64编码
     * @param sign      签名的Base64编码
     * @param data      生成签名的原数据
     * @return
     */
    public static boolean verify(String publicKey, String sign, String data) {
        try {
            RSAPublicKey rsaPublicKey = (RSAPublicKey) getPublicKey(publicKey);
            Signature signature = Signature.getInstance(MD5_WITH_RSA);
            signature.initVerify(rsaPublicKey);
            signature.update(data.getBytes(StandardCharsets.UTF_8));
            return signature.verify(Base64.getDecoder().decode(sign));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("验证签名异常");
    }

    /**
     * 根据私钥字符串获取私钥对象
     * @param privateKey 字符串格式的私钥
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
        return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
    }

    /**
     * 根据公钥字符串获取公钥对象
     * @param publicKey 字符串格式的公钥
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = Base64.getDecoder().decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 将对象转换为map
     * @param bean
     * @param <T>
     * @return
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        try {
            Field[] fields = bean.getClass().getDeclaredFields();
            Map<String, Object> paramMap = new HashMap<>((int) (fields.length / 0.75));
            Field.setAccessible(fields, true);
            for (Field field : fields) {
                if (null != field.get(bean)) {
                    paramMap.put(field.getName(), field.get(bean));
                }
            }
            return paramMap;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String data = "{\"key\":\"给我签名吧！\"}";
        /*(1)生成公钥和私钥对*/
        KeyPair keyPair = getKeypair();
        String publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        String privateKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
        System.out.println("公钥：" + publicKey);
        System.out.println("私钥：" + privateKey);
        /*(2)用私钥生成签名*/
        String signStr = sign(privateKey, data);
        System.out.println("签名是：" + signStr);
        /*(3)验证签名*/
        System.out.println("验证签名的结果是：" + verify(publicKey, signStr, data));
    }
} 