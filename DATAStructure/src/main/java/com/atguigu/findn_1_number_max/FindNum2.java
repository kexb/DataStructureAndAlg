package com.atguigu.findn_1_number_max;

import java.util.*;

public class FindNum2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("你要测试多少个数的乘积：");
        int[] num = {5, 6, 3, 9, 10, 7};
        int n = num.length;
        int[] dp = new int[n + 1];//dp表示长度为n的数组中选取n-1个数字的最大乘积
        int[] min = new int[n + 1];//min表示长度为n的数组中选取n-1个数字的最小乘积
        dp[0] = 0;
        dp[1] = 0;//长度为1时，最大乘积为0
        dp[2] = Math.max(num[0], num[1]);//长度为2时，最大乘积为两个数中的较大值
        min[0] = 0;
        min[1] = 0;
        min[2] = Math.min(num[0], num[1]);//长度为2时，最小乘积为两个数中的较小值
        List<Integer> path = new ArrayList<>();
        List<Integer> root = new ArrayList<>();
        //选出i-1个数乘积最大
        for (int i = 3; i <= n; i++) {
            int commonResult = getResult(num, i - 1);
            int next = num[i - 1];
            int maxResult = Math.max(dp[i - 1] * next, min[i - 1] * next);
            int minResult = Math.min(dp[i - 1] * next, min[i - 1] * next);
            min[i] = Math.min(commonResult, minResult);//Math.min(dp[i-1]*num[i-1],min[i-1]*num[i-1]) 参数1可以变为最小值，参数2也可能是最大值
            if (commonResult > maxResult && commonResult > minResult) {
                dp[i] = commonResult;
                path.clear();
                for (int k = 0; k < (i - 1); k++) {
                    path.add(k);
                }
            } else {
                if (maxResult > minResult) {
                    dp[i] = maxResult;
                } else {
                    dp[i] = minResult;
                }
                path.add(i - 1);
            }


        }
        if (num[0] > num[1]) {
            if (!path.contains(0)) {
                path.add(0, 0);
            }
        } else {
            if (!path.contains(1)) {
                path.add(0, 1);
            }

        }
        for (int i = 0; i < path.size(); i++) {
            root.add(i, num[path.get(i)]);
        }
        System.out.println(root);
        System.out.println(dp[n]);
    }

    //求数组乘积
    public static int getResult(int[] num, int i) {
        int result = 1;
        for (int j = 0; j < i; j++) {
            result = result * num[j];
        }
        return result;
    }

}
