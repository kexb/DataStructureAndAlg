package com.atguigu.algorithm.binarysearchnorecursion;

public class BinarySearchNoRecur {
    public static void main(String[] args) {
        int[] arr = {1, 3, 8, 10, 11, 67, 1000};
        for (int i = 0; i < arr.length; i++) {
            System.out.printf("a[%s]在%s\n",i,binarySearch(arr,arr[i]));
        }
    }

    public static int binarySearch(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        while (left <=right) {
            int mid = (left + right) / 2;
            if (arr[mid] > target) {
                right = mid - 1;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                return mid;
            }

        }
        return -1;
    }
}
