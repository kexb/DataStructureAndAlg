package com.atguigu.hannuo;

public class hannuota {
    public static int count=0;
    public static void main(String[] args) {
        hanoiTower(2, 'A', 'B', 'C');
        System.out.println(count);
    }

    //汉诺塔的移动方法
    //使用分治算法
    public static void hanoiTower(int num, char a, char b, char c) {
        count++;
        //如果只有一个盘
        if (num == 1) {
            System.out.println(  a + "->" + c);
        } else {
            //如果我们有n>=2情况 我们总是看做是两个盘 1.最下边的一个盘 2.上面的所有盘
            //1. 先把最上面的盘 A->B 移动过程中会使用到c
            hanoiTower(num - 1, a, c, b);
            //2. 把最下边的盘 A->C
            System.out.println( a + "->" + c);
            //3.把B塔的所有盘 从B->C 移动过程使用a塔
            hanoiTower(num - 1, b, a, c);
        }
    }
}
