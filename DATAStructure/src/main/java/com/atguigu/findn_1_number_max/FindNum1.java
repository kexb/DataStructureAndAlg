package com.atguigu.findn_1_number_max;

import java.util.Scanner;
 
/**
 * @基本功能:长度为n的整数数组，找出其中任意(n-1)个乘积最大的那一组
 * @program:summary
 * @author:peicc
 * @create:2019-08-29 10:16:11
 **/
public class FindNum1 {
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
        int[] dp=new int[n+1];//dp表示长度为n的数组中选取n-1数字的最大乘积
        dp[0]=0;
        dp[1]=0;
        dp[2]=Math.max(num[0],num[1]);
        for (int i = 3; i <=n ; i++) {
            dp[i]=Math.max(getResult(num,i-1),dp[i-1]*num[i-1]);
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