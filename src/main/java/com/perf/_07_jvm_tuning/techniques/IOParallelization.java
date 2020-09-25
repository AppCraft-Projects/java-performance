package com.perf._07_jvm_tuning.techniques;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IOParallelization {

    public static void main(String[] args) throws IOException {
        FileInputStream fis0 = new FileInputStream("src/main/resources/file0");
        FileInputStream fis1 = new FileInputStream("src/main/resources/file1");
        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(() -> {
            try {
                int data = fis0.read();
                while (data != -1) {
                    // ...
                    data = fis0.read();
                }
                fis0.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        executor.submit(() -> {
            try {
                int data = fis1.read();
                while (data != -1) {
                    // ...
                    data = fis1.read();
                }
                fis1.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        executor.shutdown();
    }
}
