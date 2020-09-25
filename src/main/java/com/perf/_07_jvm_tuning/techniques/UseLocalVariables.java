package com.perf._07_jvm_tuning.techniques;

import java.util.concurrent.atomic.AtomicInteger;

import static com.perf.Utils.printRuntimeOf;

public class UseLocalVariables {

    private static int counter;

    public static void main(String[] args) {
        int REPEAT = 1_000_000_000;
        final AtomicInteger total = new AtomicInteger();
        printRuntimeOf(() -> {
            for (int i = -REPEAT; i < 0; i++) {
                total.addAndGet(i);
            }
        });

        total.set(0);

        printRuntimeOf(() -> {
            for (counter = -REPEAT; counter < 0; counter++) {
                total.addAndGet(counter);
            }
        });
    }
}
