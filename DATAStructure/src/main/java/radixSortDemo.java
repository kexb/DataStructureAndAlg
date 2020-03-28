import java.util.Arrays;

public class radixSortDemo {
    public static void mainRadixSort(String[] args) {
        //int[] arr = {53, 3, 542, 748, 14, 214};
        int [] arr=new int[800*10000];
        Helper.setRandomNumber2Array(arr);
        System.out.println(DateTime.now());
        radixSort(arr);
        System.out.println(DateTime.now());
    }

    public static void radixSort(int[] arr) {
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        int maxLength = (max + "").length();


        int[][] bucket = new int[10][arr.length];

        //每个桶的元素个数
        int[] bucketElementCounts = new int[10];
        //需要几趟完成排序
        for (int i = 0, n = 1; i <maxLength; i++, n *= 10) {
            for (int j = 0; j < arr.length; j++) {
                //在第几个桶
                int digitOfElement = arr[j] / n % 10;
                bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
                bucketElementCounts[digitOfElement]++;
            }
            int index = 0;
            //遍历每一个桶
            for (int k = 0; k < bucketElementCounts.length; k++) {
                //第k个桶的元素数量不为0
                if (bucketElementCounts[k] != 0) {
                    for (int l = 0; l < bucketElementCounts[k]; l++) {
                        arr[index++] = bucket[k][l];
                    }
                }
                //第一轮处理后 计数桶置0
                bucketElementCounts[k] = 0;
            }
            //System.out.printf("第%s轮处理arr=%s\n", i + 1, Arrays.toString(arr));
        }

    }
}
