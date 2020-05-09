package com.atguigu.rbtree;

import java.util.LinkedList;
import java.util.Queue;

public class RedBlackTree {
    private TreeNode root = TreeNode.nil;

    public TreeNode getRoot() {
        return root;
    }

    public TreeNode minimum() {
        TreeNode node = minimum(root);
        if (node == TreeNode.nil) {
            return null;
        }
        return node;
    }

    public TreeNode maximum() {
        TreeNode node = maximum(root);
        if (node == TreeNode.nil) {
            return null;
        }
        return node;
    }

    public TreeNode successor(TreeNode x) {
        if (x == TreeNode.nil) {
            return null;
        }
        if (x.right != TreeNode.nil) {
            return minimum(x.right);
        } else {
            TreeNode y = x.pre;
            while (y != null && x == y.right) {
                x = y;
                y = y.pre;
            }
            return y;
        }
    }

    public TreeNode predecessor(TreeNode x) {
        if (x == TreeNode.nil) {
            return null;
        }
        if (x.left != TreeNode.nil) {
            return maximum(x.left);
        } else {
            TreeNode y = x.pre;
            while (y != null && x == y.left) {
                x = y;
                y = y.pre;
            }
            return y;
        }
    }

    public TreeNode search(int key) {
        TreeNode node = root;
        while (node != TreeNode.nil) {
            if (key < node.key) {
                node = node.left;
            } else if (key > node.key) {
                node = node.right;
            } else {
                return node;
            }
        }
        return null;
    }

    public void insert(TreeNode node) {
        TreeNode p = TreeNode.nil;
        TreeNode t = root;
        while (t != TreeNode.nil) {
            p = t;
            if (node.key < t.key) {
                t = t.left;
            } else {
                t = t.right;
            }
        }
        node.pre = p;
        if (p == TreeNode.nil) {
            root = node;
        } else if (node.key > p.key) {
            p.right = node;
        } else {
            p.left = node;
        }
        node.color = Color.RED;
        node.left = TreeNode.nil;
        node.right = TreeNode.nil;
        insertionFixup(node);
    }

    public void delete(TreeNode z) {
        TreeNode y = z;
        TreeNode x = TreeNode.nil;
        Color originalColor = y.color;
        if (z.left == TreeNode.nil) {
            x = z.right;
            transplant(z, z.right);
        } else if (z.right == TreeNode.nil) {
            x = z.left;
            transplant(z, z.left);
        } else {
            y = minimum(z.right);
            originalColor = y.color;
            x = y.right;
            if (y.pre == z) {
                x.pre = y;
            } else {
                transplant(y, y.right);
                y.right = z.right;
                y.right.pre = y;
            }
            transplant(z, y);
            y.left = z.left;
            y.left.pre = y;
            y.color = z.color;
        }
        if (originalColor == Color.BLACK) {
            deletionFixup(x);
        }
    }

    private void deletionFixup(TreeNode x) {
        while (x != root && x.color == Color.BLACK) {
            if (x == x.pre.left) {
                TreeNode w = x.pre.right;
                if (w.color == Color.RED) {
                    w.color = Color.BLACK;
                    w.pre.color = Color.RED;
                    leftRotate(x.pre);
                    w = x.pre.right;
                } else if (w.left.color == Color.BLACK && w.right.color == Color.BLACK) {
                    w.color = Color.RED;
                    x = x.pre;
                } else if (w.right.color == Color.BLACK) {
                    w.left.color = Color.BLACK;
                    w.color = Color.RED;
                    rightRotate(w);
                    w = x.pre.right;
                } else {
                    w.color = x.pre.color;
                    x.pre.color = Color.BLACK;
                    w.right.color = Color.BLACK;
                    leftRotate(x.pre);
                    x = root;
                }
            } else {
                TreeNode w = x.pre.left;
                if (w.color == Color.RED) {
                    w.color = Color.BLACK;
                    w.pre.color = Color.RED;
                    rightRotate(x.pre);
                    w = x.pre.left;
                } else if (w.right.color == Color.BLACK && w.left.color == Color.BLACK) {
                    w.color = Color.RED;
                    x = x.pre;
                } else if (w.left.color == Color.BLACK) {
                    w.right.color = Color.BLACK;
                    w.color = Color.RED;
                    leftRotate(w);
                    w = x.pre.left;
                } else {
                    w.color = x.pre.color;
                    x.pre.color = Color.BLACK;
                    w.left.color = Color.BLACK;
                    rightRotate(x.pre);
                    x = root;
                }
            }
        }
        x.color = Color.BLACK;
    }

    private TreeNode minimum(TreeNode x) {
        if (root == TreeNode.nil) {
            return TreeNode.nil;
        }
        while (x.left != TreeNode.nil) {
            x = x.left;
        }
        return x;
    }

    private TreeNode maximum(TreeNode x) {
        if (x == TreeNode.nil) {
            return TreeNode.nil;
        }
        while (x.right != TreeNode.nil) {
            x = x.right;
        }
        return x;
    }

