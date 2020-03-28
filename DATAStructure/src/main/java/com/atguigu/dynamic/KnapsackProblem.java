package com.atguigu.dynamic;

public class KnapsackProblem {
    public static void main(String[] args) {
        int[] w = {1, 4, 3};
        int[] val = {1500, 3000, 2000};//物品的价值数组
        int m = 4;//背包的容量
        int n = val.length;//物品的个数


        //v[i][j]表示在前i个物品中能够装入容量为j的背包的最大价值
        int[][] v = new int[n + 1][m + 1];
        //为了记录放入商品的情况 我们定义个二维数组
        int[][] path = new int[n + 1][m + 1];
        //初始化第一行和第一列 在这里可以不去处理 因为默认值就是0
        for (int i = 0; i < v.length; i++) {
            v[i][0] = 0;//将第一行设置为0
        }
        for (int i = 0; i < v[0].length; i++) {
            v[0][i] = 0;//将第一列设置为0
        }
        System.out.println("规划前...");
        foreachVal(v);
        //根据前面得到的公式来动态规划处理
        for (int i = 1; i < v.length; i++) {//不处理第一行0
            for (int j = 1; j < v[0].length; j++) {//不处理第一列0
                //公式采用物品计数从1开始 现实世界说第1个物品 第二个物品 不会说第0个物品
                //当前物品背包放不下 采取上一个策略
                if (w[i - 1] > j) {
                    v[i][j] = v[i - 1][j];
                } else {
                    //背包放得下 将第i个物品的价值 + 前i-1物品背包放入剩余（j-w{w-1])的价值 与 前一个策略的价值相比取最大值
                    //v[i][j] = Math.max(v[i - 1][j], val[i - 1] + v[i - 1][j - w[i - 1]]);
                    //为了记录商品存放到背包的情况 我们不能简单的使用公式 需要使用if-else来体现公式
                    if (v[i - 1][j] < val[i - 1] + v[i - 1][j - w[i - 1]]) {
                        v[i][j] = val[i - 1] + v[i - 1][j - w[i - 1]];
                        //把当前的情况记录到path
                        path[i][j] = 1;
                    } else {
                        v[i][j] = v[i - 1][j];
                    }

                }
            }
        }

        System.out.println("规划后...");
        foreachVal(v);


        System.out.println("路径记录~~~");
        //这样输出会把所有的放入情况都得到 其实我们只需要最后的放入情况
        for (int i = 0; i < path.length; i++) {
            for (int j = 0; j < path[i].length; j++) {
                if (path[i][j] == 1) {
                    System.out.printf("第%d个商品放入到背包%8s", i," ");
                }
            }
            System.out.println();
        }
        System.out.println("路径记录~~~");
        //动脑筋
        int i = path.length - 1;//行的最大下标
        int j = path[0].length - 1;//列的最大下标
        while (i > 0 && j > 0) {
            if (path[i][j] == 1) {
                System.out.printf("第%d个商品放入到背包\n", i);
                j -= w[i - 1];
            }
            i--;
        }
    }

    private static void foreachVal(int[][] v) {
        for (int i = 0; i < v.length; i++) {
            for (int j = 0; j < v[i].length; j++) {
                System.out.printf("%8s", v[i][j] + " ");
            }
            System.out.println();
        }
    }
}
