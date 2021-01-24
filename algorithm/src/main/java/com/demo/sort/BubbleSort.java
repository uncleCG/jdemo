package com.demo.sort;

/**
 * 冒泡排序
 *
 * @author zhangchangyong
 * @date 2020-09-22 14:35
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] arr = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        printArr(arr);
        // 外循环控制循环次数
        for (int i = arr.length - 1; i > 0; i--) {
            // 内循环控制单次冒泡过程
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swapArr(arr, j, j + 1);
                }
            }
            printArr(arr);
        }
    }

    static void swapArr(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    static void printArr(int[] arr) {
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}
