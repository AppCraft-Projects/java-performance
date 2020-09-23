package com.perf._03_data_structures;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"MismatchedQueryAndUpdateOfCollection"})
public class Lookups {

    public static void main(String[] args) {

        List<Integer> nums = Arrays.asList(5, 1, 3, 6, 7, 8);

        Map<Integer, Integer> lookup = new HashMap<>();

        for (int i = 0; i < nums.size(); i++) {
            lookup.put(i, nums.get(i));
        }
    }
}
