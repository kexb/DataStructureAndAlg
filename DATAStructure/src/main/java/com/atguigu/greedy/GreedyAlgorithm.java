package com.atguigu.greedy;

import com.sun.xml.internal.ws.encoding.HasEncoding;

import java.util.*;

//贪心算法
public class GreedyAlgorithm {
    public static void main(String[] args) {
        //创建广播电台 key为电台 value为覆盖地区
        HashMap<String, HashSet<String>> broadcasts = new HashMap<>();
        HashSet<String> hashSet1 = new HashSet<>();
        hashSet1.add("北京");
        hashSet1.add("上海");
        hashSet1.add("天津");

        HashSet<String> hashSet2 = new HashSet<>();
        hashSet2.add("广州");
        hashSet2.add("北京");
        hashSet2.add("深圳");

        HashSet<String> hashSet3 = new HashSet<>();
        hashSet3.add("成都");
        hashSet3.add("上海");
        hashSet3.add("杭州");

        HashSet<String> hashSet4 = new HashSet<>();
        hashSet4.add("上海");
        hashSet4.add("天津");
        hashSet4.add("大连");


        HashSet<String> hashSet5 = new HashSet<>();
        hashSet5.add("杭州");
        hashSet5.add("大连");
        hashSet5.add("重庆");

        broadcasts.put("k1", hashSet1);
        broadcasts.put("k2", hashSet2);
        broadcasts.put("k3", hashSet3);
        broadcasts.put("k4", hashSet4);
        broadcasts.put("k5", hashSet5);

        //存放所有的地区
        HashSet<String> allAreas = new HashSet<>();
        allAreas.addAll(hashSet2);
        allAreas.addAll(hashSet3);
        allAreas.addAll(hashSet5);
        allAreas.addAll(hashSet1);
        allAreas.addAll(hashSet4);


        //创建ArrayList存放选择的电台
        ArrayList<String> selects = new ArrayList<>();
        //定义一个临时的集合,在遍历的过程中,存放遍历过程中的电台覆盖的地区和当前还没有覆盖地区的交集
        HashSet<String> tempSet = new HashSet<>();
        //定义一个maxKey 保存在一次遍历过程中 能够覆盖最大未覆盖地区的电台的key
        String maxKey = null;
        HashMap<String, Integer> retainMap = new HashMap<>();
        //如果maxKey 不为空则会加入selects
        while (allAreas.size() != 0) {//如果allAreas不为0 表示还没有覆盖到所有的地区
            for (String key : broadcasts.keySet()) {
                HashSet<String> areas = broadcasts.get(key);
                tempSet.addAll(areas);
                //求出tempSet和allAreas的交集 赋给tempSet
                tempSet.retainAll(allAreas);
                if (tempSet.size() > 0 && (maxKey == null || tempSet.size() > retainMap.get(maxKey))) {
                    maxKey = key;
                    retainMap.put(key, tempSet.size());
                }

            }
            if (maxKey != null) {
                selects.add(maxKey);
                tempSet.clear();
                allAreas.removeAll(broadcasts.get(maxKey));
                maxKey = null;

            }

        }
        HashSet<String> list= getNoRepetSet(hashSet1,hashSet2,hashSet3,hashSet5);
        System.out.println("得到的结果是" + selects);
    }

    private static HashSet<String> getNoRepetSet(HashSet<String>... hashSet) {
        HashSet<String> set=new HashSet<>();
        for (HashSet<String> strings : hashSet) {
            set.addAll(strings);
        }
        return set;
    }

}
