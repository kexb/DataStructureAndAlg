package com.atguigu.prim;

import java.util.Arrays;

//普利姆算法-最小生成树
public class PrimAlgorithm {
    public static void main(String[] args) {
        //测试看看图是否创建ok
        char[] data = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int verxs = data.length;
        //邻接矩阵的关系使用二维数组表示 10000这个大数 表示两个点不连通
        int[][] weight = new int[][]{
                {10000, 5, 7, 10000, 10000, 10000, 2},
                {5, 10000, 10000, 9, 10000, 10000, 3},
                {7, 10000, 10000, 10000, 8, 10000, 10000},
                {10000, 9, 10000, 10000, 10000, 4, 10000},
                {10000, 10000, 8, 10000, 10000, 5, 4},
                {10000, 10000, 10000, 4, 5, 10000, 6},
                {2, 3, 10000, 10000, 4, 6, 10000}

        };
        //创建MGraph对象
        MGraph graph = new MGraph(verxs);
        //创建一个MinTree对象
        MinTree minTree = new MinTree();
        //创建图
        minTree.createGraph(graph, verxs, data, weight);
        //输出
        minTree.showGraph(graph);
    }
}

//最小生成树->村庄图
class MinTree {
    public void createGraph(MGraph graph, int verx, char[] data, int[][] weight) {
        int i, j;
        for (i = 0; i < verx; i++) {
            graph.data[i] = data[i];
            for (j = 0; j < verx; j++) {
                graph.weight[i][j] = weight[i][j];
            }
        }
    }

    //显示图的邻接矩阵
    public void showGraph(MGraph graph) {
        for (int[] link : graph.weight) {
            System.out.println(Arrays.toString(link));
        }
    }
}

class MGraph {
    int verxs;//表示图的节点个数
    char[] data;//存放顶点的数据
    int[][] weight;//邻接矩阵存放边

    public MGraph(int verxs) {
        this.verxs = verxs;
        this.data = new char[verxs];
        this.weight = new int[verxs][verxs];//邻接矩阵
    }


}
