package com.atguigu.dijkstra;

import org.omg.CORBA.MARSHAL;

import java.util.Arrays;
import java.util.HashMap;

//迪杰斯特拉算法
public class DijkstraAlgorithm {
    public static void main(String[] args) {
        char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        HashMap<Integer, Character> map = new HashMap<>();
        for (int i = 0; i < vertex.length; i++) {
            map.put(i, vertex[i]);
        }
        //邻接矩阵
        int[][] martrix = new int[vertex.length][vertex.length];
        final int N = 65535;//表示不可连接
        martrix[0] = new int[]{N, 5, 7, N, N, N, 2};
        martrix[1] = new int[]{5, N, N, 9, N, N, 3};
        martrix[2] = new int[]{7, N, N, N, 8, N, N};
        martrix[3] = new int[]{N, 9, N, N, N, 4, N};
        martrix[4] = new int[]{N, N, 8, N, N, 5, 4};
        martrix[5] = new int[]{N, N, N, 4, 5, N, 6};
        martrix[6] = new int[]{2, 3, N, N, 4, 6, N};
        Graph graph = new Graph(vertex, martrix);
        graph.showGraph();
        for (int i = 0; i < vertex.length; i++) {
            System.out.printf("从%s出发===================", map.get(i));
            graph.dsj(i);
            graph.showDijkstra();
            System.out.println();
        }


    }
}

class Graph {
    private char[] vertex;//顶点数组
    private int[][] martrix;//邻接矩阵
    private VisitedVertex vv;//已经访问的顶点的集合
    private static HashMap<Integer, Character> map = new HashMap<>();

    static {
        char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        for (int i = 0; i < vertex.length; i++) {
            map.put(i, vertex[i]);
        }
    }

    //构造器
    public Graph(char[] vertex, int[][] martrix) {
        this.vertex = vertex;
        this.martrix = martrix;
    }

    public void showDijkstra() {
        vv.show();
    }

    //显示图
    public void showGraph() {
        for (int[] link : martrix) {
            System.out.println(Arrays.toString(link));
        }
    }

    /**
     * 迪杰斯特拉算法实现
     *
     * @param index 表示出发顶点对应的下标
     */
    public void dsj(int index) {
        vv = new VisitedVertex(this.vertex.length, index);
        //更新index顶点到周围顶点的距离
        update(index);
        for (int j = 1; j < vertex.length; j++) {
            index = vv.updateArr();
            update(index);
        }
    }

    //更新index下标顶点到周围顶点的距离和周围顶点的前驱顶点
    private void update(int index) {
        int len;
        int startPoint2IndexDix = 0;
        int ijDix;
        //boolean jNoAccess;
        //根据遍历我们的邻接矩阵的martrix{index]行
        for (int j = 0; j < martrix[index].length; j++) {
            //出发顶点到index顶点的距离
            startPoint2IndexDix = vv.getDis(index);
            //从index顶点到j的距离的和
            ijDix = martrix[index][j];
            //len 含义是: 出发顶点到index顶点的距离+从index顶点到j的距离的和
            len = startPoint2IndexDix + ijDix;
            //jNoAccess = !vv.in(j);
            //如果j顶点没有被访问过 并且len 小于出发顶点到j顶点的距离 就需要更新
            //len < vv.getDis(j) 例子G->B->D 和 G->F->D  com/atguigu/dijkstra/图解.xlsx 和 53.迪杰斯特拉算法-图解
            //jNoAccess &&
            if (len < vv.getDis(j)) {
                //出发-》index-》j
                vv.updatePre(j, index);//j顶点的前驱更新为index顶点
                vv.updateDis(j, len);//更新出发顶点到j顶点的距离
            }
        }
    }

}

class VisitedVertex {
    //记录各个顶点时是否访问过 1表示访问过 0问访问 会动态更新
    public int[] already_arr;
    //每个下标对应的值为前一个顶点下标 会动态更新
    public int[] pre_visited;
    //记录出发顶点到其他所有顶点的距离
    // 比如G为出发顶点 就会记录G到其他顶点的距离 会动态更新
    //求的最短距离就会存放到dis
    public int[] dis;

    /**
     * @param length 表示顶点的个数
     * @param index  出发顶点对应的下标比如G顶点 下标就为6
     */
    public VisitedVertex(int length, int index) {
        this.already_arr = new int[length];
        this.pre_visited = new int[length];
        this.dis = new int[length];
        //初始化 dis数组
        Arrays.fill(dis, 65535);
        this.already_arr[index] = 1;
        //设置出发顶点的访问距离为0
        this.dis[index] = 0;
    }

    /**
     * 功能: 判断index顶点是否被访问过
     *
     * @param index
     * @return 访问过:true 未访问:fasle
     */
    public boolean in(int index) {
        return already_arr[index] == 1;
    }

    /**
     * 功能: 更新出发顶点到index顶点的距离
     *
     * @param index
     * @param len
     */
    public void updateDis(int index, int len) {
        dis[index] = len;
    }

    /**
     * 功能: 更新pre这个顶点的前驱顶点为index节点
     *
     * @param pre
     * @param index
     */
    public void updatePre(int pre, int index) {
        pre_visited[pre] = index;
    }

    /**
     * 功能: 返回出发顶点到index这个顶点的距离
     *
     * @param index
     */
    public int getDis(int index) {
        return dis[index];
    }

    //继续选择并返回新的访问顶点 比如这里G完后 就是A作为新的访问顶点(注意不是出发点)
    public int updateArr() {
        int min = 65535, index = 0;
        for (int i = 0; i < already_arr.length; i++) {
            //顶点还没有被访问过 且距离最短的选择出来作为新的访问顶点
            if (already_arr[i] == 0 && dis[i] < min) {
                min = dis[i];
                index = i;
            }
        }
        //更新index顶点被访问过
        already_arr[index] = 1;
        return index;
    }

    //显示最后的结果
    //即将3个数组的情况输出
    public void show() {
        System.out.println("====================================");
        //先输出already_arr
        for (int i : already_arr) {
            System.out.print(i + " ");
        }
        System.out.println();
        //输出pre_visited
        for (int i : pre_visited) {
            System.out.print(i + " ");
        }
        System.out.println();
        //dis
        for (int i : dis) {
            System.out.print(i + " ");
        }
        System.out.println();

        //为了好看最后的距离
        char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int count = 0;
        for (int i : dis) {
            if (i != 65535) {
                System.out.print(vertex[count++] + "(" + i + ")");
            } else {
                System.out.println("N");
            }
        }

    }
}
