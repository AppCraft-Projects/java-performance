package com.perf._05_streaming;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamAPI {

    public static void main(String[] args) {

        System.out.println(Stream.of(1, 2, 3)
                .map(num -> num * 2)
                .collect(Collectors.toList()));
        // [2, 4, 6]

        System.out.println(Stream.of(1, 2, 3)
                .filter(num -> num > 1)
                .collect(Collectors.toList()));
        // [2, 3]

        System.out.println(Stream.of(Arrays.asList(1, 2), Arrays.asList(3, 4))
                .flatMap(Collection::stream)
                .collect(Collectors.toList()));
        // [1, 2, 3, 4]

        final Stream<Integer> intGen = Stream.iterate(1, item -> item + 1);

        System.out.println(intGen.skip(5)
                .limit(3)
                .collect(Collectors.toList()));
        // [6, 7, 8]

        Stream.of(1, 2, 3, 4, 5, 6)
                .filter(num -> {
                    System.out.println("filtering for greater than 3");
                    return num > 3;
                })
                .filter(num -> {
                    System.out.println("filtering for null");
                    return num != null;
                })
                .findFirst()
                .orElse(null);

        System.out.println(Stream.of(1, 2, 3, 4, 5, 6)
                .min(Integer::compareTo)
                .orElse(0));
        // 1

        System.out.println(Stream.of(1, 1, 2, 2, 3, 3)
                .distinct()
                .collect(Collectors.toList()));
        // [1, 2, 3]

        System.out.println(Stream.of(1, 2, 3)
                .reduce(0, Integer::sum));
        // 6

        System.out.println(Stream.of(1, 2, 3, 4, 5, 6)
                .map(Object::toString)
                .collect(Collectors.joining(", ")));
        // 1, 2, 3, 4, 5, 6

        System.out.println(Stream.of(1, 2, 3, 4, 5, 6)
                .collect(Collectors.summarizingInt(num -> num)));
        // IntSummaryStatistics{count=6, sum=21, min=1, average=3, max=6}

        System.out.println(Stream.of(1, 2, 3, 4, 5, 6)
                .collect(Collectors.partitioningBy(num -> num % 2 == 0)));
        // {false=[1, 3, 5], true=[2, 4, 6]}

        System.out.println(Stream.of(1, 2, 3, 4, 5, 6)
                .collect(Collectors.groupingBy(num -> num % 3)));
        // {0=[3, 6], 1=[1, 4], 2=[2, 5]}
    }
}
