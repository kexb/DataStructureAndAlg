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
            while (j - 1 >= 0 && insertValue < arr[j - 1]) {
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = insertValue;
        }
    }
}
