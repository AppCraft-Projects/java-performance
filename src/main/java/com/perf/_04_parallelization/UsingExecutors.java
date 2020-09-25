package com.perf._04_parallelization;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

@SuppressWarnings({"DuplicatedCode", "TryWithIdenticalCatches"})
public class UsingExecutors {

    public static void main(String[] args) throws InterruptedException {

        simpleUsage();
        System.out.println();
        multipleUsage();
        System.out.println();
        mapReduceExample();

    }

    static void simpleUsage() {
        System.out.println("Simple usage");
        // create executor
        ExecutorService executor = Executors.newFixedThreadPool(4);

        // create task
        final Runnable task = () -> {
            System.out.println("Started task");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // if Future.cancel or ExecutorService.shutdown is called
                e.printStackTrace();
            }
        };

        System.out.println("Submitting task.");
        Future<?> future = executor.submit(task);
        System.out.println("Task submitted.");

        try {
            // Block while waiting for the result
            future.get();
            System.out.println("Finished task.");
        } catch (InterruptedException e) {
            // if Future.cancel or ExecutorService.shutdown is called
            System.out.println("Interrupted!");
            e.printStackTrace();
        } catch (ExecutionException e) {
            // If an Exception was thrown while running the code
            System.out.println("Execution failed!");
            e.printStackTrace();
        }

        // process won't exit if there are root references
        executor.shutdownNow();
    }

    static void multipleUsage() throws InterruptedException {
        System.out.println("Multiple usage");
        // create executor
        ExecutorService executor = Executors.newFixedThreadPool(4);

        // create task
        final Callable<Void> task = () -> {
            System.out.println("Started task");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // if Future.cancel or ExecutorService.shutdown is called
                e.printStackTrace();
            }
            System.out.println("Finished task");
            return null;
        };

        final List<Future<Void>> futures = executor.invokeAll(Arrays.asList(task, task, task, task));

        // Block while waiting for the result
        futures.forEach(future -> {
            try {
                future.get();
            } catch (InterruptedException e) {
                // if Future.cancel or ExecutorService.shutdown is called
                System.out.println("Interrupted!");
                e.printStackTrace();
            } catch (ExecutionException e) {
                // If an Exception was thrown while running the code
                System.out.println("Execution failed!");
                e.printStackTrace();
            }
        });
        System.out.println("Finished tasks.");

        // process won't exit if there are root references
        executor.shutdownNow();
    }

    static void mapReduceExample() throws InterruptedException {
        System.out.println("Map reduce");
        // create executor
        ExecutorService executor = Executors.newFixedThreadPool(4);
        Random random = new Random();

        // create task
        final Callable<Integer> task = () -> {
            System.out.println("Calculating next value...");
            return random.nextInt(Integer.MAX_VALUE);
        };

        Long sum = executor.invokeAll(Arrays.asList(task, task, task, task)).stream().map(future -> {
            Long result = null;
            try {
                System.out.println("Extracting value...");
                result = future.get().longValue();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return result;
        }).reduce(0L, Long::sum);

        System.out.printf("Sum is %d%n", sum);

        // process won't exit if there are root references
        executor.shutdownNow();
    }

}
