public class BinarySearchDemo {
    public static void maiBinaryDemo(String[] args) {
        int[] arr = {1,8,10,89,100,1234};
        for (int i = 0; i < arr.length; i++) {

            System.out.printf("%s在%s\n", arr[i], binarySearch2(arr, 0, arr.length - 1, arr[i]));
        }
        System.out.println(binarySearch2(arr,0,arr.length-1,1000));
    }

    public static int binarySearch2(int[] arr, int left, int right, int findValue) {
        int mid = (left + right) / 2;
        int midVal = arr[mid];
        while (true) {
            if (left > right) {
                return -1;
            }
            if (findValue > midVal) {//在->
                left = mid + 1;
                mid = (left + right) / 2;
                midVal = arr[mid];
            } else if (findValue < midVal) {//在<-
                right = mid - 1;
                mid = (left + right) / 2;
                midVal = arr[mid];
            } else {
                return mid;
            }
        }
    }

    public static int binarySearch1(int[] arr, int left, int right, int findValue) {
        if (left > right) {
            return -1;
        }
        int mid = (left + right) / 2;
        int midVal = arr[mid];
        if (findValue > midVal) {
            return binarySearch1(arr, mid + 1, right, findValue);
        } else if (findValue < midVal) {
            return binarySearch1(arr, left, mid - 1, findValue);
        } else {
            return mid;
        }
    }
}
