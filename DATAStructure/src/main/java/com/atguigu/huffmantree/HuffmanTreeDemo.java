package com.atguigu.huffmantree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HuffmanTreeDemo {
    public static void main(String[] args) {
        int[] arr = {13, 7, 8, 3, 29, 6, 1};
        Node huffmanTree = createHuffmanTree(arr);
        preOrder(huffmanTree);
    }
    public static void preOrder(Node root){
        if(root!=null) {
            root.preOrder();
        }else{
            System.out.println("是空数不能遍历!!");
        }
    }

    //创建赫夫曼树的方法
    public static Node createHuffmanTree(int[] arr) {
        //第一步为了操作方便
        //1. 遍历arr数组
        //2. 将arr的每个元素构成一个Node
        //3. 将Node放入到ArrayLis中
        List<Node> nodes = new ArrayList<>();
        for (int value : arr) {
            nodes.add(new Node(value));
        }
        while (nodes.size() > 1) {
            //排序从小到大
            Collections.sort(nodes);
            System.out.println("nodes=" + nodes);

            //取出根节点权值最小的两棵二叉树
            //(1)取出权值最小的节点（二叉树）
            Node leftNode = nodes.get(0);
            //(1)取出权值次小的节点（二叉树）
            Node rightNode = nodes.get(1);
            //(3)构建一颗新的二叉树
            Node parent = new Node(leftNode.value + rightNode.value);
            parent.left = leftNode;
            parent.right = rightNode;
            //(4)从ArrayList中删除处理过的二叉树
            nodes.remove(leftNode);
            nodes.remove(rightNode);
            //(5)将parent加入到nodes中
            nodes.add(parent);
            System.out.println("第一次处理后" + nodes);
        }
        //返回赫夫曼树的root节点
        return nodes.get(0);
    }
}

//创建节点类
//为了让Node对象 支持排序 需要实现 Comparable<Node>
class Node implements Comparable<Node> {
    char c;
    int value;//节点权值
    Node left;//指向左子节点
    Node right;//指向右子节点

    //写一个前序遍历
    public void preOrder() {
        System.out.println(this);
        if (this.left != null) {
            this.left.preOrder();
        }
        if (this.right != null) {
            this.right.preOrder();
        }
    }

    public Node(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

    @Override
    public int compareTo(Node o) {
        //表示从小到大排列
        return this.value - o.value;
    }
}
