package com.perf._06_memory;

import java.util.HashMap;
import java.util.Map;

public class EqHcMemoryLeak {

    public final String key;


    public EqHcMemoryLeak(String key) {
        this.key = key;
    }


    public static void main(String args[]) {

        try {
            Map<EqHcMemoryLeak, String> leak = new HashMap<>();
            for (; ; ) {
                leak.put(new EqHcMemoryLeak("key"), "value");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
