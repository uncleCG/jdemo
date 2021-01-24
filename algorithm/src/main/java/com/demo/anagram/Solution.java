package com.demo.anagram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ### 思路
 * 对strs中的每一个字符串strs[i]进行排序（桶排序），然后判断Map中是否有该关键字（例如bat，排序后为abt，判断Map中是否有abt）
 * 1.如果没有该关键字，则新建一个ArrayList，将strs[i]加入ArrayList，然后将ArrayList加入结果res。并将排序后的字符串作为key，其所属ArrayList存储在res中的位置作为value。
 * 2.如果有该关键字，则取出所属ArrayList在res中的位置，并将所属ArrayList从res中取出，将strs[i]加入ArrayList。
 * <p>
 * 执行结果
 * ![QQ图片20200328134315.png](https://pic.leetcode-cn.com/66e22e0d33b089b08a778524ec17155343b8e82e163933ff1f54c063d552c260-QQ%E5%9B%BE%E7%89%8720200328134315.png)
 */
class Solution {

    public static void main(String[] args) {
        String[] strArr = new String[]{"python", "go", "og", "golang", "pyonth"};
        Solution solution = new Solution();
        System.out.println(solution.groupAnagrams(strArr));
    }

    public List<List<String>> groupAnagrams(String[] strArr) {
        List<List<String>> res = new ArrayList<>();
        int length = strArr.length;
        if (length == 0) {
            return res;
        }
        Map<String, Integer> map = new HashMap<>();

        List<String> tempList;
        for (int i = 0; i < length; i++) {
            String temp = sort(strArr[i]);
            if (map.get(temp) == null) {
                List<String> list = new ArrayList<>();
                list.add(strArr[i]);
                map.put(temp, res.size());
                res.add(list);
            } else {
                int index = map.get(temp);
                tempList = res.get(index);
                tempList.add(strArr[i]);
            }
        }

        return res;
    }

    public String sort(String s) {
        StringBuilder builder = new StringBuilder();
        int[] buckets = new int[26];
        int length = s.length();

        for (int i = 0; i < length; i++) {
            buckets[s.charAt(i) - 'a']++;
        }

        for (int i = 0; i < 26; i++) {
            while (buckets[i] > 0) {
                builder.append((char) (i + 'a'));
                buckets[i]--;
            }
        }

        return builder.toString();
    }
}