package com.perf._06_memory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ThreadMemoryLeak {


    public static void main(String args[]) {

        new Thread(() -> {
            List<UUID> ids = new ArrayList<>();
            while(true) {
                ids.add(UUID.randomUUID());
            }
        }).start();

    }

}
