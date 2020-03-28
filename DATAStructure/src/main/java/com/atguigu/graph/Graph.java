package com.atguigu.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Graph {
    private ArrayList<String> vertexList;//存储顶点的集合
    private int[][] edges;//存储图对应的邻接矩阵
    private int numOfEdges;//表示边的个数
    private boolean[] isVisited;

    public boolean[] getIsVisited() {
        return isVisited;
    }

    public static void main(String[] args) {
        int n = 8;//节点个数
        String[] vertexs = {"1", "2", "3", "4", "5","6","7","8"};
        //String[] vertexs = {"A", "B", "C", "D", "E"};
        Graph graph = new Graph(n);
        //循环的添加顶点
        for (String vertex : vertexs) {
            graph.insertVertex(vertex);
        }
        //添加边
        //A-B A-C B-C B-D B-E
//        graph.insertEdge(0, 1, 1);
//        graph.insertEdge(0, 2, 1);
//        graph.insertEdge(1, 2, 1);
//        graph.insertEdge(1, 3, 1);
//        graph.insertEdge(1, 4, 1);


        graph.insertEdge(0, 1, 1);
        graph.insertEdge(0, 2, 1);
        graph.insertEdge(1, 3, 1);
        graph.insertEdge(1, 4, 1);
        graph.insertEdge(3, 7, 1);
        graph.insertEdge(4, 7, 1);
        graph.insertEdge(2, 5, 1);
        graph.insertEdge(2, 6, 1);
        graph.insertEdge(5, 6, 1);
        graph.showGraph();
        System.out.println("深度优先");
        graph.dfs();
        System.out.println("广度优先");
        graph.bfs();

    }
    //构造器
    public Graph(int n) {
        edges = new int[n][n];
        vertexList = new ArrayList<>(n);
        numOfEdges = 0;
        isVisited = new boolean[n];
    }

    //得到第一个邻接节点的下标 w
    public int getFirstNeighbor(int index) {
        for (int j = 0; j < vertexList.size(); j++) {
            if (edges[index][j] > 0) {
                return j;
            }
        }
        return -1;
    }

    //深度优先遍历算法
    //i第一次就是0
    public void dfs(boolean[] isVisited, int i) {
        //首先我们访问该节点,输出
        System.out.print(getValueByIndex(i) + "->");
        //将节点设置为已访问
        isVisited[i] = true;
        //查找节点i的第一个邻接节点w
        int w = getFirstNeighbor(i);
        while (w != -1) {//说明有邻接节点
            if (!isVisited[w]) {
                dfs(isVisited, w);
            }
            //如果w节点已经被访问过
            w = getNextNeighbor(i, w);
        }
    }

    //对dfs进行重载,遍历我们所有的节点
    public void dfs() {
        isVisited = new boolean[vertexList.size()];
        //遍历所有dfs节点 进行dfs回溯
        for (int i = 0; i < getNumOfVertex(); i++) {
            if (!isVisited[i]) {
                dfs(isVisited, i);
            }
        }
        System.out.println();
    }

    //对bfs进行重载,遍历我们所有的节点
    public void bfs() {
        isVisited = new boolean[vertexList.size()];
        //遍历所有dfs节点 进行dfs回溯
        for (int i = 0; i < getNumOfVertex(); i++) {
            if (!isVisited[i]) {
                bfs(isVisited, i);
            }
        }
        System.out.println();
    }

    //对一个节点进行广度优先遍历的方法
    public void bfs(boolean[] isVisited, int i) {
        int u;//表示队列的头结点对应的下标
        int w;//邻接节点w
        LinkedList<Integer> queue = new LinkedList(); //队列记录节点访问的顺序
        System.out.print(getValueByIndex(i) + "=>");
        //标记为已访问
        isVisited[i] = true;
        //将节点加入队列
        queue.addLast(i);
        while (!queue.isEmpty()) {
            //取出队列的头结点下标
            u = queue.removeFirst();
            //得到第一个邻节点的下标 w
            w = getFirstNeighbor(u);
            while (w != -1) {//找到邻节点
                //是否访问过
                if (!isVisited[w]) {
                    System.out.print(getValueByIndex(w) + "=>");
                    isVisited[w] = true;
                    queue.addLast(w);
                }
                //以u为前驱点 找到w后面的下一个邻接节点
                w = getNextNeighbor(u, w);
            }
        }
    }

    //根据前一个邻接节点的下标来获取下一个邻接节点
    public int getNextNeighbor(int v1, int v2) {
        for (int j = v2 + 1; j < edges.length; j++) {
            if (edges[v1][j] > 0) {
                return j;
            }
        }
        return -1;
    }

    //图中常用的方法
    //返回节点的个数
    public int getNumOfVertex() {
        return vertexList.size();
    }

    //显示图对应的矩阵
    public void showGraph() {
        for (int[] link : edges) {
            System.err.println(Arrays.toString(link));
        }
    }

    //得到边的数目
    public int getNumOfEdges() {
        return numOfEdges;
    }

    //返回v1和v2的权值
    public int getWeight(int v1, int v2) {
        return edges[v1][v2];
    }
    //显示图对应的矩阵


    //返回节点i(下标)对应的数据 0->"A" 1->"B" 2->"C"
    public String getValueByIndex(int i) {
        return vertexList.get(i);
    }


    //插入顶点
    public void insertVertex(String vertex) {
        vertexList.add(vertex);
    }


    /**
     * 添加边
     *
     * @param v1     第一个顶点对应的下标
     * @param v2     第二个顶点对应的下标
     * @param weight 权值
     */
    public void insertEdge(int v1, int v2, int weight) {
        edges[v1][v2] = weight;
        edges[v2][v1] = weight;
        numOfEdges++;
    }
}
