package com.atguigu.kmp;

import java.util.Arrays;

public class KmpAlgorithm {
    public static void main(String[] args) {
        //String str1 = "尚硅谷你 尚硅谷你尚硅 尚硅谷你尚硅谷你尚硅你好";
        //String str2 = "谷你尚";//尚硅谷你尚硅

        //ABBBDABBBBAAAACABBCBAAAACABBCAAAAACABBCAAAAACABB 主串
        //AAAACABBCAAAAACABB 子串
        int prexLen="ABBBDABBBBAAAACABBCBAAAACABBCAA".length();
        System.out.println("prexlen="+prexLen);
        String str1="C\t C\tC\tC\tC\tC\tC\tC\tC\tA\tA\tB\tB\tC\tA\tB\tB\tC\tA\t A\tB\tB\tC\tA\tB\tB\tC\tA\tA\tA\tB\tB\tC\tA\tB\tB"
                .replace("\t","")
                .replace(" ","");
        String str2="AAAACABBCAAAAACABB";
        String dest="A\t A\tB\tB\tC\tA\tB\tB\tC\tA\tA\tA\tB\tB\tC\tA\tB\tB"
                .replace("\t","")
                .replace(" ","");
        str2=dest;
        int[] next = kmpNext(str2);
        for (int i = 0; i < next.length; i++) {
            System.out.print(str2.charAt(i)+"\t");
        }
        System.out.println();
        for (int i = 0; i < next.length; i++) {
            System.out.print(next[i]+"\t");
        }
        System.out.println();
        System.out.println("====================");
        int index = kmpSearch(str1, str2, next);
        System.out.println(index);
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


                //3 另一个解释
                //在起始i之后 如今的i之前 这中间才存在匹配真正的起点
                //所以如果可以回溯就回溯到这之前 例如  1 2 3  。。。。 1 2 3 ？ 在字符串扫描到i的时候没有第4次匹配
                //我们循环回溯 直到回溯点j满足arr[i]==arr[j] (回溯代码j=next[j-1]) 并且j!=0 那么j-1之前的值也必定匹配
                //如果一直找不到满足arr[i]==arr[j] 那么最终j=0 那么j就得从头开始扫描


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

    //示例1：获取到一个字符串(子串)的部分匹配值表 从index=9 next[9]=1
    //                                              4   4
    //                                  ↓   ↓   ↓   ↓   ↓                   ↓指示匹配点(REAL)
    //C	 C	C	C	C	C	C	C	C	A	A	B	B	C	A	B	B	C	A	 A	B	B	C	A	B	B	C	A	A	A	B	B	C	A	B	B原始字符串1
    //                                      2   2
    //                                  ↓   ↓   ↓   ↓   ↓                   ↓指示匹配点(REAL)
    //A	 A	B	B	C	A	B	B	C	A	A	A	B	B	C	A	B	B  搜索字符串1
    //0	 1	2	3	0	1	0	0	0	1	2	2	3	4	5	6	7	8  next1[]
    //0  1  2   3   4   5   6   7   8   9   10  11  12  13  14  15  16  17 index


    //示例2：获取到一个字符串(子串)的部分匹配值表 从index=9 next[9]=1
    //C	 C	C	C	C	C	C	C	C	A	A	A	A	C	A	B	B	C	A	 A	A	A	C	A	B	B	C	A	A	A	B	B	C	A	B	B原始字符串1
    //                                  ↓   ↓   ↓   ↓   ↓                   ↓指示匹配点(REAL)
    //A	 A	A	A	C	A	B	B	C	A	A	A	A	A	C	A	B	B  搜索字符串2
    //0	 1	2	3	0	1	0	0	0	1	2	3	4	4	5	6	7	8  next2[]
    //0  1  2   3   4   5   6   7   8   9   10  11  12  13  14  15  16  17 index

    /**
     由回溯条件得知最后两个元素是一样的 假设为A
     则搜索字符串为 a1a2a3....AAB...的形式

     因为 存在可能的回溯 得出
     a1a2a3...A=a2a3a4...A

     由一 一 对应原则 可以再次得出
     a1=a2
     a2=a3
     an-1=an


     假设a1=A  则有N+1个A
     如AAAB...AAAAB A的个数是N=3 子串就有4个A+一个B

     **/
    public static int[] kmpNext(String dest) {
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

