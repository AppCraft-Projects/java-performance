package com.perf._04_parallelization;

import com.perf.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static com.perf.Utils.generateRandomNumbers;

@SuppressWarnings("ALL")
public class ConcurrentDataStructures {

    public static void main(String[] args) throws InterruptedException {
        concurrentMapExample();
        blockingQueueExample();
        copyOnWriteArrayListExample();
    }

    static void concurrentMapExample() {

        final ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();

        // simple usage
        map.get(1);
        map.put(1, "foo");

        // atomic put
        map.putIfAbsent(2, "xul");
        map.replace(2, "xul", "wom");
        map.replace(2, "foo");

        // atomic update
        map.compute(1, (key, value) -> "wom");

        // don't modify while in compute
        map.compute(1, (key, value) -> {
            map.put(1, "xul"); // OH NO
            return "wom";
        });

        // atomic get + put
        map.computeIfAbsent(1, key -> key.toString());

        // atomic get + put with old value
        map.computeIfPresent(1, (key, value) -> key.toString());

    }

    static void blockingQueueExample() throws InterruptedException {
        final BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

        // non-blocking add boolean return
        queue.offer(1);

        // non-blocking add throws exception
        queue.add(1);

        // blocking add
        queue.put(1);

        // non-blocking get(0)
        queue.peek();

        // non-blocking remove(0)
        queue.poll();

        // blocking remove(0)
        queue.take();

        // removes elements from start to target list
        queue.drainTo(new ArrayList<>());
        queue.drainTo(new ArrayList<>(), 10);
    }

    static void copyOnWriteArrayListExample() {

        final LongStream nums = generateRandomNumbers(1000);

        final CopyOnWriteArrayList<Integer> cowList = new CopyOnWriteArrayList(nums.boxed().collect(Collectors.toList()));
        final List<Integer> list = new ArrayList(cowList);

        System.out.println(Utils.measureRuntimeOf(() -> {
            for (int i = 0; i < 10_000; i++) {
                cowList.add(i);
            }
        }));

        System.out.println(Utils.measureRuntimeOf(() -> {
            for (int i = 0; i < 10_000; i++) {
                list.add(i);
            }
        }));

    }

}
