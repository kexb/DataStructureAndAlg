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
        minTree.prim(graph, 0);
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

    /**
     * 编写prim算法 得到最小生成树
     *
     * @param graph 图
     * @param v     顶点
     */
    public void prim(MGraph graph, int v) {
        //visited[] 标记顶点是否被访问过
        int[] visited = new int[graph.verxs];
        //visited[] 默认元素的值都是0 表示没有访问过
        for (int i : visited) {
            visited[i] = 0;
        }
        visited[v] = 1;
        //顶点1下标
        int h1 = -1;
        //顶点2下标
        int h2 = -1;
        //初始化为一个大树 后面遍历过程中 会被替换
        int minWeight = 10000;
        //确定每一次生成的子图和哪个节点的距离最近
        for (int k = 1; k < graph.verxs; k++) {//因为有graph.verxs个顶点 普利姆算法结束后 右graph.verxs-1条边
            for (int i = 0; i < graph.verxs; i++) {//i节点表示被访问过的节点
                for (int j = 0; j < graph.verxs; j++) {//j节点表示还没有被访问过的节点
                    if (visited[i] == 1 && visited[j] == 0 && graph.weight[i][j] < minWeight) {
                        minWeight = graph.weight[i][j];
                        h1 = i;
                        h2 = j;
                    }
                }
            }
            //找到一条边是最小
            System.out.printf("边<%s,%s> 权值:%s\n", graph.data[h1], graph.data[h2], minWeight);
            //当前节点标记为已访问
            visited[h2] = 1;
            //minWeight重新设置为最大值
            minWeight = 10000;
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
