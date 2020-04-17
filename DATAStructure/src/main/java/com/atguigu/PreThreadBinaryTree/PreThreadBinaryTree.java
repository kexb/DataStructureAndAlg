package com.atguigu.PreThreadBinaryTree;

public class PreThreadBinaryTree {
    public static void main(String[] args) {

        //测试一把中序线索二叉树的功能 以数组｛8, 3, 10, 1, 14, 6｝为例

        /**
         *          1
         *        /   \
         *       3     6
         *      / \   /
         *     8  10 14
         */

        HeroNode root = new HeroNode(1, "java");
        HeroNode node2 = new HeroNode(3, "C#");
        HeroNode node3 = new HeroNode(6, "Python");
        HeroNode node4 = new HeroNode(8, "C++");
        HeroNode node5 = new HeroNode(10, "GO");
        HeroNode node6 = new HeroNode(14, "Dephi");

        //二叉树，后面我们要递归创建, 现在简单处理使用手动创建
        root.setLeft(node2);
        root.setRight(node3);
        node2.setLeft(node4);
        node2.setRight(node5);
        node3.setLeft(node6);


        System.out.println("==========前序线索化开始=============");
        System.out.println("{1,3,8,10,6,14}");

        //前序：{1,3,8,10,6,14}
        ThreadedBinaryTree threadedBinaryTreePre = new ThreadedBinaryTree();
        threadedBinaryTreePre.setRoot(root);
        threadedBinaryTreePre.threadedNodesPre();

        System.out.println("==========前序线索化遍历=============");
        threadedBinaryTreePre.threadedListPre();//{1,3,8,10,6,14}
    }
}

class ThreadedBinaryTree {
    private HeroNode root;

    /**
     * 为了实现线索化，需要创建要给指向当前结点的前驱结点的指针
     * 在递归进行线索化时，pre 总是保留前一个结点
     */
    private HeroNode pre = null;

    public void setRoot(HeroNode root) {
        this.root = root;
    }


    /**
     * 重载一把threadedNodesPre方法
     */
    public void threadedNodesPre() {
        this.threadedNodesPre(root);
    }


    /**
     * 前序线索化二叉树遍历方法
     * 1
     * /   \
     * 3     6
     * / \   /
     * 8  10 14
     * <p>
     * {1,3,8,10,6,14}
     */
    public void threadedListPre() {
        //定义一个变量，存储当前遍历的结点，从root开始
        HeroNode node = root;
        while ( node != null ) {
            while ( node.getLeftType() == 0 ) {
                //如果是叶子节点，非前驱节点，打印当前这个结点
                System.out.print(node + "\r\n");
                node = node.getLeft();
            }
            System.out.print(node + "\r\n");
            //替换这个遍历的结点
            node = node.getRight();
        }
    }



    /**
     * 前序线索化
     * 变成数组后{1,3,8,10,6,14}
     * 1
     * /   \
     * 3     6
     * / \   /
     * 8  10 14
     *
     * @param node 就是当前需要线索化的结点
     */
    public void threadedNodesPre(HeroNode node) {
        //如果node==null, 不能线索化
        if (node == null) {
            return;
        }
        //左指针为空,将左指针指向前驱节点
        //8结点的.left = 上一个节点 , 8结点的.leftType = 1
        if (node.getLeft() == null) {
            //让当前结点的左指针指向前驱结点
            node.setLeft(pre);
            //修改当前结点的左指针的类型,指向前驱结点
            node.setLeftType(1);
        }
        //处理后继结点,是下一次进行处理，有点不好理解
        if (pre != null && pre.getRight() == null) {
            //让前驱结点的右指针指向当前结点
            pre.setRight(node);
            //修改前驱结点的右指针类型
            pre.setRightType(1);
        }
        //!!! 每处理一个结点后，让当前结点是下一个结点的前驱结点
        pre = node;
        //(一)先线索化左子树
        if (node.getLeftType() != 1) {
            threadedNodesPre(node.getLeft());
        }
        //(三)再线索化右子树
        if (node.getRightType() != 1) {
            threadedNodesPre(node.getRight());
        }

    }


}


 class HeroNode {
     public int getNo() {
         return no;
     }

     public void setNo(int no) {
         this.no = no;
     }

     public String getName() {
         return name;
     }

     public void setName(String name) {
         this.name = name;
     }

     public HeroNode getLeft() {
         return left;
     }

     public void setLeft(HeroNode left) {
         this.left = left;
     }

     public HeroNode getRight() {
         return right;
     }

     public void setRight(HeroNode right) {
         this.right = right;
     }

     public HeroNode getParent() {
         return parent;
     }

     public void setParent(HeroNode parent) {
         this.parent = parent;
     }

     public int getLeftType() {
         return leftType;
     }

     public void setLeftType(int leftType) {
         this.leftType = leftType;
     }

     public int getRightType() {
         return rightType;
     }

     public void setRightType(int rightType) {
         this.rightType = rightType;
     }

     private int no;
    private String name;
    /**
     * //默认null
     */
    private HeroNode left;
    /**
     * //默认null
     */
    private HeroNode right;

    /**
     * //父节点的指针（为了后序线索化使用）
     */
    private HeroNode parent;

    /**
     * //说明
     * //1. 如果leftType == 0 表示指向的是左子树, 如果 1 则表示指向前驱结点
     * //2. 如果rightType == 0 表示指向是右子树, 如果 1表示指向后继结点
     */
    private int leftType;
    private int rightType;

    public HeroNode(int no, String name) {
        this.no = no;
        this.name = name;
    }

    @Override
    public String toString() {
        return "HeroNode [no=" + no + ", name=" + name + "]";
    }

}