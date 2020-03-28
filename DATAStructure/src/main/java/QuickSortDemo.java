import java.util.ArrayList;
import java.util.Arrays;

public class QuickSortDemo {
    public static void mainq1(String[] args) {
        int[] arr = new int[8 * 10000];// {-9, 78, 0, 23, -567, 70};
        int[] arr1 = new int[8 * 10000];// {-9, 78, 0, 23, -567, 70};
        Helper.setRandomNumber2Array(arr);
        //System.out.println(Arrays.toString(arr));
        System.out.println(DateTime.now());
        quickSort(arr, 0, arr.length - 1);
        System.out.println(DateTime.now());
        System.out.println(Arrays.toString(arr));
    }

    public static void quickSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int l = left;
        int r = right;
        int mid = (l + r) / 2;
        int pivot = arr[mid];
        while (l < r) {
            //左边递归
            while (arr[l] <= pivot && l < r) {
                l++;
            }
            arr[mid] = arr[l];
            mid = l;

            //右边递归
            while (arr[r] >= pivot && l < r) {
                r--;
            }
            arr[mid] = arr[r];
            mid = r;
        }
        arr[l] = pivot;
        quickSort(arr, left, mid - 1);
        quickSort(arr, mid + 1, right);

    }
}
