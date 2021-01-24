package com.demo;

import java.util.HashMap;

/**
 * @author zhangchangyong
 * @date 2020-10-10 13:59
 */
public class MapTest {

    public static void main(String[] args) {
        HashMap<String, String> hashMap = new HashMap<>();
        String putVal1 = hashMap.put("key1", "value1");
        String putOldVal1 = hashMap.put("key1", "newValue1");
        String getVal1 = hashMap.get("key1");
    }
}
