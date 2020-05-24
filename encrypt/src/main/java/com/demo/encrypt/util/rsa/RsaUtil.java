package com.demo.encrypt.util.rsa;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Objects;
import java.util.Properties;
import java.util.UUID;

/**
 * RSA算法加密/解密工具类
 * 支持根据密钥长度自动分块进行加解密
 * 参考：https://blog.csdn.net/zoubaicai/article/details/78494369
 * @author zhangchangyong
 */
public class RsaUtil {

    /**
     * 用来指定保存密钥对的文件名和存储的名称
     */
    private static final String PUBLIC_KEY_NAME = "publicKey";
    private static final String PRIVATE_KEY_NAME = "privateKey";
    private static final String PUBLIC_FILENAME = "publicKey.properties";
    private static final String PRIVATE_FILENAME = "privateKey.properties";

    /**
     * 算法名称
     */
    private static final String ALGORITHM_RSA = "RSA";

    /**
     * 算法/模式/填充
     */
    private static final String CIPHER_TRANSFORMATION_RSA = "RSA/ECB/PKCS1Padding";

    /**
     * 默认密钥长度(bit)
     * 支持长度：512（已被破解）、1024（默认）、2048
     * 密钥长度越长，安全性越高，加解密越耗时
     */
    private static final int KEY_SIZE = 1024;

    /**
     * 密钥对生成器
     */
    private static KeyPairGenerator keyPairGenerator = null;

    /**
     * 密钥工厂
     */
    private static KeyFactory keyFactory = null;

    /**
     * Base64 编码/解码器 JDK1.8
     */
    private static Base64.Decoder decoder = Base64.getDecoder();
    private static Base64.Encoder encoder = Base64.getEncoder();

