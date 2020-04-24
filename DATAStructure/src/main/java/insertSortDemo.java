import java.util.Arrays;

public class insertSortDemo {
    public static void main(String[] args) {
        int[] arr = {5, 3, 9, 4, 7};
        insertSort(arr);
        sysoutArr(arr);
    }

    private static void sysoutArr(int[] arr) {
        System.out.println(Arrays.toString(arr));
    }

    public static void insertSort(int[] arr) {
        //39 29 19
        for (int i = 1; i < arr.length; i++) {
            int insertValue = arr[i];
            int j = i;
            //往前看 如果前面的大于要插入的值 前面的值后移arr[j] = arr[j - 1] 并且指针实际前移j--
            //如果虽然可以往前看 但是前面的值小于后面的值 那么退出循环直接插入arr[j]=insertValue
            //如果不能往前看 退出循环 当前的j就是temp的位置 arr[j]=insertValue
            while (j - 1 >= 0 && insertValue < arr[j - 1]) {
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = insertValue;
        }
    }
}
