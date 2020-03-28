//八皇后问题
public class Queue8 {
    //一共多少个皇后
    int max = 8;
    //定义数组array，保存皇后放置的位置的结果，比如arr={0,4,7,5,2,6,1,3}
    int[] array = new int[max];
    static int count = 0;
    static int judgeCount = 0;

    public static void mainQueue8(String[] args) {
        Queue8 queue8 = new Queue8();
        queue8.check(0);
        System.out.println(count);//92
        System.out.println(judgeCount);//15720

    }

    //编写一个方法 放置第n个皇后
    private void check(int n) {
        if (n == max) {//n=8 说明8个皇后已经放好
            print();
            return;
        }
        //依次放入皇后 并判断是否冲突
        for (int i = 0; i < max; i++) {
            //先把当前这个皇后n,放到该行的第一列
            array[n] = i;
            //判断当前放置第n个皇后在i列是,是否冲突
            if (judge(n)) {
                //接着放第n+1个皇后,即开始递归
                check(n + 1);
            }
            //如果冲突,就继续执行 array[n]=i 即将第n个皇后,放置在本行的后移一个位置
        }
    }

    private void print() {
        count++;
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + "|");
        }
        System.out.println();
    }

    /**
     * 查看放置第n个皇后，是否与前面的n-1个皇后冲突
     *
     * @param n 表示第n个皇后
     * @return true:没有冲突 false:冲突
     */
    private boolean judge(int n) {
        judgeCount++;
        for (int i = 0; i < n; i++) {
            //array[i]==array[n]  是否在同一列
            //Math.abs(array[n]-array[i]) |行差|=|列差| 在同一斜线上
            // 0,0
            //      1,1
            //            2,2
            if (array[i] == array[n] || Math.abs(n - i) == Math.abs(array[n] - array[i])) {
                return false;
            }
        }
        return true;
    }
}
