package com.perf._07_jvm_tuning.techniques;

import java.util.concurrent.atomic.AtomicInteger;

import static com.perf.Utils.printRuntimeOf;

public class NoMethodChecksInLoops {

    private static final AtomicInteger ai = new AtomicInteger();

    public static void main(String[] args) {

        printRuntimeOf(() -> {
            for (int i = 0; i < 1_000; i++) {
                ai.addAndGet(i);
            }
        });
        printRuntimeOf(() -> {
            for (int i = 0; shouldEnd(); i++) {
                ai.addAndGet(i);
            }
        });
    }

    static boolean shouldEnd() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return !(ai.get() % 1000 == 0);
    }
}
