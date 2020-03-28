package com.atguigu.binarysorttree;

import java.util.*;

//二叉排序树
public class BinarySortTreeDemo {
    public static void main(String[] args) {
        int[] arr = {7, 3, 10, 12, 5, 1, 9, 2};
        //int[] arr = {1, 2, 3, 4, 5, 6, 7, 8};
        BinarySortTree binarySortTree = new BinarySortTree();
        for (int i = 0; i < arr.length; i++) {
            binarySortTree.add(new Node(arr[i]));
        }

        binarySortTree.delNode(2);
        binarySortTree.delNode(5);
        binarySortTree.delNode(9);
        binarySortTree.delNode(12);
        binarySortTree.delNode(7);
        binarySortTree.delNode(3);
        outPath(binarySortTree);
        binarySortTree.delNode(10);
        outPath(binarySortTree);
        binarySortTree.delNode(1);
        outPath(binarySortTree);
//
//
//        System.out.println("中序遍历二叉排序树");
//        binarySortTree.infixOrder();

    }

    private static void outPath(BinarySortTree binarySortTree) {
        Node root = binarySortTree.getRoot();
        if(root==null){
            System.out.println("二叉排序树为空");
            return;
        }
        List<String> path = root.getPath();
        LinkedHashMap<Integer, LinkedHashSet<String>> perRowValue = root.perRowValue(path);
        int count = path.get(0).replace(" ", "").length() - 1;
        for (Map.Entry<Integer, LinkedHashSet<String>> entry : perRowValue.entrySet()) {
            int blank = (int) Math.pow(2, count - 1);
            for (int i = 0; i < blank; i++) {
                System.out.printf(" ");
            }
            for (String s : entry.getValue()) {
                System.out.printf(s + "  ");
            }
            count--;
            System.out.println();
        }
    }
}

//创建二叉排序树
class BinarySortTree {
    private Node root;

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public Node search(int value) {
        if (root != null) {
            return root.search(value);
        } else {
            return null;
        }
    }

    public Node searchParent(int value) {
        if (root != null) {
            return root.searchParent(value);
        } else {
            return null;
        }
    }


    /**
     * 1. 返回以node为根节点的二叉排序树的最小节点的值
     * 2. 删除node为根节点的儿茶排序树的最小节点
     *
     * @param node
     */
    public int delRightTreeMin(Node node) {
        Node target = node;
        //循环查找左子节点 就会找到最小值(如果没有左子树传入的node就是最小值 Node target = node已经做了备份)
        while (target.left != null) {
            target = target.left;
        }
        //删除最小节点
        delNode(target.value);
        return target.value;
    }

    /**
     * 删除节点
     *
     * @param value 希望删除节点的值
     */
    public void delNode(int value) {
        //空树
        if (root == null) return;
        //树只有一个节点 而且要删除的节点刚好就是root节点
        if (root.value == value && root.left == null && root.right == null) {
            root = null;
            return;
        }

        Node targetNode = search(value);
        if (targetNode == null) {
            return;
        }
        //找到父节点
        Node parent = searchParent(value);
        if (targetNode.left == null && targetNode.right == null) {//删除的是叶子节点
            if (parent.left != null && parent.left.value == value) {
                parent.left = null;
            } else if (parent.right != null && parent.right.value == value) {
                parent.right = null;
            }
        } else if (targetNode.left != null && targetNode.right != null) {//删除的节点有两棵子树
            int minVal = delRightTreeMin(targetNode.right);
            targetNode.value = minVal;

        } else {//删除的节点只有一颗子树(因为不是叶子 也没有两棵子树 剩下的就只能是只有一颗子树)
            //如果要删除的只有左子节点
            if (targetNode.left != null) {
                if (parent == null) {
                    this.setRoot(targetNode.left);
                    return;
                }
                //targetNode是parent的左子节点
                if (parent.left.value == value) {
                    parent.left = targetNode.left;
                } else {
                    parent.right = targetNode.left;
                }
            } else {//说明要删除的节点只有右子节点
                if (parent == null) {
                    this.setRoot(targetNode.right);
                    return;
                }
                if (parent.left.value == value) {
                    parent.left = targetNode.right;
                } else {
                    parent.right = targetNode.right;
                }
            }
        }


    }

    public void add(Node node) {
        if (root == null) {
            root = node;
        } else {
            this.root.add(node);
        }
    }

    //二叉排序树 中序遍历刚好是有序
    public void infixOrder() {
        if (this.root != null) {
            this.root.infixOrder();
        } else {
            System.out.println("二叉排序树为空无法遍历！");
        }
    }
}

//创建节点
class Node {
    int value;
    Node left;
    Node right;

    public Node(int value) {
        this.value = value;
    }

    /**
     * 查找要删除的节点
     *
     * @param value 希望删除节点的值
     * @return 如果找到返回该节点 否则返回null
     */
    public Node search(int value) {
        if (value == this.value) {
            return this;
        } else if (value < this.value) {
            if (this.left == null) {
                return null;
            }
            return this.left.search(value);
        } else {
            if (this.right == null) {
                return null;
            }
            return this.right.search(value);
        }
    }

    /**
     * 查找要删除节点的父节点
     *
     * @param value 希望查找节点的值
     * @return 返回查找节点的父节点
     */
    public Node searchParent(int value) {
        if ((this.left != null && this.left.value == value) ||
                (this.right != null && this.right.value == value)) {
            return this;
        }
        if (value < this.value && this.left != null) {
            return this.left.searchParent(value);
        } else if (value >= this.value && this.right != null) {
            return this.right.searchParent(value);
        } else {
            //没有找到父节点
            return null;
        }
    }

    public void add(Node node) {
        if (node == null) return;
        if (node.value < this.value) {
            if (this.left == null) {
                this.left = node;
            } else {
                this.left.add(node);
            }
        } else {
            if (this.right == null) {
                this.right = node;
            } else {
                this.right.add(node);
            }
        }
    }

    public void infixOrder() {
        if (this.left != null)
            this.left.infixOrder();
        System.out.println(this);
        if (this.right != null)
            this.right.infixOrder();
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

    public List<String> getPath() {
        if (this.left == null && this.right == null) {
            return Collections.singletonList(value + " ");
        }
        List<String> leftPath = new ArrayList<>();
        if (this.left != null) {
            leftPath = this.left.getPath();
        }
        List<String> rightPath = new ArrayList<>();
        if (this.right != null) {
            rightPath = this.right.getPath();
        }
        List<String> path = new ArrayList<>();
        for (String s : leftPath) {
            path.add(this.value + " " + s);
        }
        for (String s : rightPath) {
            path.add(this.value + " " + s);
        }
        return path;
    }

    public LinkedHashMap<Integer, LinkedHashSet<String>> perRowValue(List<String> path) {
        //第几行 第几个 值
        LinkedHashMap<Integer, LinkedHashSet<String>> codes = new LinkedHashMap<>();
        for (String s : path) {
            String[] split = s.split(" ");
            int cx = 0;
            for (String s1 : split) {
                if (s1.isEmpty()) {
                    continue;
                }
                codes.putIfAbsent(cx, new LinkedHashSet<>());
                codes.get(cx).add(s1);
                cx++;
            }
        }
        return codes;
    }

}
