import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class testMain {
    public static void main(String[] args) {
        //思路无序列表先排序，这里我直接给出数据，假设已经完成了排序
        //           0  1  2  3  4  5  6  7  8   9   10  11 12  13
        //int[] arr = {1, 5, 5, 5, 6, 7, 8, 8, 11, 12, 12, 14, 9, 5};
        int [] arr={1,1,1,3,1,1};
        int other;
        int sum = 2;
        //预测答案 (4,3) (5,1) (5,2) (6,1) (6,2) (8,0) (9,0)
        int len = arr.length;
        List<String> groups = new ArrayList<String>();
        HashMap<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
        for (int i = 0; i < len; i++) {
            other = sum - arr[i];
            accumulationMapValueIndex(arr, map, i);
            List<Integer> otherIdx = map.get(other);
            if (otherIdx != null) {
                for (Integer prev : otherIdx) {
                    if (i != prev)
                        groups.add(i + "," + prev);
                }
                i = continueEqualHandle(arr, other, map, groups, i);
            }
        }
        System.out.println("hello world");
        for (String group : groups) {
            System.out.println(group + "\t");
        }
    }

    private static int continueEqualHandle(int[] arr, int other, HashMap<Integer, List<Integer>> map, List<String> groups, int i) {

        List<Integer> otherIdx = map.get(other);
        int temp = arr[i];
        while (true) {
            //没有下一个了
            if (i+1>arr.length-1) {
                break;
            }
            if (arr[i + 1] == temp) {
                int i_plus_1 = ++i;
                accumulationMapValueIndex(arr, map, i_plus_1);
                for (Integer prev : otherIdx) {
                    if (i_plus_1 != prev)
                        groups.add(i_plus_1 + "," + prev);
                }
            } else {
                //没有连续相等的数了
                break;
            }
        }
        return i;
    }

    private static void accumulationMapValueIndex(int[] arr, HashMap<Integer, List<Integer>> map, int i) {
        if (map.get(arr[i]) == null) {
            map.put(arr[i], new ArrayList<Integer>());
        }
        map.get(arr[i]).add(i);
    }

}

