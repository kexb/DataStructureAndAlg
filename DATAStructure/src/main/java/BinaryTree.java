//public class BinaryTree {
//    private HeroNode root;
//
//    public void setRoot(HeroNode root) {
//        this.root = root;
//    }
//
//    public void delNode(int no) {
//        if (root != null) {
//            if (root.getNo() == no) {
//                root = null;
//            } else {
//                //递归删除
//                root.delNode(no);
//            }
//        } else {
//            System.out.println("空树不能删除");
//        }
//    }
//
//    //前续遍历
//    public HeroNode preOrderSearch(int no) {
//        if (root != null) {
//            return this.root.preOrderSearch(no);
//        } else {
//            return null;
//        }
//    }
//
//    //中续遍历
//    public HeroNode inflixOrderSearch(int no) {
//        if (root != null) {
//            return this.root.infixOrderSearch(no);
//        } else {
//            return null;
//        }
//    }
//
//    //后续遍历
//    public HeroNode postOrderSearch(int no) {
//        if (root != null) {
//            return this.root.postOrderSearch(no);
//        } else {
//            return null;
//        }
//    }
//}