package com.atguigu.kmp;

import java.util.Arrays;

public class KmpAlgorithm {
    public static void main(String[] args) {
        String str1 = "尚硅谷你 尚硅谷你尚硅 尚硅谷你尚硅谷你尚硅你好";
        String str2 = "谷你尚";//尚硅谷你尚硅

//        String str1 = "ABCDABCDEFG";
//        String str2 = "ABCDEFG";//尚硅谷你尚硅

        System.out.println("=====子串3====");
        System.out.println(Arrays.toString(kmpNext("AA ABA A A C A A   A  A   D".replace(" ",""))));
        System.out.println("=====子串3====");
        int[] next = kmpNext(str2);
        int index = kmpSearch(str1, str2, next);
        System.out.println("index=" + index);
        System.out.println(count);

        str2="AA ABA A A C A A   A  A   D".replace(" ","");
        str1="FFF"+"AAABA"+ str2+"QQQQ";
        index = kmpSearch(str1, str2, kmpNext(str2));
        System.out.println("index=" + index);
    }

    static int count = 0;

    public static int kmpSearch(String str1, String str2, int[] next) {
        for (int i = 0, j = 0; i < str1.length(); i++) {
            count++;
            while (j > 0 && str1.charAt(i) != str2.charAt(j)) {
                //i=i-j+1复原后右移j-next[j-1] <=> j左移j-(j-next[j-1])=next[j-1] 所以有两种写法 这两种效果相同 但是第2种不好理解
                //#region 1
                // 1.1 (i-j) 复原
                // 1.2 j-next[j-1] 移多少位 第j位不匹配说明前面(0~j-1)是匹配的长度为j 长度j减去连续匹配数next[j-1]就是位移数
//                i = i - j  + (j  - next[j - 1]);
//                j = 0;
                //endregion

                //#region 2 i可以保持 只需要j左移
                // 2.1 位移 k=j-next[j-1]位
                // 2.2 j=j-k=j-(j-next[j-1])=j-j+next[j-1]=next[j-1]
                 j = next[j - 1];
                //endregion


            }
            if (str1.charAt(i) == str2.charAt(j)) {
                j++;
            }
            if (j == str2.length()) {
                return i - (j - 1);
            }
        }
        return -1;
    }

    //获取到一个字符串(子串)的部分匹配值表
    public static int[] kmpNext(String dest) {
        //创建一个next数组 把偶才能部分匹配值
        int[] next = new int[dest.length()];
        for (int i = 1, j = 0; i < dest.length(); i++) {
            while (j > 0 && dest.charAt(i) != dest.charAt(j)) {
                //回溯 不相等去需要判断回溯的情况
                j = next[j - 1];
            }
            if (dest.charAt(i) == dest.charAt(j)) {
                j++;
            }
            next[i] = j;
        }
        return next;
    }
}