    private void transplant(TreeNode u, TreeNode v) {
        if (u.pre == TreeNode.nil) {
            root = v;
        } else if (u == u.pre.left) {
            u.pre.left = v;
        } else {
            u.pre.right = v;
        }
        v.pre = u.pre;
    }

    private void leftRotate(TreeNode cur) {
        TreeNode y = cur.right;
        cur.right = y.left;
        if (y.left != TreeNode.nil) {
            y.left.pre = cur;
        }
        if (cur.pre == TreeNode.nil) {
            root = y;
        } else if (cur == cur.pre.left) {
            cur.pre.left = y;
        } else {
            cur.pre.right = y;
        }
        y.pre = cur.pre;
        y.left = cur;
        cur.pre = y;
    }

    private void rightRotate(TreeNode cur) {
        TreeNode y = cur.left;
        cur.left = y.right;
        if (y.right != TreeNode.nil) {
            y.right.pre = cur;
        }
        if (cur.pre == TreeNode.nil) {
            root = y;
        } else if (cur == cur.pre.left) {
            cur.pre.left = y;
        } else {
            cur.pre.right = y;
        }
        y.pre = cur.pre;
        y.right = cur;
        cur.pre = y;
    }

    private void insertionFixup(TreeNode cur) {
        while (cur.pre.color == Color.RED) {//插入节点的前一个节点是红色
            if (cur.pre == cur.pre.pre.left) {//先向左
                TreeNode grandfather = cur.pre.pre;//祖父
                TreeNode uncle = grandfather.right;//叔叔
                TreeNode father = cur.pre;//老爹

                //如果叔叔是红色的节点，需要进行变色 父变黑 叔变黑 祖父变红
                if (uncle.color == Color.RED) {
                    father.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    grandfather.color = Color.RED;
                    cur = cur.pre.pre;
                }
                //先向左 再向右
                else if (cur == cur.pre.right) {
                    //移动到父节点
                    cur = cur.pre;
                    //对父节点左旋
                    leftRotate(cur);
                }
                //一直向左 令父节点变黑 祖父节点变红 祖父节点右旋
                else {
                    father.color = Color.BLACK;
                    grandfather.color = Color.RED;
                    rightRotate(grandfather);
                }
            }
            //先向右
            else {
                TreeNode uncle = cur.pre.pre.left;
                TreeNode father = cur.pre;
                TreeNode grandfather = cur.pre.pre;
                //如果叔叔是红节点 则父变黑 叔变黑 祖父变红
                if (uncle.color == Color.RED) {
                    father.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    grandfather.color = Color.RED;
                    cur = cur.pre.pre;
                }
                //先向右 再向左
                else if (cur == cur.pre.left) {
                    //当前节点 指向父节点
                    cur = cur.pre;
                    //并对父节点右旋
                    rightRotate(cur);
                }
                //一直向右 父节点变黑 祖父节点变红 祖父节点左旋
                else {
                    father.color = Color.BLACK;
                    grandfather.color = Color.RED;
                    leftRotate(grandfather);
                }
            }
        }
        root.color = Color.BLACK;
    }

    @Override
    public String toString() {
        Queue<TreeNode> q = new LinkedList<TreeNode>();
        StringBuilder sb = new StringBuilder();
        q.offer(root);
        while (!q.isEmpty()) {
            TreeNode node = q.poll();
            if (node == TreeNode.nil) {
                sb.append("#");
                continue;
            } else {
                sb.append(node);
                q.offer(node.left);
                q.offer(node.right);
            }
        }
        int p = sb.length();
        while (p > 0) {
            if (sb.charAt(p - 1) != '#') {
                break;
            }
            p--;
        }
        return sb.substring(0, p).toString();
    }

    static class TreeNode {
        public static final TreeNode nil = new TreeNode(-999);
        Color color = Color.BLACK;
        int key;
        TreeNode left = nil;
        TreeNode right = nil;
        TreeNode pre = nil;

        public TreeNode(int key) {
            this.key = key;
        }

        @Override
        public String toString() {
            return "(" + key + " " + color + ")";
        }
    }

    enum Color {
        RED, BLACK;
    }

    public static void main(String[] args) {
        RedBlackTree rbt = new RedBlackTree();
        int n = 10;
        for (int j = 1; j <= n; j++) {
            rbt.insert(new TreeNode(j));
        }
        //rbt.delete(five);
        printTree(rbt.getRoot());
    }

    private static void printTree(TreeNode node) {
        if (node == null || node.key == -999) return;
        Integer leftKey = null, rightKey = null;
        if (node.left != null) {
            leftKey = node.left.key;
        }
        if (node.right != null) {
            rightKey = node.right.key;
        }
        System.out.printf("key:%s ,left:%s ,right:%s\r\n", node.key, leftKey, rightKey);
        printTree(node.left);
        printTree(node.right);
    }
}  