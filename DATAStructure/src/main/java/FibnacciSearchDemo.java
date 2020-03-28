import java.util.Arrays;

public class FibnacciSearchDemo {
    public static int maxSize = 20;

    public static void mainFibnacciSearchDemo(String[] args) {
        int[] arr = {1, 8, 10, 89, 1000, 1234};
        System.out.println(fibSearch(arr,2));
    }

    public static int[] fib() {
        int[] f = new int[maxSize];
        f[0] = 0;
        f[0] = 1;
        for (int i = 2; i < maxSize; i++) {
            f[i] = f[i - 1] + f[i - 2];
        }
        return f;
    }

    //斐波拉契数列刚好满足黄金分割1 1 2 3 5 8 13 21 34 => 13/21=0.619 21/34=0.617 接近 黄金分割 0.618
    //黄金分割点线段一部分设为f(k-1) 另一段设为f(k-2)  则f(k-1)/f(k)=f(k-2)/f(k-1)
    //f(k)-1 = (f(k-1)-1) + 1 + (f(k-2)-1)
    //=>mid = low + f(k-1)-1
    //编写斐波拉契查找算法
    //a为数组 key为关键字
    public static int fibSearch(int[] a, int key) {
        int low = 0;
        int high = a.length - 1;
        int k = 0; //斐波拉契分割数值的下标
        int mid = 0;
        int[] f = fib();
        while (high > f[k] - 1) {
            k++;
        }
        //因为f(k)值可能大于a的长度,因此我们需要使用Arrays类,构造一个新的数组,并指向a
        //不足的部分使用0补充
        int[] temp = Arrays.copyOf(a, f[k]);
        //实际上需要使用a数组最后的数填充temp
        // temp={1, 8, 10, 89, 1000, 1234,0,0,0}=>temp={1, 8, 10, 89, 1000, 1234,1234,1234,1234}
        for (int i = high + 1; i < temp.length; i++) {
            temp[i] = a[high];
        }
        while (low <= high) {
            //黄金分割坐标
            mid = low + f[k - 1] - 1;
            if (key < temp[mid]) {//向左查找
                high = mid - 1;
                //#region 为什么k--
                //为什么是k--
                //说明1
                //1. 全部元素=前面的元素+后面的元素
                //2. f[k]=f[k-1]+f[k-2]
                //因为前面有f[k-1]个元素，可以继续拆分f[k-1]=f[k-2]+f[k-3]
                //即在f[k-1]的前面继续查找 k--
                //即下次循环 mid = low + f[k - 1 - 1] - 1;
                //endregion
                k--;
            } else if (key > temp[mid]) {
                low = mid + 1;
                //region 为什么是k-=2
                //为什么是k-=2
                //说明
                //1. 全部元素=前面的元素+后面的元素
                //2. f[k]=f[k-1]+f[k-2]
                //3. 因为后面我们有f[k-2] 可以继续拆分 f[k-2]=f[k-3]+f[k-4]
                //4. 即在f[k-2]的前面进行查找k-=2
                //5. 即下次循环 mid = low + f[k - 1 - 2] - 1;
                //endregion
                k -= 2;
            } else {
                if (mid <= high) {
                    return mid;
                } else {
                    return high;
                }
            }
        }
        return -1;
    }
}
