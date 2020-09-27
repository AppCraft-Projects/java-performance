package com.perf._03_data_structures;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static com.perf.Utils.generateRandomNumbers;
import static com.perf.Utils.measureRuntimeOf;

@SuppressWarnings({"MismatchedQueryAndUpdateOfCollection", "ResultOfMethodCallIgnored"})
public class TreeSetVsListAndSort {

    public static void main(String[] args) {
        List<Long> nums = generateRandomNumbers(5_000_000).boxed().collect(Collectors.toList());

        Set<Long> numbers = new TreeSet<>(nums);
        List<Long> sortedNumbers = nums.stream().sorted().collect(Collectors.toList());

        System.out.println("Insertion");
        System.out.printf("TreeSet: %s%n", measureRuntimeOf(() -> numbers.add(2_000_000L)));
        System.out.printf("List: %s%n", measureRuntimeOf(() -> {
            sortedNumbers.add(2_000_000L);
            Collections.sort(sortedNumbers);
        }));
        System.out.println();

        System.out.println("Search");
        System.out.printf("TreeSet: %s%n", measureRuntimeOf(() -> numbers.contains(2_000_000L)));
        System.out.printf("List: %s%n", measureRuntimeOf(() -> sortedNumbers.contains(2_000_000L)));
        System.out.println();
    }
}
