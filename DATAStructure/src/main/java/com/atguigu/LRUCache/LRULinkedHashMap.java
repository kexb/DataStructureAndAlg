package com.atguigu.LRUCache;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRULinkedHashMap {

    public static void main(String[] arg){
        testLinkedHashMap();

    }

    public static void testLinkedHashMap() {
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(5,0.75F ,true){ //第三个参数设为true时，在被使用时会把被使用的数据放到结尾。
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, String> var){ //该方法原本是默认返回false，会在put之后被执行，重写该方法，在数据多于某个值时，返回true，删除头结点。
                if(this.size() > 5){
                    return true;
                }
                return false;
            }

        };
        map.put("aa", "1");
        map.put("bb", "2");
        map.put("cc", "3");
        map.put("dd", "4");
        System.out.println(map);
        map.get("cc");
        System.out.println("=================使用cc==================");
        System.out.println(map);

        map.get("bb");
        System.out.println("=================使用bb===================");
        System.out.println(map);

        map.put("ee","5");
        System.out.println("=================加入ee===================");
        System.out.println(map);

        map.put("ff","6");
        System.out.println("=================加入ff===================");
        System.out.println(map);
    }

    void print(LinkedHashMap<String, String> source) {
        source.keySet().iterator().forEachRemaining(System.out::println);
    }
}
