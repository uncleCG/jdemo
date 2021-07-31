package com.demo.encrypt.util;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author zhangchangyong
 * @date 2020-01-14 18:35
 */
public class Md5Util {

    public static byte[] getMD5(String content) {
        if (content == null) {
            return null;
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            return md5.digest(content.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String content = "18612485086";
        byte[] md5Bytes = getMD5(content);
        System.out.println(HexUtil.byteArr2HexStr(md5Bytes));
    }

}
