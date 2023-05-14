package com.valdisdot.customersupport.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class Counter {
    //total word's count variable
    private int sum = 0;
    //storage for keyword and its frequency
    private Map<String, Integer> counter = new HashMap<>();

    public int increase(String word) {
        counter.compute(word, (key, value) -> Objects.isNull(value) ? 1 : value + 1);
        sum++;
        return counter.get(word);
    }

    public int decrease(String word) {
        counter.computeIfPresent(word, (key, value) -> value == 1 ? null : value - 1);
        sum--;
        //return 0 if null
        return counter.getOrDefault(word, 0);
    }

    //get sorted result map by keyword
    public TreeMap<String, Integer> getResult() {
        return new TreeMap<>(counter);
    }

    public int getSum() {
        return sum;
    }
}
