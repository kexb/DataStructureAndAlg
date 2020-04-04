package com.atguigu.consistenthash;

import java.util.*;

//一致性hash算法
public class ConsistentHash {
    private static TreeMap<Integer, String> virtualNodes = new TreeMap<>();
    private static final int VIRTUAL_NODES = 160;

    static {
        for (String ip : ServerIps.LIST) {
            for (int i = 0; i < VIRTUAL_NODES; i++) {
                int hash = getHash(ip + i);
                virtualNodes.put(hash, ip);
            }
        }
    }

    public static String getServer(String client) {
        int hash = getHash(client);
        //大于hash virtualNodes子树的firstKey
        SortedMap<Integer, String> subMap = virtualNodes.tailMap(hash);
        Integer firstKey=null;
        if (subMap == null) {
            firstKey = virtualNodes.firstKey();
        } else {
            firstKey = subMap.firstKey();
        }
        return virtualNodes.get(firstKey);
    }

    private static int getHash(String str) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < str.length(); i++) {
            hash = (hash ^ str.charAt(i)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        if (hash < 0) {
            hash = Math.abs(hash);
        }
        return hash;
    }


    public static void main(String[] args) {
        for (int i = 0; i < 12; i++) {
            System.out.println(getServer("client" + i));
        }
    }
}


class ServerIps {
    public static final Map<String, Integer> WEIGHT_MAP = new LinkedHashMap<>();

    public static final List<String> LIST = Arrays.asList(
            "192.168.0.1",
            "192.168.0.2",
            "192.168.0.3",
            "192.168.0.4",
            "192.168.0.5",
            "192.168.0.6",
            "192.168.0.7",
            "192.168.0.8",
            "192.168.0.9",
            "192.168.0.10"
    );

    static {
        WEIGHT_MAP.put("A", 5);
        WEIGHT_MAP.put("B", 1);
        WEIGHT_MAP.put("C", 1);
    }
}

