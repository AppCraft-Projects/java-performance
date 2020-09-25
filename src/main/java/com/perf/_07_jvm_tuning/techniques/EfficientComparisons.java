package com.perf._07_jvm_tuning.techniques;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.perf.Utils.printRuntimeOf;

public class EfficientComparisons {

    public static void main(String[] args) {
        List<Integer> nums = Stream.iterate(1, i -> i + 1)
                .limit(1_000)
                .collect(Collectors.toList());

        AtomicInteger counter = new AtomicInteger();

        printRuntimeOf(() -> {
            nums.forEach((num) -> {
                if (num % 2 == 0 || notSoCheapComparison()) {
                    counter.incrementAndGet();
                }
            });
        });

        printRuntimeOf(() -> {
            nums.forEach((num) -> {
                if (notSoCheapComparison() || num % 2 == 0) {
                    counter.incrementAndGet();
                }
            });
        });
    }

    private static boolean notSoCheapComparison() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

}
