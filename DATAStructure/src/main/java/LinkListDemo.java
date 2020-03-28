public class LinkListDemo {
    public static void main3(String[] args) {
        Node head = new Node(0, null);
        Node node1 = new Node(1, null);
        Node node2 = new Node(2, null);
        Node node3 = new Node(3, null);
        Node node4 = new Node(4, null);
        Node node5 = new Node(5, null);
        LinkList linkList = new LinkList(head);
        linkList.addNode(node1);
        linkList.addNode(node2);
        linkList.addNode(node3);
        linkList.addNode(node4);
        linkList.addNode(node5);

        linkList.show();
        System.out.println("==================\r\n");
        linkList.reverse2();
        linkList.show();

    }
}

class LinkList {
    private Node head;

    public Node getHead() {
        return head;
    }

    public LinkList(Node head) {
        this.head = head;
    }

    public void addNode(Node node) {
        Node tmp = head;
        while (true) {
            if (tmp.getNext() == null) {
                tmp.setNext(node);
                break;
            }
            tmp = tmp.getNext();
        }
    }

    public void show() {
        Node tmp = head;
        while (true) {
            if (tmp.getNext() == null) {
                break;
            }
            System.out.println(tmp.getNext());
            tmp = tmp.getNext();
        }
    }

    public void reverse2() {
        Node reveseHead = new Node(0, null);
        Node cur = head.next;
        if (head.next == null || head.next.next == null) {
            throw new RuntimeException("只有一个元素，不需要反转");
        }
        while (true) {
            Node next = cur.next;
            cur.next = reveseHead.next;
            reveseHead.next = cur;
            cur = next;
            if (next == null) break;
        }
        head.next = reveseHead.next;
    }

    public void reverse1() {
        Node firstNode = head.next;
        Node lastNode = null;

        Node next = head.next.next;
        Node prev = head.next;
        if (head.next == null || head.next.next == null) {
            throw new RuntimeException("只要一个元素不需要反转");
        }
        Node tmpNext;
        //1  2  3
        while (true) {
            tmpNext = next.next;
            next.next = prev;
            prev = next;
            next = tmpNext;
            if (next == null) {
                lastNode = prev;
                break;
            }
        }
        //最后一个节点变成第一个节点
        if (lastNode != null) {
            head.next = lastNode;
        }
        //第一个节点变成最后一个节点
        if (firstNode != null) {
            firstNode.next = null;
        }

    }
}

class Node {
    private int data;
    public Node next;

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Node(int data, Node next) {
        this.data = data;
        this.next = next;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                "}\r\n";
    }
}
