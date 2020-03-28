public class DoubleLinkListDemo {
    public static void main2(String[] args) {
        Node2 head=new Node2(0,null,null);
        DoubleLinkList doubleLinkList=new DoubleLinkList(head);
        doubleLinkList.addNode(new Node2(1,null,null));
        doubleLinkList.addNode(new Node2(2,null,null));
        doubleLinkList.addNode(new Node2(3,null,null));
        doubleLinkList.addNode(new Node2(4,null,null));
        doubleLinkList.addNode(new Node2(5,null,null));
        doubleLinkList.show();
        System.out.println("输出数据成功");
        System.out.println("==========================");
        doubleLinkList.reveserHead();
        doubleLinkList.show();
    }
}

class DoubleLinkList {
    public Node2 head;

    public DoubleLinkList(Node2 head) {
        this.head = head;
    }
    public void reveserHead(){
        Node2 reveseHead=new Node2(0,null,null);
        Node2 tmp=this.head.next;
        if(tmp==null){
            System.out.println("链表为空！！");
            return;
        }

        //h  tmp hnext
        while (true){
            Node2 cur=tmp.next;
            tmp.next=reveseHead.next;
            reveseHead.prev=tmp;
            tmp.prev=reveseHead;
            reveseHead.next=tmp;
            //得到下一个元素
            tmp=cur;
            if(tmp==null){
                break;
            }
        }
        this.head.next=reveseHead.next;
        this.head.prev=reveseHead.prev;
    }

    public void addNode(Node2 node) {
        Node2 tmp = head;
        if(head.next==null){
            this.head.next=node;
            node.prev=this.head;
            return;
        }
        while (true) {
            if (tmp.next == null) {
                break;
            }
            tmp = tmp.next;
        }
        tmp.next = node;
        node.prev = tmp;
        node.next = null;
    }

    public void show() {
        Node2 tmp = this.head.next;
        while (true) {
            System.out.println(tmp);
            tmp = tmp.next;
            if (tmp == null) {
                break;
            }
        }
    }

}

class Node2 {
    private int data;
    public Node2 next;
    public Node2 prev;

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public Node2 getNext() {
        return next;
    }

    public void setNext(Node2 next) {
        this.next = next;
    }

    public Node2(int data, Node2 next, Node2 prev) {
        this.data = data;
        this.next = next;
        this.prev = prev;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                "}\r\n";
    }
}

