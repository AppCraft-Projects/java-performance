package com.perf._07_jvm_tuning.techniques;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static com.perf.Utils.printRuntimeOf;

public class NoRecursion {

    private static final AtomicInteger ai = new AtomicInteger();

    public static void main(String[] args) {
        printRuntimeOf(TimeUnit.MICROSECONDS, () -> {
            accumulateNumsRec(10_000);
        });
        printRuntimeOf(TimeUnit.MICROSECONDS, () -> {
            accumulateNumsIter(10_000);
        });
    }

    static void accumulateNumsRec(int until) {
        if(until == 0) return;
        ai.incrementAndGet();
        accumulateNumsRec(until - 1);
    }

    static void accumulateNumsIter(int until) {
        for (int i = 0; i < until; i++) {
            ai.incrementAndGet();
        }
    }
}
