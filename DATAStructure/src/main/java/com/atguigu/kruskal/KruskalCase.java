package com.atguigu.kruskal;

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
            for (int j = 0; j < vlen; j++) {
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
}
