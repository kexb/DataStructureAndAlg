public class cir02 {
    public static void main1(String[] args) throws Exception {
        CircleArray c=new CircleArray(6);
        c.addQueue(1);
        c.addQueue(2);
        c.addQueue(3);
        c.addQueue(4);
        c.addQueue(5);
        String pos1=getPos(c);

        c.getQueue();
        c.getQueue();
        String pos2=getPos(c);

        c.addQueue(3);
        String pos3=getPos(c);
        c.addQueue(4);
        String pos4=getPos(c);



        c.getQueue();
        c.getQueue();
        c.getQueue();
        c.getQueue();
        c.show();

        c.getQueue();

        c.show();

        System.out.println("================================");
        c.getQueue();//

        c.show();
        System.out.println("222");
    }

    private static String getPos(CircleArray c) {
        return "front:"+c.getFront()+"\t rear="+c.getRear();
    }
}

class CircleArray {
    public int getQueue() throws Exception {
        if(isEmpty()){
            throw new Exception("队列空。。");
        }
        int value=arr[front];
        front=(front+1)%maxSize;
        return value;
    }
    public void addQueue(int n){
        if(isFull()){
            System.out.println("队列满。。。。");
            return;
        }
        arr[rear]=n;
        rear=(rear+1)%maxSize;
    }
    private int maxSize;
    private int front=0;
    private int rear=0;
    private int[] arr;

    public int getFront() {
        return front;
    }

    public int getRear() {
        return rear;
    }

    public CircleArray(int maxSize) {
        this.maxSize = maxSize;
        this.arr = new int[maxSize];

    }

    public boolean isFull() {
         boolean ful= (rear+1)%maxSize==front;
         return ful;
    }




    public boolean isEmpty() {
        return front==rear;
    }
    public void show(){
        if(isEmpty()){
            System.out.println("空。。");
        }
        for (int i = front; i < front+activeSize(); i++) {
            System.out.printf("arr[%d]=%d\n",i%maxSize,arr[i%maxSize]);
        }
    }
    public int activeSize(){
        return (rear+maxSize-front)%maxSize;
    }
}
