package com.demo.sort;

/**
 * 选择排序算法
 * 不稳定
 *
 * @author zhangchangyong
 * @date 2020-09-17 10:32
 */
public class SelectionSort {

    public static void main(String[] args) {
        int[] arr = {1, 6, 8, 7, 2, 5, 3, 9, 4};
        printArr(arr);
        for (int i = 0; i < arr.length - 1; i++) {
            int minPos = i;
            int maxPos = arr.length - i - 1;
            // 遍历得到最小最大位置
            for (int j = minPos + 1; j < arr.length; j++) {
                if (arr[j] < arr[minPos]) {
                    minPos = j;
                } else {
                    maxPos = j;
                }
                // minPos = arr[j] < arr[minPos] ? j : minPos;
                // maxPos = arr[j] < arr[maxPos] ? maxPos : j;
            }
            System.out.println("第" + (i + 1) + "次循环，minPos = " + minPos + ", maxPos = " + maxPos);
            //在内层循环结束，也就是找到本轮循环的最小的数以后，再进行交换
            swapArr(arr, i, minPos);
            // swapArr(arr, arr.length - i - 1, maxPos);
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
