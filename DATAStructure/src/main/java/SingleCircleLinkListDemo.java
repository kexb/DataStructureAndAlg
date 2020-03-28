public class SingleCircleLinkListDemo {
    public static void main5(String[] args) {
        //addNode_1();
        //addNode_21();
        addNode_22();
    }

    private static void addNode_22() {
        //Node3 head=new Node3(0,null);
        SingleCircleLinkList list = new SingleCircleLinkList(null);
        list.addNode_2(new Node3(1, null));
        list.addNode_2(new Node3(2, null));
        list.addNode_2(new Node3(3, null));
        list.addNode_2(new Node3(4, null));
        list.addNode_2(new Node3(5, null));

        list.show();
        System.out.println("================================");
        list.show2_2(2);
    }

    private static void addNode_21() {
        //Node3 head=new Node3(0,null);
        SingleCircleLinkList list = new SingleCircleLinkList(null);
        list.addNode_2(new Node3(1, null));
        list.addNode_2(new Node3(2, null));
        list.addNode_2(new Node3(3, null));
        list.addNode_2(new Node3(4, null));
        list.addNode_2(new Node3(5, null));

        list.show();
        System.out.println("================================");
        list.show2_1(2);
    }

    private static void addNode_1() {
        //Node3 head=new Node3(0,null);
        SingleCircleLinkList list = new SingleCircleLinkList(null);
        list.addNode(new Node3(1, null), false);
        list.addNode(new Node3(2, null), false);
        list.addNode(new Node3(3, null), false);
        list.addNode(new Node3(4, null), false);
        list.addNode(new Node3(5, null), true);

        list.show();
        System.out.println("================================");
        list.show2_1(2);
    }
}

class SingleCircleLinkList {
    public Node3 head;
    public Node3 lastNode;

    public SingleCircleLinkList(Node3 head) {
        this.head = head;
    }

    public Node3 curNode;

    public void addNode_2(Node3 newNode) {
        this.lastNode = newNode;
        //自成环
        if (this.head == null) {
            this.head = newNode;
            this.head.next = this.head;
            this.curNode = this.head;
        } else {
            this.curNode.next = newNode;
            newNode.next = this.head;
            this.curNode = newNode;
        }
    }

    public void addNode(Node3 node, boolean isLast) {
        if (this.head == null) {
            this.head = node;
            return;
        }
        Node3 tmp = this.head;
        while (true) {
            if (tmp.next == null) {
                break;
            }
            tmp = tmp.next;
        }
        //此时tmp为最后一个元素
        tmp.next = node;
        if (isLast) {
            node.next = this.head;
        }
    }

    //测试用1
    public void show2_1(int m) {
        //1 2x 3 4 5            2
        //1.1) 1 3 4x 5         4
        //1.2) 1x 3 5           1
        //1.3) 3 5x             5
        //1.4)                  3
        //2->4->1->5->3
        Node3 next = this.head;
        Node3 prev = this.head;
        while (true) {
            //喊数
            for (int i = 1; i <= m - 1; i++) {
                next = next.next;
            }

            //出队start
            System.out.println(next);
            //自己指向自己
            if (next == next.next) {
                break;
            }
            //删除next
            prev.next = next.next;
            next = next.next;
            prev = next;
            //出队end


        }
    }

    public void show2_2(int m) {
        Node3 helper = this.lastNode;
        Node3 first = this.head;
        while (true) {

            //移动(m-1)次
            for (int i = 0; i < m - 1; i++) {
                helper = helper.next;
                first = first.next;
            }
            System.out.println(first);
            //上一次移动之后如果重合了 说明只有一个元素不用出队
            if (first == helper) {
                break;
            }
            first = first.next;
            helper.next = first;
        }
    }

    //出队
    private void pop(Node3 prev, Node3 next) {
        System.out.println(next);
        prev.next = next.next;
    }

    public void show() {
        Node3 tmp = this.head;
        boolean flag = false;
        while (true) {
            if (tmp == null || (flag && tmp == head)) {
                break;
            }
            flag = true;
            System.out.println(tmp);
            tmp = tmp.next;
        }
    }
}

class Node3 {
    public int data;
    public Node3 next;

    public Node3(int data, Node3 next) {
        this.data = data;
        this.next = next;
    }

    @Override
    public String toString() {
        return "Node3{" +
                "data=" + data +
                "}\r\n";
    }
}
