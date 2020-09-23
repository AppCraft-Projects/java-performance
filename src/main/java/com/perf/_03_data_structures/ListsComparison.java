package com.perf._03_data_structures;

import com.perf.Utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@SuppressWarnings("ALL")
public class ListsComparison {

    public static void main(String[] args) {

        List<Long> nums = Utils.generateRandomNumbers(5_000_000)
                .boxed()
                .collect(Collectors.toList());

        ArrayList<Long> arrayList = new ArrayList<>(nums);
        LinkedList<Long> linkedList = new LinkedList<>(nums);

        System.out.println("Seek at front");
        System.out.printf("ArrayList %s%n", Utils.measureRuntimeOf(TimeUnit.NANOSECONDS, () -> {
            arrayList.get(0);
        }));
        System.out.printf("LinkedList %s%n", Utils.measureRuntimeOf(TimeUnit.NANOSECONDS, () -> {
            linkedList.get(0);
        }));
        System.out.println();

        System.out.println("Seek at Back");
        System.out.printf("ArrayList %s%n", Utils.measureRuntimeOf(TimeUnit.NANOSECONDS, () -> {
            arrayList.get(arrayList.size() - 1);
        }));
        System.out.printf("LinkedList %s%n", Utils.measureRuntimeOf(TimeUnit.NANOSECONDS, () -> {
            linkedList.get(linkedList.size() - 1);
        }));
        System.out.println();

        System.out.println("Seek at Index");
        System.out.printf("ArrayList %s%n", Utils.measureRuntimeOf(TimeUnit.NANOSECONDS, () -> {
            arrayList.get(2_500_000);
        }));
        System.out.printf("LinkedList %s%n", Utils.measureRuntimeOf(TimeUnit.NANOSECONDS, () -> {
            linkedList.get(2_500_000);
        }));
        System.out.println();

        System.out.println("Insert at Front");
        System.out.printf("ArrayList %s%n", Utils.measureRuntimeOf(TimeUnit.NANOSECONDS, () -> {
            arrayList.add(0, 1L);
        }));
        System.out.printf("LinkedList %s%n", Utils.measureRuntimeOf(TimeUnit.NANOSECONDS, () -> {
            linkedList.add(0, 1L);
        }));
        System.out.println();

        System.out.println("Insert at Back");
        System.out.printf("ArrayList %s%n", Utils.measureRuntimeOf(TimeUnit.NANOSECONDS, () -> {
            arrayList.add(1L);
        }));
        System.out.printf("LinkedList %s%n", Utils.measureRuntimeOf(TimeUnit.NANOSECONDS, () -> {
            linkedList.add(1L);
        }));
        System.out.println();

        System.out.println("Insert after an Item");
        System.out.printf("ArrayList %s%n", Utils.measureRuntimeOf(TimeUnit.NANOSECONDS, () -> {
            arrayList.add(2_500_000, 1L);
        }));
        System.out.printf("LinkedList %s%n", Utils.measureRuntimeOf(TimeUnit.NANOSECONDS, () -> {
            linkedList.add(2_500_000, 1L);
        }));
        System.out.println();

    }
}
