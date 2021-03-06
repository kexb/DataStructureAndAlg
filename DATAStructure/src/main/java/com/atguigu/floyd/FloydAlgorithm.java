package com.atguigu.floyd;

import org.omg.CORBA.MARSHAL;

import java.util.*;

//佛洛依德算法
public class FloydAlgorithm {
    public static void main(String[] args) {
        //测试看看图是否创建成功
        char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        HashMap<String,Integer> map=new HashMap<>();
        String[] str = new String[vertex.length];
        for (int i = 0; i < vertex.length; i++) {
            str[i] = vertex[i] + "";
            map.put(str[i],i);
        }
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
        graph.floyd();
        graph.show();

        int start = map.get("B");
        int end = map.get("G");
        System.out.printf("%s到%s的最短路径====\r\n", start, end);
        List<Integer> theRoad = graph.findTheRoad(graph.getPre(), start, end);
        for (Integer iroad : theRoad) {
            System.out.printf(str[iroad] + " ");
        }
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

    public int[][] getPre() {
        return pre;
    }

    private static HashMap<Integer, Character> map = new HashMap<>();

    static {
        char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        for (int i = 0; i < vertex.length; i++) {
            map.put(i, vertex[i]);
        }
    }

    //构造器
    public Graph(int length, int[][] matrix, char[] vertex) {
        this.vertex = vertex;
        this.dis = matrix;
        this.pre = new int[length][length];
        //对pre数组初始化 存放的是前驱顶点的下标
        for (int i = 0; i < length; i++) {
            //如(0,0) (0,1) (0,2) (0,3)的前驱是0
            //如(i,0) (i,1) (i,2) (i,3)的前驱是i
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
                System.out.printf("(min<%s,%s>=%s)", this.vertex[k], this.vertex[i], dis[k][i] + "  ");
            }
            System.out.println();
        }
    }

    //佛洛依德算法
    public void floyd() {
        int len = 0; //变量保存距离
        //从中间顶点遍历 k就是中间顶点的下标 //[A,B,C,D,E,F,G]
        for (int k = 0; k < this.dis.length; k++) {
            //从i顶点出发 //[A,B,C,D,E,F,G]
            for (int i = 0; i < this.dis.length; i++) {
                //到达j顶点 [A,B,C,D,E,F,G]
                for (int j = 0; j < this.dis.length; j++) {
                    String iStr = map.get(i) + "";
                    String jStr = map.get(j) + "";
                    String ijStr = (map.get(i) + "" + map.get(j));
                    String ijPre = map.get(pre[i][j]) + "";

                    String kjStr = (map.get(k) + "" + map.get(j));
                    String kjPre = map.get(pre[k][j]) + "";

                    //求出从i顶点出发 经过中间顶点k 到达j顶点的距离
                    len = this.dis[i][k] + this.dis[k][j];
                    //如果len小于dis[i][j]
                    if (len < this.dis[i][j]) {
                        this.dis[i][j] = len;//更新距离
                        this.pre[i][j] = this.pre[k][j];//更新前驱顶点
                    }
                }
            }
        }
    }

    /**
     * 用于输出弗洛伊德算法的路径
     *
     * @param path  得到的路径矩阵
     * @param start 开始点，注意这个点是从零开始计数的  例如从 0到3
     * @param end   结束点，也是从零开始计数的
     * @return List数组  返回的值也是从零开始计数的。
     */
    public List<Integer> findTheRoad(int[][] path, int start, int end) {
        Stack<Integer> list = new Stack<>();
        list.push(end);
        int temp = path[start][end];
        while (temp != start) {
            list.push(temp);
            temp = path[start][temp];
        }
        list.push(start);
        Collections.reverse(list);
        return list;
    }
}
