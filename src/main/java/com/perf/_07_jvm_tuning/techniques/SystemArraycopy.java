package com.perf._07_jvm_tuning.techniques;

import static com.perf.Utils.printRuntimeOf;
import static java.util.concurrent.TimeUnit.MICROSECONDS;

public class SystemArraycopy {

    public static void main(String[] args) {
        int[] arr = new int[10_000];

        int[] copied = new int[10_000];
        int[] manual = new int[10_000];

        printRuntimeOf(MICROSECONDS, () -> {
            System.arraycopy(arr, 0, copied, 0, arr.length);
        });

        printRuntimeOf(MICROSECONDS, () -> {
            for (int i = 0; i < arr.length; i++) {
                manual[i] = arr[i];
            }
        });
    }
}
