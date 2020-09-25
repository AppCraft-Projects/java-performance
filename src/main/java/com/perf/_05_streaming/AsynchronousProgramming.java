package com.perf._05_streaming;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AsynchronousProgramming {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println(calculateAsync().get());
        System.out.println(supplyAsync().get());

        supplyAsync()
                .thenAccept(System.out::println) // consume result (terminal operation)
                .get(); // block

        supplyAsync()
                .thenApply(str -> str + "Batman!") // transform result (like Stream.map)
                .thenAccept(System.out::println)
                .get();

        supplyAsync()
                .thenCompose(str -> { // async compose (like Stream.flatMap)
                    return CompletableFuture.supplyAsync(() -> str + "Batman!");
                })
                .thenAccept(System.out::println)
                .get();

        final CompletableFuture<String> f0 = CompletableFuture.supplyAsync(() -> "Wat, ");
        final CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> "Wat, ");
        final CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> "Batman!");

        CompletableFuture.allOf(f0, f1, f2).get(); // awaits all (similar to a terminal operation)

        final CompletableFuture<String> f3 = CompletableFuture.supplyAsync(() -> "Wat, ");
        final CompletableFuture<String> f4 = CompletableFuture.supplyAsync(() -> "Wat, ");
        final CompletableFuture<String> f5 = CompletableFuture.supplyAsync(() -> "Batman!");

        System.out.println(Stream.of(f3, f4, f5)
                .map(CompletableFuture::join)
                .collect(Collectors.joining("")));

    }

    // Async by hand
    public static Future<String> calculateAsync() {
        CompletableFuture<String> result = new CompletableFuture<>();
        new Thread(() -> {
            result.complete("Hello.");
        }).start();
        return result;
    }

    // Async using ThreadPool
    public static CompletableFuture<String> supplyAsync() {
        // offloads to a ThreadPool
        return CompletableFuture.supplyAsync(() -> "Hey, ");
    }
}
