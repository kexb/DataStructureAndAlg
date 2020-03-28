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
            int insertIndex = i - 1;
            int insertValue = arr[i];
            while (insertIndex >= 0 && insertValue < arr[insertIndex]) {
                arr[insertIndex + 1] = arr[insertIndex];
                insertIndex--;
            }
            if (insertIndex + 1 != i)
                arr[insertIndex + 1] = insertValue;
        }
    }
}
