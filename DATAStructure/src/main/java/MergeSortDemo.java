import java.util.Arrays;

public class MergeSortDemo {


    public static void mainMergeSort(String[] args) {
        int[] a = {5, 4, 8, 7};
        int[] b = {2, 1, 6, 3};
        //int[] c = new int[]{4,5,7,8,1,2,3,6};
        int[] c = new int[800*10000];
        Helper.setRandomNumber2Array(c);


        System.out.println(DateTime.now());
        c = mergeSort(c);
        System.out.println(DateTime.now());

        System.out.println();
        if(c.length==800*10000)return;
        System.out.println(Arrays.toString(c));
    }

    private static void swap(int[] arr) {
        if (arr[1] < arr[0]) {
            int temp = arr[1];
            arr[1] = arr[0];
            arr[0] = temp;
        }
    }

    public static int[] mergeSort(int[] arr) {
        if (arr.length == 0) return new int[0];
        if (arr.length == 1) return arr;
        int len = arr.length;
        int mid = arr.length / 2;

        int[] a = new int[mid];
        for (int i = 0; i < mid; i++) {
            a[i] = arr[i];
        }
        int[] b = new int[len - mid];
        for (int i = 0; i < b.length; i++) {
            b[i] = arr[i + mid];
        }
        int[] t1 = mergeSort(a);
        int[] t2 = mergeSort(b);
        return mergeTwoArray(t1, t2);
    }

    public static int[] mergeTwoArray(int[] a, int[] b) {
        int[] c = new int[a.length + b.length];
        int l = 0;
        int r = 0;
        int cx = 0;
        while (true) {
            if (l >= a.length || r >= b.length) {
                break;
            }
            if (a[l] < b[r]) {
                c[cx++] = a[l];
                l++;
            } else {
                c[cx++] = b[r];
                r++;
            }
        }
        if (l >= a.length) {
            for (int i = r; i < b.length; i++) {
                c[cx++] = b[i];
            }

        } else if (r >= b.length) {
            for (int i = l; i < a.length; i++) {
                c[cx++] = a[i];
            }
        }
        return c;
    }
}
