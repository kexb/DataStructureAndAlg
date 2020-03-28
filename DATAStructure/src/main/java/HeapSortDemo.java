import java.util.Arrays;

public class HeapSortDemo {
    public static void main(String[] args) {
        //要求将一个数组进行升序排序
        int[] arr = {4, 6, 8, 5, 9, -1, 90, 89, 56, -999};
        arr=new int[800*10000];
        Helper.setRandomNumber2Array(arr);
        System.out.println("len=" + arr.length);
        //System.out.println("第0次数组=" + Arrays.toString(arr));

        System.out.println(DateTime.now());
        heapSort(arr);
        System.out.println(DateTime.now());
    }


    //编写一个堆排序的方法
    public static void heapSort(int[] arr) {
        int temp = 0;
        int count=1;
        //保证每个非叶子树都是大顶堆
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            adjustHeap(arr, i, arr.length);
            //System.out.printf("第%s次数组=%s\r\n",count++, Arrays.toString(arr));
        }
        for (int j = arr.length - 1; j > 0; j--) {
            temp = arr[j];
            arr[j] = arr[0];
            arr[0] = temp;
            adjustHeap(arr, 0, j);
        }
        //System.out.println("数组=" + Arrays.toString(arr));

    }

    /**
     * 将一个数组(二叉树),调整成一个大顶堆
     * 功能: i对应的非叶子节点的树 调整成大顶堆
     * 4,6,8,5,9 初始
     * 4,9,8,5,6 第一次
     * 9,6,8,5,4 第二次
     *
     * @param arr    待调整的数组
     * @param i      表示非叶子节点在数组中的索引
     * @param length 表示对多少个元素进行调整
     */
    public static void adjustHeap(int[] arr, int i, int length) {
        int temp = arr[i];
        //开始调整
        //说明
        //初始=============
        //                4
        //       6                8
        //   5       9      -1        90
        //89   56  -999
        //1. k = i * 2 + 1 是 节点的左子节点
        for (int k = i * 2 + 1; k < length; k = k * 2 + 1) {
            //#k1
            if (k + 1 < length && arr[k] < arr[k + 1]) {//说明左子节点的值小于右子节点的值
                k++;// k 指向右子节点
            }
            if (arr[k] > temp) {//如果子节点大于父节点
                arr[i] = arr[k];//把较大的值赋值给当前节点
                i = k;//!!! i 指向 k,继续循环比较
            } else {
                //因为外层调整为大顶堆前就是从len/2-1开始每次-1 方向是从右至左边 从底到顶的方向调整的
                // 并且也判断了#k1 所以left不需要判断
                break;
            }
        }
        //for循环结束后 我们已经将i为父节点的树的最大值,放在了最顶(局部)
        arr[i] = temp;//将temp值放到调整后的位置
    }
}

//初始=============
//             4
//      6                8
//  5       9      -1        90
//89   56  -999

//第一次=============
//                  4
//         6                   8
//   5            9      -1        90
//89    56  -999


//第二次=============
//                  4
//         6                   8
//   89            9      -1        90
//5     56   -999


//第三次=============
//                  4
//         6                   90
//   89            9      -1        8
//5     56   -999

//第四次-1=============
//                  4
//         89                   90
//   6            9      -1        8
//5     56   -999


//第四次-2=============
//                  4
//         89                   90
//   56            9      -1        8
//5      6   -999


//第五次-1=============
//                  4
//         89                   90
//   56            9      -1        8
//5      6   -999


//第五次-2=============
//                  90
//         89                   8
//   56            9      -1        4
//5      6   -999

//至此成为一个大顶堆
//第1-0次 =============
//                 90
//         89                   8
//   56            9      -1        4
//5      6   -999

//第1-1次交换 =============
//                 -999
//         89                   8
//   56            9      -1        4
//5      6   90

//第1-2次交换 =============
//                     89
//         -999                   8
//   56            9      -1        4
//5      6   90


//第1-3次交换 =============
//                     89
//           56                     8
//   -999            9      -1           4
//5         6   90

//第1-4次交换 =============
//                     89
//           56                     8
//   6            9      -1           4
//5     -999   90

//已经有89,90两个沉到底了 其他同理