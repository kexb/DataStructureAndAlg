public class Helper {
    public static void setRandomNumber2Array(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 80000);
        }
    }
    public static void swap(int[] arr) {
        if (arr[1] < arr[0]) {
            int temp = arr[1];
            arr[1] = arr[0];
            arr[0] = temp;
        }
    }
}
