package com.perf._07_jvm_tuning.techniques;

import java.util.concurrent.TimeUnit;

import static com.perf.Utils.printRuntimeOf;

public class UsePrimitives {


    public static void main(String[] args) {

        int[] nums = new int[10_000];
        Integer[] ints = new Integer[10_000];

        for (int i = 0; i < nums.length; i++) {
            nums[i] = i;
            ints[i] = i;
        }

        printRuntimeOf(TimeUnit.MICROSECONDS, () -> {
            int counter = 0;
            for (int num : nums) {
                counter += num;
            }
        });
        printRuntimeOf(TimeUnit.MICROSECONDS, () -> {
            int counter = 0;
            for (int num : ints) {
                counter += num;
            }
        });
    }
}
