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
            TreeNode y = x.p;
            while (y != null && x == y.right) {
                x = y;
                y = y.p;
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
            TreeNode y = x.p;
            while (y != null && x == y.left) {
                x = y;
                y = y.p;
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
        node.p = p;
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
            if (y.p == z) {
                x.p = y;
            } else {
                transplant(y, y.right);
                y.right = z.right;
                y.right.p = y;
            }
            transplant(z, y);
            y.left = z.left;
            y.left.p = y;
            y.color = z.color;
        }
        if (originalColor == Color.BLACK) {
            deletionFixup(x);
        }
    }

    private void deletionFixup(TreeNode x) {
        while (x != root && x.color == Color.BLACK) {
            if (x == x.p.left) {
                TreeNode w = x.p.right;
                if (w.color == Color.RED) {
                    w.color = Color.BLACK;
                    w.p.color = Color.RED;
                    leftRotate(x.p);
                    w = x.p.right;
                } else if (w.left.color == Color.BLACK && w.right.color == Color.BLACK) {
                    w.color = Color.RED;
                    x = x.p;
                } else if (w.right.color == Color.BLACK) {
                    w.left.color = Color.BLACK;
                    w.color = Color.RED;
                    rightRotate(w);
                    w = x.p.right;
                } else {
                    w.color = x.p.color;
                    x.p.color = Color.BLACK;
                    w.right.color = Color.BLACK;
                    leftRotate(x.p);
                    x = root;
                }
            } else {
                TreeNode w = x.p.left;
                if (w.color == Color.RED) {
                    w.color = Color.BLACK;
                    w.p.color = Color.RED;
                    rightRotate(x.p);
                    w = x.p.left;
                } else if (w.right.color == Color.BLACK && w.left.color == Color.BLACK) {
                    w.color = Color.RED;
                    x = x.p;
                } else if (w.left.color == Color.BLACK) {
                    w.right.color = Color.BLACK;
                    w.color = Color.RED;
                    leftRotate(w);
                    w = x.p.left;
                } else {
                    w.color = x.p.color;
                    x.p.color = Color.BLACK;
                    w.left.color = Color.BLACK;
                    rightRotate(x.p);
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
        if (u.p == TreeNode.nil) {
            root = v;
        } else if (u == u.p.left) {
            u.p.left = v;
        } else {
            u.p.right = v;
        }
        v.p = u.p;
    }

    private void leftRotate(TreeNode x) {
        TreeNode y = x.right;
        x.right = y.left;
        if (y.left != TreeNode.nil) {
            y.left.p = x;
        }
        if (x.p == TreeNode.nil) {
            root = y;
        } else if (x == x.p.left) {
            x.p.left = y;
        } else {
            x.p.right = y;
        }
        y.p = x.p;
        y.left = x;
        x.p = y;
    }

    private void rightRotate(TreeNode x) {
        TreeNode y = x.left;
        x.left = y.right;
        if (y.right != TreeNode.nil) {
            y.right.p = x;
        }
        if (x.p == TreeNode.nil) {
            root = y;
        } else if (x == x.p.left) {
            x.p.left = y;
        } else {
            x.p.right = y;
        }
        y.p = x.p;
        y.right = x;
        x.p = y;
    }

    private void insertionFixup(TreeNode z) {
        while (z.p.color == Color.RED) {
            if (z.p == z.p.p.left) {
                TreeNode y = z.p.p.right;
                if (y.color == Color.RED) {
                    z.p.color = Color.BLACK;
                    y.color = Color.BLACK;
                    z.p.p.color = Color.RED;
                    z = z.p.p;
                } else if (z == z.p.right) {
                    z = z.p;
                    leftRotate(z);
                } else {
                    z.p.color = Color.BLACK;
                    z.p.p.color = Color.RED;
                    rightRotate(z.p.p);
                }
            } else {
                TreeNode y = z.p.p.left;
                if (y.color == Color.RED) {
                    z.p.color = Color.BLACK;
                    y.color = Color.BLACK;
                    z.p.p.color = Color.RED;
                    z = z.p.p;
                } else if (z == z.p.left) {
                    z = z.p;
                    rightRotate(z);
                } else {
                    z.p.color = Color.BLACK;
                    z.p.p.color = Color.RED;
                    leftRotate(z.p.p);
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
        TreeNode p = nil;

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
        if (node == null||node.key==-999) return;
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