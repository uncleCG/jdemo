package com.demo.encrypt;

import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class ImageToBase64 {

private static String strNetImageToBase64;

public static void main(String[] args) {
    //第一个:把网络图片装换成Base64
    String netImagePath = "http://10.152.17.88:8080/fastdfs-gateway/rmps/download?curSysNo=0909&key=1367349497873190912";
    //下面是网络图片转换Base64的方法
    NetImageToBase64(netImagePath);

    //下面是本地图片转换Base64的方法
    // String imagePath = "D:/资料/idcard_face.jpg";
    // ImageToBase64(imagePath);
}

/**
 * 网络图片转换Base64的方法
 *
 * @param netImagePath     
 */
private static void NetImageToBase64(String netImagePath) {
    final ByteArrayOutputStream data = new ByteArrayOutputStream();
    try {
        // 创建URL
        URL url = new URL(netImagePath);
        final byte[] by = new byte[1024];
        // 创建链接
        final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream is = conn.getInputStream();
                    // 将内容读取内存中
                    int len = -1;
                    while ((len = is.read(by)) != -1) {
                        data.write(by, 0, len);
                    }
                    // 对字节数组Base64编码
                    BASE64Encoder encoder = new BASE64Encoder();
                    strNetImageToBase64 = encoder.encode(data.toByteArray());
                    System.out.println("网络图片转换Base64:" + strNetImageToBase64);
                    // 关闭流
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    } catch (IOException e) {
        e.printStackTrace();
    }
}


/**
 * 本地图片转换Base64的方法
 *
 * @param imgPath     
 */

public static String ImageToBase64(String imgPath) {
    byte[] data = null;
    // 读取图片字节数组
    try {
        InputStream in = new FileInputStream(imgPath);
        data = new byte[in.available()];
        in.read(data);
        in.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
    // 对字节数组Base64编码
    BASE64Encoder encoder = new BASE64Encoder();
    // 返回Base64编码过的字节数组字符串
    return encoder.encode(Objects.requireNonNull(data));
}

}