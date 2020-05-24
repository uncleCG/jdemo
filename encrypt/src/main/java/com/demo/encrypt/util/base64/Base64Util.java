package com.demo.encrypt.util.base64;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Base64工具类
 * 可以参考：org.springframework.util.Base64Utils
 * @author zhangchangyong
 * @date 2020-01-13 17:49
 */
public class Base64Util {

    /**
     * 文件读取缓冲区大小
     */
    private static final int CACHE_SIZE = 1024;

    /**
     * 解码
     * @param base64 待解码字符串
     * @return {@link byte[]} 解码后的字节数组
     * @throws
     * @author zhangchangyong
     * @date 2020/1/13 18:36
     */
    public static byte[] decode(String base64) {
        return Base64.getDecoder().decode(base64.getBytes(StandardCharsets.UTF_8));
    }

    public static String decode(byte[] bytes) {
        return new String(Base64.getDecoder().decode(bytes));
    }

    /**
     * @param source 待编码字符串
     * @return {@link String}
     * @throws
     * @author zhangchangyong
     * @date 2020/1/13 19:26
     */
    public static String encode(String source) {
        return Base64.getEncoder().encodeToString(source.getBytes());
    }

    public static String encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static void main(String[] args) {
        String source = "张昌永test1234567890张炎增张昌永test1234567890张炎增";
        String encodeStr = encode(source);
        String decodeStr = new String(decode(encodeStr));
        System.out.println("source：" + source);
        System.out.println("encodeStr：" + encodeStr);
        System.out.println("decodeStr：" + decodeStr);
    }

}
