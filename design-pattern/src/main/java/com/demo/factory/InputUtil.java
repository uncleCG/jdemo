package com.demo.factory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 命令行工具栏
 */
public class InputUtil {

    /**
     * 获取命令行输入的内容
     * @return
     */
    public static String getType() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String readLine = bufferedReader.readLine();
            System.out.println("输入的披萨类型：" + readLine);
            return readLine;
        } catch (IOException e) {
            return "";
        }
    }
}
