import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ShellSortDemo {
    public static void mainShellSort(String[] args) {

        int[] arr = {8, 9, 1, 7, 2, 3, 5, 4, 6, 0};
//        for (int i = 0; i < arr.length; i++) {
//            arr[i] = (int) (Math.random() * 80000);
//        }
        String s1 = NowDateTime();
        System.out.println(s1);
        shellSort2(arr);
        String s2 = NowDateTime();
        System.out.println(s2);
        System.out.println("最后一轮希尔排序第" + Arrays.toString(arr));
    }

    private static String NowDateTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }

    /**
     * 希尔排序(位移式) 再慢希尔就爬出来了
     * @param arr
     */
    public static void shellSort2(int[] arr) {
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            //下标为i从gap开始 说明前面已经隔了gap个元素开始 如gap=0+5 0 1 2 3 4 5
            for (int i = gap; i < arr.length; i++) {
                int j = i;
                int temp = arr[j];
                while (j - gap >= 0 && temp < arr[j - gap]) {
                    arr[j] = arr[j - gap];
                    j -= gap;
                }
                arr[j] = temp;
            }
        }
    }

    /**
     * 伪希尔排序(交换式)
     * @param arr
     */
    public static void shellSort(int[] arr) {
        int temp = 0;
        int count = 0;
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < arr.length; i++) {
                for (int j = i - gap; j >= 0; j -= gap) {
                    if (arr[j] > arr[j + gap]) {
                        temp = arr[j];
                        arr[j] = arr[j + gap];
                        arr[j + gap] = temp;
                    }
                }
            }
            //System.out.println("希尔排序第" + (++count) + "轮=" + Arrays.toString(arr));
        }
    }
}