    // 初始化密钥对生成器及密钥工厂
    static {
        try {
            keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM_RSA);
            keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
        } catch (NoSuchAlgorithmException e) {
            // LOGGER.error(e.getMessage(),e);
            e.printStackTrace();
        }
    }

    /**
     * 私有构造器
     */
    // private RSAUtilsV2() {
    // }

    /**
     * 生成密钥对
     * 将密钥分别用Base64编码保存到#publicKey.properties#和#privateKey.properties#文件中
     * 保存的默认名称分别为publicKey和privateKey
     */
    public static synchronized void generateKeyPair() {
        try {
            // 初始化密钥对生成器
            // keyPairGenerator.initialize(KEY_SIZE);
            keyPairGenerator.initialize(KEY_SIZE, new SecureRandom(UUID.randomUUID().toString().replaceAll("-", "").getBytes()));

            // 生成密钥对
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
            String publicKeyString = encoder.encodeToString(rsaPublicKey.getEncoded());
            String privateKeyString = encoder.encodeToString(rsaPrivateKey.getEncoded());
            storeKey(publicKeyString, PUBLIC_KEY_NAME, PUBLIC_FILENAME);
            storeKey(privateKeyString, PRIVATE_KEY_NAME, PRIVATE_FILENAME);
        } catch (InvalidParameterException e) {
            // LOGGER.error("KeyPairGenerator does not support a key length of " + KEY_SIZE + ".",e);
            e.printStackTrace();
        } catch (NullPointerException e) {
            // LOGGER.error("RSAUtils#key_pair_gen is null,can not generate KeyPairGenerator instance.",e);
            e.printStackTrace();
        }
    }

    /**
     * 将指定的密钥字符串保存到文件中,如果找不到文件就创建
     *
     * @param keyString 密钥的Base64编码字符串（值）
     * @param keyName   保存在文件中的名称（键）
     * @param fileName  目标文件名
     */
    private static void storeKey(String keyString, String keyName, String fileName) {
        Properties properties = new Properties();
        //存放密钥的绝对地址
        String keyStorePath = null;
        OutputStream keyOutStream = null;
        URL resource = RsaUtil.class.getClassLoader().getResource(fileName);
        try {
            if (Objects.isNull(resource)) {
                // 首次不存在或者本移除后不存在
                String classPath = Objects.requireNonNull(RsaUtil.class.getClassLoader().getResource("")).toString();
                String prefix = classPath.substring(classPath.indexOf(":") + 1);
                File file = new File(prefix + fileName);
                final boolean createFlag = file.createNewFile();
                keyStorePath = file.getAbsolutePath();
            } else {
                keyStorePath = resource.toString();
                keyStorePath = keyStorePath.substring(keyStorePath.indexOf(":") + 1);
            }
            keyOutStream = new FileOutputStream(keyStorePath);
            properties.setProperty(keyName, keyString);
            properties.store(keyOutStream, "this is " + keyName);
        } catch (IOException e) {
            // LOGGER.error(fileName +" create fail.",e1);
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(keyOutStream)) {
                try {
                    keyOutStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取密钥字符串
     *
     * @param keyName  需要获取的密钥名
     * @param fileName 密钥所在文件
     * @return Base64编码的密钥字符串
     */
    private static String getKeyString(String keyName, String fileName) {
        if (RsaUtil.class.getClassLoader().getResource(fileName) == null) {
            // LOGGER.warn("getKeyString()# " + fileName + " is not exist.Will run #generateKeyPair()# firstly.");
            generateKeyPair();
        }
        try (InputStream in = RsaUtil.class.getClassLoader().getResource(fileName).openStream()) {
            Properties properties = new Properties();
            properties.load(in);
            return properties.getProperty(keyName);
        } catch (IOException e) {
            // LOGGER.error("getKeyString()#" + e.getMessage(),e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从文件获取RSA公钥
     *
     * @return RSA公钥
     * @throws
     */
    public static RSAPublicKey getPublicKey() {
        try {
            String publicKeyContent = getKeyString(PUBLIC_KEY_NAME, PUBLIC_FILENAME);
            if (publicKeyContent == null) {
                return null;
            }
            byte[] keyBytes = decoder.decode(publicKeyContent.getBytes(StandardCharsets.UTF_8));
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
            return (RSAPublicKey) keyFactory.generatePublic(x509EncodedKeySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从文件获取RSA私钥
     *
     * @return RSA私钥
     * @throws
     */
    public static RSAPrivateKey getPrivateKey() {
        try {
            String privateKeyContent = getKeyString(PRIVATE_KEY_NAME, PRIVATE_FILENAME);
            if (privateKeyContent == null) {
                // 获取私钥内容异常
                return null;
            }
            byte[] keyBytes = decoder.decode(privateKeyContent.getBytes(StandardCharsets.UTF_8));
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
            return (RSAPrivateKey) keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        } catch (InvalidKeySpecException e) {
            // LOGGER.error("getPrivateKey()#" + e.getMessage(),e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据公钥字符串获取公钥对象
     * @param publicKey 公钥内容
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
     * 根据私钥字符串获取私钥对象
     * @param privateKey 私钥
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
     * RSA公钥加密
     *
     * @param content   待加密的数据
     * @param publicKey RSA公钥
     * @return 加密后的密文(16进制的字符串)
     */
    public static String encryptByPublic(byte[] content, PublicKey publicKey) {
        if (publicKey == null) {
            publicKey = getPublicKey();
        }
        if (publicKey == null) {
            return null;
        }
        try {
            // Cipher cipher = Cipher.getInstance(ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION_RSA);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            //该密钥能够加密的最大字节长度
            int splitLength = ((RSAPublicKey) publicKey).getModulus().bitLength() / 8 - 11;
            byte[][] arrays = splitBytes(content, splitLength);
            StringBuilder strBuilder = new StringBuilder();
            for (byte[] array : arrays) {
                // strBuilder.append(HexUtil.byteArr2HexStr(cipher.doFinal(array)));
                strBuilder.append(bytesToHexString(cipher.doFinal(array)));
            }
            return strBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            // LOGGER.error("encrypt()#NoSuchAlgorithmException",e);
        } catch (NoSuchPaddingException e) {
            // LOGGER.error("encrypt()#NoSuchPaddingException",e);
        } catch (InvalidKeyException e) {
            // LOGGER.error("encrypt()#InvalidKeyException",e);
        } catch (BadPaddingException e) {
            // LOGGER.error("encrypt()#BadPaddingException",e);
        } catch (IllegalBlockSizeException e) {
            // LOGGER.error("encrypt()#IllegalBlockSizeException",e);
        }
        return null;
    }

    /**
     * RSA公钥加密
     *
     * @param data   待加密的数据
     * @param publicKey RSA 公钥 if null then getPublicKey()
     * @return 加密后的密文(16进制的字符串)
     */
    public static String encryptByPublic(String data, String publicKey) {
        try {
            RSAPublicKey rsaPublicKey = (RSAPublicKey) getPublicKey(publicKey);
            // Cipher cipher = Cipher.getInstance(ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION_RSA);
            cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
            // 该密钥能够加密的最大字节长度
            int splitLength = rsaPublicKey.getModulus().bitLength() / 8 - 11;
            byte[][] arrays = splitBytes(data.getBytes(StandardCharsets.UTF_8), splitLength);
            StringBuilder strBuilder = new StringBuilder();
            for (byte[] array : arrays) {
                // strBuilder.append(HexUtil.byteArr2HexStr(cipher.doFinal(array)));
                strBuilder.append(bytesToHexString(cipher.doFinal(array)));
            }
            return strBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            // LOGGER.error("encrypt()#NoSuchAlgorithmException",e);
        } catch (NoSuchPaddingException e) {
            // LOGGER.error("encrypt()#NoSuchPaddingException",e);
        } catch (InvalidKeyException e) {
            // LOGGER.error("encrypt()#InvalidKeyException",e);
        } catch (BadPaddingException e) {
            // LOGGER.error("encrypt()#BadPaddingException",e);
        } catch (IllegalBlockSizeException e) {
            // LOGGER.error("encrypt()#IllegalBlockSizeException",e);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * RSA私钥加密
     *
     * @param content    等待加密的数据
     * @param privateKey RSA 私钥 if null then getPrivateKey()
     * @return 加密后的密文(16进制的字符串)
     */
    public static String encryptByPrivate(byte[] content, PrivateKey privateKey) {
        if (privateKey == null) {
            privateKey = getPrivateKey();
        }
        if (Objects.isNull(privateKey)) {
            // 获取私钥异常
            return null;
        }
        try {
            // Cipher cipher = Cipher.getInstance(ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION_RSA);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            // 该密钥能够加密的最大字节长度（动态支持不同长度密钥，自动分块进行加解密）
            // 根据RSA加密规则，加密1byte字节的数据需要12byte，即其他11byte可能用于记录其他信息
            int splitLength = ((RSAPrivateKey) privateKey).getModulus().bitLength() / 8 - 11;
            // 将待加密字节数组按照私钥长度进行分组
            byte[][] arrays = splitBytes(content, splitLength);
            StringBuffer sb = new StringBuffer();
            for (byte[] array : arrays) {
                // 将每一组的字节数组进行加密并转化为16进制字符串后拼装
                // sb.append(HexUtil.byteArr2HexStr(cipher.doFinal(array)));
                sb.append(bytesToHexString(cipher.doFinal(array)));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // LOGGER.error("encrypt()#NoSuchAlgorithmException",e);
        } catch (NoSuchPaddingException e) {
            // LOGGER.error("encrypt()#NoSuchPaddingException",e);
        } catch (InvalidKeyException e) {
            // LOGGER.error("encrypt()#InvalidKeyException",e);
        } catch (BadPaddingException e) {
            // LOGGER.error("encrypt()#BadPaddingException",e);
        } catch (IllegalBlockSizeException e) {
            // LOGGER.error("encrypt()#IllegalBlockSizeException",e);
        }
        return null;
    }

    /**
     * RSA私钥加密
     *
     * @param content    待加密的数据
     * @param privateKey RSA私钥
     * @return 加密后的密文(16进制的字符串)
     */
    public static String encryptByPrivate(String content, String privateKey) {
        try {
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) getPrivateKey(privateKey);
            // Cipher cipher = Cipher.getInstance(ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION_RSA);
            cipher.init(Cipher.ENCRYPT_MODE, rsaPrivateKey);
            // 该密钥能够加密的最大字节长度（动态支持不同长度密钥，自动分块进行加解密）
            // 根据RSA加密规则，加密1byte字节的数据需要12byte，即其他11byte可能用于记录其他信息
            int splitLength = rsaPrivateKey.getModulus().bitLength() / 8 - 11;
            // 将待加密字节数组按照私钥长度进行分组
            byte[][] arrays = splitBytes(content.getBytes(StandardCharsets.UTF_8), splitLength);
            StringBuilder sb = new StringBuilder();
            for (byte[] array : arrays) {
                // 将每一组的字节数组进行加密并转化为16进制字符串后拼装
                // sb.append(HexUtil.byteArr2HexStr(cipher.doFinal(array)));
                sb.append(bytesToHexString(cipher.doFinal(array)));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * RSA私钥解密
     *
     * @param content    等待解密的数据
     * @param privateKey RSA 私钥 if null then getPrivateKey()
     * @return 解密后的明文
     */
    public static String decryptByPrivate(String content, PrivateKey privateKey) {
        if (privateKey == null) {
            privateKey = getPrivateKey();
        }
        if (privateKey == null) {
            return null;
        }
        try {
            // Cipher cipher = Cipher.getInstance(ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION_RSA);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            //该密钥能够加密的最大字节长度
            int splitLength = ((RSAPrivateKey) privateKey).getModulus().bitLength() / 8;
            // byte[] contentBytes = HexUtil.hexStr2ByteArr(content);
            byte[] contentBytes = hexStringToBytes(content);
            byte[][] arrays = splitBytes(contentBytes, splitLength);
            StringBuffer stringBuffer = new StringBuffer();
            String sTemp = null;
            for (byte[] array : arrays) {
                stringBuffer.append(new String(cipher.doFinal(array)));
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            // LOGGER.error("encrypt()#NoSuchAlgorithmException",e);
        } catch (NoSuchPaddingException e) {
            // LOGGER.error("encrypt()#NoSuchPaddingException",e);
        } catch (InvalidKeyException e) {
            // LOGGER.error("encrypt()#InvalidKeyException",e);
        } catch (BadPaddingException e) {
            // LOGGER.error("encrypt()#BadPaddingException",e);
        } catch (IllegalBlockSizeException e) {
            // LOGGER.error("encrypt()#IllegalBlockSizeException",e);
        }
        return null;
    }

    /**
     * RSA私钥解密
     *
     * @param content    等待解密的数据
     * @param privateKey 字符串格式RSA私钥
     * @return 解密后的明文
     */
    public static String decryptByPrivate(String content, String privateKey) {
        try {
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) getPrivateKey(privateKey);
            // Cipher cipher = Cipher.getInstance(ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION_RSA);
            cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
            // 该密钥能够加密的最大字节长度
            int splitLength = rsaPrivateKey.getModulus().bitLength() / 8;
            // byte[] contentBytes = HexUtil.hexStr2ByteArr(content);
            byte[] contentBytes = hexStringToBytes(content);
            byte[][] arrays = splitBytes(contentBytes, splitLength);
            StringBuilder stringBuilder = new StringBuilder();
            for (byte[] array : arrays) {
                stringBuilder.append(new String(cipher.doFinal(array)));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            // LOGGER.error("encrypt()#NoSuchAlgorithmException",e);
        } catch (NoSuchPaddingException e) {
            // LOGGER.error("encrypt()#NoSuchPaddingException",e);
        } catch (InvalidKeyException e) {
            // LOGGER.error("encrypt()#InvalidKeyException",e);
        } catch (BadPaddingException e) {
            // LOGGER.error("encrypt()#BadPaddingException",e);
        } catch (IllegalBlockSizeException e) {
            // LOGGER.error("encrypt()#IllegalBlockSizeException",e);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * RSA公钥解密
     *
     * @param content   等待解密的数据
     * @param publicKey RSA 公钥 if null then getPublicKey()
     * @return 解密后的明文
     */
    public static String decryptByPublic(String content, PublicKey publicKey) {
        if (publicKey == null) {
            publicKey = getPublicKey();
        }
        if (Objects.isNull(publicKey)) {
            // 获取公钥异常
            return null;
        }
        try {
            // Cipher cipher = Cipher.getInstance(ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION_RSA);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            // 该密钥能够加密的最大字节长度（动态支持不同长度密钥，自动分块进行加解密）
            // 根据RSA加密规则，加密1byte字节的数据需要12byte，即其他11byte可能用于记录其他信息
            int splitLength = ((RSAPublicKey) publicKey).getModulus().bitLength() / 8;
            // 16进制字符串转化为字节数组
            // byte[] contentBytes = HexUtil.hexStr2ByteArr(content);
            byte[] contentBytes = hexStringToBytes(content);
            byte[][] arrays = splitBytes(contentBytes, splitLength);
            StringBuffer stringBuffer = new StringBuffer();
            String sTemp = null;
            for (byte[] array : arrays) {
                stringBuffer.append(new String(cipher.doFinal(array)));
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            // LOGGER.error("encrypt()#NoSuchAlgorithmException",e);
        } catch (NoSuchPaddingException e) {
            // LOGGER.error("encrypt()#NoSuchPaddingException",e);
        } catch (InvalidKeyException e) {
            // LOGGER.error("encrypt()#InvalidKeyException",e);
        } catch (BadPaddingException e) {
            // LOGGER.error("encrypt()#BadPaddingException",e);
        } catch (IllegalBlockSizeException e) {
            // LOGGER.error("encrypt()#IllegalBlockSizeException",e);
        }
        return null;
    }

    /**
     * RSA公钥解密
     *
     * @param content   等待解密的数据
     * @param publicKey RSA公钥
     * @return 解密后的明文
     */
    public static String decryptByPublic(String content, String publicKey) {
        try {
            RSAPublicKey rsaPublicKey = (RSAPublicKey) getPublicKey(publicKey);
            // Cipher cipher = Cipher.getInstance(ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION_RSA);
            cipher.init(Cipher.DECRYPT_MODE, rsaPublicKey);
            // 该密钥能够加密的最大字节长度（动态支持不同长度密钥，自动分块进行加解密）
            // 根据RSA加密规则，加密1byte字节的数据需要12byte，即其他11byte可能用于记录其他信息
            int splitLength = rsaPublicKey.getModulus().bitLength() / 8;
            // 16进制字符串转化为字节数组
            // byte[] contentBytes = HexUtil.hexStr2ByteArr(content);
            byte[] contentBytes = hexStringToBytes(content);
            byte[][] arrays = splitBytes(contentBytes, splitLength);
            StringBuilder stringBuilder = new StringBuilder();
            for (byte[] array : arrays) {
                stringBuilder.append(new String(cipher.doFinal(array)));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            // LOGGER.error("encrypt()#NoSuchAlgorithmException",e);
        } catch (NoSuchPaddingException e) {
            // LOGGER.error("encrypt()#NoSuchPaddingException",e);
        } catch (InvalidKeyException e) {
            // LOGGER.error("encrypt()#InvalidKeyException",e);
        } catch (BadPaddingException e) {
            // LOGGER.error("encrypt()#BadPaddingException",e);
        } catch (IllegalBlockSizeException e) {
            // LOGGER.error("encrypt()#IllegalBlockSizeException",e);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据限定的每组字节长度，将字节数组分组
     *
     * @param bytes       等待分组的字节组
     * @param splitLength 每组长度
     * @return 分组后的字节组
     */
    public static byte[][] splitBytes(byte[] bytes, int splitLength) {
        // bytes与splitLength的余数
        int remainder = bytes.length % splitLength;
        // 数据拆分后的组数，余数不为0时加1
        int quotient = remainder != 0 ? bytes.length / splitLength + 1 : bytes.length / splitLength;
        byte[][] arrays = new byte[quotient][];
        byte[] array = null;
        for (int i = 0; i < quotient; i++) {
            // 如果是最后一组（quotient-1）,同时余数不等于0，就将最后一组设置为remainder的长度
            if (i == quotient - 1 && remainder != 0) {
                array = new byte[remainder];
                System.arraycopy(bytes, i * splitLength, array, 0, remainder);
            } else {
                array = new byte[splitLength];
                System.arraycopy(bytes, i * splitLength, array, 0, splitLength);
            }
            arrays[i] = array;
        }
        return arrays;
    }

    /**
     * 将字节数组转换成16进制字符串
     *
     * @param bytes 即将转换的数据
     * @return 16进制字符串
     */
    public static String bytesToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length);
        String temp = null;
        for (byte aByte : bytes) {
            temp = Integer.toHexString(0xFF & aByte);
            if (temp.length() < 2) {
                sb.append(0);
            }
            sb.append(temp);
        }
        return sb.toString();
    }

    /**
     * 将16进制字符串转换成字节数组
     *
     * @param hex 16进制字符串
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hex) {
        int len = (hex.length() / 2);
        hex = hex.toUpperCase();
        byte[] result = new byte[len];
        char[] chars = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(chars[pos]) << 4 | toByte(chars[pos + 1]));
        }
        return result;
    }

    /**
     * 将char转换为byte
     *
     * @param c char
     * @return byte
     */
    private static byte toByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static void main(String[] args) {
        RSAPublicKey rsaPublicKey = getPublicKey();
        RSAPrivateKey rsaPrivateKey = getPrivateKey();
        String publicKey = Base64.getEncoder().encodeToString(rsaPublicKey.getEncoded());
        String privateKey = Base64.getEncoder().encodeToString(rsaPrivateKey.getEncoded());
        String content = "张昌永test";
        String c1 = RsaUtil.encryptByPublic(content, publicKey);
        String m1 = RsaUtil.decryptByPrivate(c1, privateKey);
        System.out.println(m1);
        System.out.println(c1);
        String c2 = RsaUtil.encryptByPrivate(content, privateKey);
        String m2 = RsaUtil.decryptByPublic(c2, publicKey);
        System.out.println(c2);
        System.out.println(m2);
        String c11 = RsaUtil.encryptByPublic(content.getBytes(StandardCharsets.UTF_8), rsaPublicKey);
        String m11 = RsaUtil.decryptByPrivate(c11, rsaPrivateKey);
        System.out.println(c11);
        System.out.println(m11);
        String c22 = RsaUtil.encryptByPrivate(content.getBytes(StandardCharsets.UTF_8), rsaPrivateKey);
        String m22 = RsaUtil.decryptByPublic(c22, rsaPublicKey);
        System.out.println(c22);
        System.out.println(m22);
    }

}