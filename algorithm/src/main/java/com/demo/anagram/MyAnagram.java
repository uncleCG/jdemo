package com.demo.anagram;

import java.util.Arrays;

/**
 * @author zhangchangyong
 * @date 2021-01-07 16:17
 */
public class MyAnagram {

    public static void main(String[] args) {
        System.out.println(isAnagram("python","typonh"));
    }

    static boolean isAnagram(String wordA, String wordB) {
        if (wordA == null || wordB == null) {
            return false;
        }
        if (wordA.length() != wordB.length()) {
            return false;
        }

        char[] wordAChars = wordA.toCharArray();
        char[] wordBChars = wordB.toCharArray();
        Arrays.sort(wordAChars);
        Arrays.sort(wordBChars);
        return new String(wordAChars).equals(new String(wordBChars));
    }
}
