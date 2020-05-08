package com.atguigu.findn_1_number_max;

import java.util.Arrays;
import java.util.Scanner;

public class FindNum2 {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("你要测试多少个数的乘积：");
//        int n=sc.nextInt();
//        int[] num=new int[n];
//        for (int i = 0; i <n ; i++) {
//            System.out.println("请输入第"+(i+1)+"个数：");
//            num[i]=sc.nextInt();
//        }
        int n=4;
        int[] num= {1,2,3,4};
        int[] dp=new int[n+1];//dp表示长度为n的数组中选取n-1个数字的最大乘积
        int[] min=new int[n+1];//min表示长度为n的数组中选取n-1个数字的最小乘积
        dp[0]=0;
        dp[1]=0;//长度为1时，最大乘积为0
        dp[2]=Math.max(num[0],num[1]);//长度为2时，最大乘积为两个数中的较大值
        min[0]=0;
        min[1]=0;
        min[2]=Math.min(num[0],num[1]);//长度为2时，最小乘积为两个数中的较小值
        for (int i = 3; i <=n ; i++) {
            dp[i]=Math.max(getResult(num,i-1),Math.max(dp[i-1]*num[i-1],min[i-1]*num[i-1]));
            //Math.min(dp[i-1]*num[i-1],min[i-1]*num[i-1]) 参数1可以变为最小值，参数2也可能是最小值
            min[i]=Math.min(getResult(num,i-1),Math.min(dp[i-1]*num[i-1],min[i-1]*num[i-1]));
        }
        System.out.println(dp[n]);
    }
    //求数组乘积
    public static int getResult(int[] num,int i){
        int result=1;
        for (int j = 0; j <i ; j++) {
            result=result*num[j];
        }
        return result;
    }

}
