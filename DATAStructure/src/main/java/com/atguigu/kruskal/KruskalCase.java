package com.atguigu.kruskal;

import java.util.Arrays;

//克鲁斯卡尔案例
public class KruskalCase {
    private int edgeNum;//边的个数
    private char[] vertexts;//顶点数组
    private int[][] matrix;//邻接矩阵
    private static final int INF = Integer.MAX_VALUE;//使用INF表示两个顶点不能连通

    public static void main(String[] args) {
        char[] vertexs = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int[][] martrix = {
                /*A*//*B*//*C*//*D*//*E*//*F*//*G*/
                /*A*/     {0, 12, INF, INF, INF, 16, 14},
                /*B*/     {12, 0, 10, INF, INF, 7, INF},
                /*C*/     {INF, 10, 0, 3, 5, 6, INF},
                /*D*/     {INF, INF, 3, 0, 4, INF, INF},
                /*E*/     {INF, INF, 5, 4, 0, 2, 8},
                /*F*/     {16, 7, 6, INF, 2, 0, 9},
                /*G*/     {14, INF, INF, INF, 8, 9, 0}

        };
        KruskalCase kruskalCase = new KruskalCase(vertexs, martrix);
        kruskalCase.print();
    }

    private static void testSort(KruskalCase kruskalCase) {
        EData[] edges = kruskalCase.getEdges();
        System.out.println("排序前=" + Arrays.toString(edges));
        kruskalCase.sortEdges(edges);
        System.out.println("排序后=" + Arrays.toString(edges));
    }

    ;

    public KruskalCase(char[] vertexs, int[][] martrix) {
        //初始化顶点数和边的个数
        int vlen = vertexs.length;
        //初始化顶点 复制拷贝的方式
        this.vertexts = new char[vlen];
        for (int i = 0; i < vertexs.length; i++) {
            this.vertexts[i] = vertexs[i];
        }
        //初始化 使用的是复制拷贝的方式
        this.matrix = new int[vlen][vlen];
        for (int i = 0; i < vlen; i++) {
            for (int j = 0; j < vlen; j++) {
                this.matrix[i][j] = martrix[i][j];
            }
        }
        //统计边
        for (int i = 0; i < vlen; i++) {
            for (int j = i + 1; j < vlen; j++) {
                if (this.matrix[i][j] != INF) {
                    this.edgeNum++;
                }
            }
        }
    }

    //打印邻接矩阵
    public void print() {
        System.out.println("邻接矩阵为:\n");
        for (int i = 0; i < this.vertexts.length; i++) {
            for (int j = 0; j < this.vertexts.length; j++) {
                System.out.printf("%12d\t", this.matrix[i][j]);
            }
            System.out.println();
        }
    }

    //对边进行排序处理,冒泡排序
    public void sortEdges(EData[] edges) {
        for (int i = 0; i < edges.length - 1; i++) {
            for (int j = 0; j < edges.length - i - 1; j++) {
                if (edges[j].weight > edges[j + 1].weight) {
                    EData tmp = edges[j];
                    edges[j] = edges[j + 1];
                    edges[j + 1] = tmp;
                }
            }
        }
    }

    /**
     * @param ch 顶点的值 比如'A','B'
     * @return 返回ch顶点对应的下标 如果找不到返回-1
     */
    public int getPosition(char ch) {
        for (int i = 0; i < vertexts.length; i++) {
            if (vertexts[i] == ch) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 功能: 获取途中边 放到EData[]数组 后面我们需要遍历该数组
     * 是通过martrix 邻接矩阵来获取
     * EData[] 形式[['A','B',12],['B'],'F',7]......]
     *
     * @return
     */
    private EData[] getEdges() {
        int index = 0;
        EData[] edges = new EData[this.edgeNum];
        for (int i = 0; i < vertexts.length; i++) {
            for (int j = i + 1; j < vertexts.length; j++) {
                if (this.matrix[i][j] != INF) {
                    edges[index++] = new EData(this.vertexts[i], this.vertexts[j], this.matrix[i][j]);
                }
            }
        }
        return edges;
    }

    /**
     * 功能: 获取下标为i的顶点的终点 用于后面判断两个顶点的终点是否相同
     *
     * @param ends 数组就是记录了各个顶点对应的终点是哪个 ends数组是在遍历过程中 逐步形成的
     * @param i    传入顶点对应的下标
     * @return 返回的就是下标为i的这个顶点对应的终点的下标
     */
    private int getEnd(int[] ends, int i) {
        while (ends[i] != 0) {
            i = ends[i];
        }
        return i;
    }

}

//编写一个雷EData 他的对象实例就表示一条边
class EData {
    char start;//边的一个点
    char end;//边的另外一个点
    int weight;//边的权值

    public EData(char start, char end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }
    //重写toString 便于输出

    @Override
    public String toString() {
        return "EData{" +
                "start=" + start +
                ", end=" + end +
                ", weight=" + weight +
                '}';
    }
}
