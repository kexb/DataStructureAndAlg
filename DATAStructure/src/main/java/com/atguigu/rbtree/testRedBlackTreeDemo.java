package com.atguigu.rbtree;

public class testRedBlackTreeDemo {
    public static void main(String[] args) {
        RBTree<Integer> rbTree=new RBTree<>();
        for (int i = 1; i <= 10; i++) {
            rbTree.addNode(i);
        }
        RBTreeNode<Integer> root = rbTree.getRoot();
        rbTree.printTree(root);
    }

}
