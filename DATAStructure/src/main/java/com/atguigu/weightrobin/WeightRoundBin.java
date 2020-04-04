package com.atguigu.weightrobin;

import java.util.LinkedHashMap;
import java.util.Map;

//平衡加权算法
public class WeightRoundBin {
    public static void main(String[] args) {
        for (int i = 0; i < 12; i++) {
            System.out.println(getServer());
        }
    }

    private static Map<String, Weight> weightMap = new LinkedHashMap<>();

    public static String getServer() {
        int totalWeight = 0;
        for (Integer weight : ServerIps.WEIGHT_MAP.values()) {
            totalWeight += weight;
        }
        if (weightMap.isEmpty()) {
            for (String ip : ServerIps.WEIGHT_MAP.keySet()) {
                Integer weight = ServerIps.WEIGHT_MAP.get(ip);
                weightMap.put(ip, new Weight(ip, weight, 0));
            }
        }
        //CurrentWeight+=weight
        for (Weight weight : weightMap.values()) {
            weight.setCurrentWeight(weight.getCurrentWeight() + weight.getWeight());
        }

        //max(currentWeight)
        Weight maxCurrentWeight = null;
        for (Weight weight : weightMap.values()) {
            if (maxCurrentWeight == null || weight.getCurrentWeight() > maxCurrentWeight.getCurrentWeight()) {
                maxCurrentWeight = weight;
            }
        }


        //max(currentWeight)-=sumWeight
        maxCurrentWeight.setCurrentWeight(maxCurrentWeight.getCurrentWeight() - totalWeight);

        //result
        return maxCurrentWeight.getIp();
    }
}

class Weight {
    private String ip;
    private Integer weight;
    private int currentWeight;

    public Weight(String ip, Integer weight, int currentWeight) {
        this.ip = ip;
        this.weight = weight;
        this.currentWeight = currentWeight;
    }

    public String getIp() {
        return ip;
    }

    public Integer getWeight() {
        return weight;
    }

    public int getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(int currentWeight) {
        this.currentWeight = currentWeight;
    }
}

class ServerIps {
    public static final Map<String, Integer> WEIGHT_MAP = new LinkedHashMap<>();

    static {
        WEIGHT_MAP.put("A", 5);
        WEIGHT_MAP.put("B", 1);
        WEIGHT_MAP.put("C", 1);
    }
}
