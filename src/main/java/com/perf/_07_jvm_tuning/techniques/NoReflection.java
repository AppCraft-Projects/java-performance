package com.perf._07_jvm_tuning.techniques;

import java.util.concurrent.TimeUnit;

import static com.perf.Utils.printRuntimeOf;

public class NoReflection {

    public static void main(String[] args) {
        printRuntimeOf(TimeUnit.MICROSECONDS, () -> {
            for (int i = 0; i < 1000; i++) {
                try {
                    SomeClass.class.getConstructors()[0].newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        printRuntimeOf(TimeUnit.MICROSECONDS, () -> {
            for (int i = 0; i < 1000; i++) {
                new SomeClass();
            }
        });
    }

    static class SomeClass {

        private String foo;

        public SomeClass() {
            foo = "foo";
        }
    }
}
