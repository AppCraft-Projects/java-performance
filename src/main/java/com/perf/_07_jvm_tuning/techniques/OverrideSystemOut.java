package com.perf._07_jvm_tuning.techniques;

import com.perf._07_jvm_tuning.NoOpPrintStream;

import java.io.PrintStream;
import java.util.concurrent.TimeUnit;

import static com.perf.Utils.RunTime;
import static com.perf.Utils.measureRuntimeOf;

public class OverrideSystemOut {

    public static void main(String[] args) {
        RunTime slow = measureRuntimeOf(TimeUnit.MICROSECONDS, () -> {
            for (int i = 0; i < 1000; i++) {
                System.out.println(i);
            }
        });
        PrintStream oldOut = System.out;
        System.setOut(new NoOpPrintStream(oldOut));
        RunTime fast = measureRuntimeOf(TimeUnit.MICROSECONDS, () -> {
            for (int i = 0; i < 1000; i++) {
                System.out.println(i);
            }
        });
        System.setOut(oldOut);
        System.out.println(slow);
        System.out.println(fast);
    }
}
