package com.demo.encrypt.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author zhangchangyong
 * @date 2020-01-21 10:32
 */
public class ShaUtil {

    public static void main(String[] args) {
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            String content = "这是明文内容";
            byte[] digest = sha256.digest(content.getBytes());
            System.out.println(HexUtil.byteArr2HexStr(digest));
            sha256.update(content.getBytes());
            System.out.println(HexUtil.byteArr2HexStr(sha256.digest()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    
}
