package com.atguigu.floyd;

import java.util.Arrays;

//佛洛依德算法
public class FloydAlgorithm {
    public static void main(String[] args) {
        //测试看看图是否创建成功
        char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        //创建邻接矩阵
        int[][] matrix = new int[vertex.length][vertex.length];
        final int N = 65535;
        matrix[0] = new int[]{0, 5, 7, N, N, N, 2};
        matrix[1] = new int[]{5, 0, N, 9, N, N, 3};
        matrix[2] = new int[]{7, N, 0, N, 8, N, N};
        matrix[3] = new int[]{N, 9, N, 0, N, 4, N};
        matrix[4] = new int[]{N, N, 8, N, 0, 5, 4};
        matrix[5] = new int[]{N, N, N, 4, 5, 0, 6};
        matrix[6] = new int[]{2, 3, N, N, 4, 6, 0};
        //创建Graph 对象
        Graph graph = new Graph(vertex.length, matrix, vertex);
        graph.show();
    }
}

//创建图
class Graph {
    //顶点
    private char[] vertex;
    //保存 从各个顶点出发到其他顶点的距离 最后的结果 也是保留在该数组
    private int[][] dis;
    //保存到达目标顶点的前驱顶点
    private int[][] pre;

    //构造器
    public Graph(int length, int[][] matrix, char[] vertex) {
        this.vertex = vertex;
        this.dis = matrix;
        this.pre = new int[length][length];
        //对pre数组初始化 存放的是前驱顶点的下标
        for (int i = 0; i < length; i++) {
            Arrays.fill(pre[i], i);
        }
    }

    //显示pre数组和dis数组
    public void show() {
        for (int k = 0; k < dis.length; k++) {
            //先将pre数组输出一行
            for (int i = 0; i < dis.length; i++) {
                System.out.print(this.vertex[pre[k][i]] + " ");
            }
            System.out.println();
            //输出dis数组的一行
            for (int i = 0; i < dis.length; i++) {
                System.out.printf("(min<%s,%s>=%s)", this.vertex[k],this.vertex[i], dis[k][i] + "  ");
            }
            System.out.println();
        }
    }

}
