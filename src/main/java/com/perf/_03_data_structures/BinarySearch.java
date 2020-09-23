package com.perf._03_data_structures;

import com.perf.Utils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.binarySearch;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class BinarySearch {

    public static void main(String[] args) {

        List<Long> nums = Utils.generateRandomNumbers(5_000_000)
                .boxed()
                .collect(Collectors.toList());
        List<Long> sortedNums = nums
                .stream()
                .sorted()
                .collect(Collectors.toList());

        long num = nums.get(1);

        Collections.shuffle(nums);

        System.out.println(Utils.measureRuntimeOf(() -> nums.indexOf(num)));
        System.out.println(Utils.measureRuntimeOf(() -> binarySearch(sortedNums, num)));
    }
}
