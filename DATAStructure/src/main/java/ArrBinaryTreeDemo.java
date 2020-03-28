public class ArrBinaryTreeDemo {
    public static void mainArrBinaryTreeDemo(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7};
        ArrBinaryTree arrBinaryTree=new ArrBinaryTree(arr);
        arrBinaryTree.preOrder();
    }

}

//编写一个ArrayBinaryTree,实现顺序存储二叉树遍历
class ArrBinaryTree {
    private int[] arr;//存储数据节点的数组()

    public ArrBinaryTree(int[] arr) {
        this.arr = arr;
    }
    public void preOrder(){
        preOrder(0);
    }

    //二叉树的前序遍历
    public void preOrder(int index) {
        if (arr == null || arr.length == 0) {
            System.out.println("数组为空");
        }
        System.out.println(arr[index]);
        //向左递归遍历
        if ((index * 2 + 1) <arr.length) {
            preOrder(2 * index + 1);
        }
        //向右递归遍历
        if ((index * 2 + 2) < arr.length) {
            preOrder(2 * index + 2);
        }
    }
}
