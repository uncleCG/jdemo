package com.demo.encrypt.util;

/**
 * 为了解决加解密出现乱码问题需要进行进制转换。
 *
 * @author zhangchangyong
 * @date 2020-01-14 17:08
 */
public class HexUtil {

    /**
     * 将字节数组换成十六进制字符串
     *
     * @param buf
     * @return {@link String}十六进制字符串
     * @throws
     * @author zhangchangyong
     * @date 2020/1/14 18:27
     */
    public static String byteArr2HexStr(byte[] buf) {
        StringBuffer sb = new StringBuffer();
        for (byte b : buf) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将十六进制字符串转换为字节数组
     *
     * @param hexStr 十六进制字符串
     * @return {@link byte[]}
     * @throws
     * @author zhangchangyong
     * @date 2020/1/14 18:28
     */
    public static byte[] hexStr2ByteArr(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

}
