package com.perf._03_data_structures;

import com.perf.Utils;

import java.util.*;
import java.util.stream.Collectors;

import static com.perf.Utils.measureRuntimeOf;

@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
public class SetOperations {

    public static void main(String[] args) {

        List<Long> numList0 = Utils.generateRandomNumbers(50_000)
                .boxed()
                .collect(Collectors.toList());
        List<Long> numList1 = Utils.generateRandomNumbers(50_000)
                .boxed()
                .collect(Collectors.toList());

        Set<Long> numSet0 = new HashSet<>(numList0);
        Set<Long> numSet1 = new HashSet<>(numList1);

        List<Long> listInter = new ArrayList<>(numList0);
        Set<Long> setInter = new HashSet<>(numSet0);

        System.out.println("Intersection");
        System.out.printf("List: %s%n", measureRuntimeOf(() -> listInter.removeIf(num -> !numList1.contains(num))));
        System.out.printf("Set: %s%n", measureRuntimeOf(() -> setInter.retainAll(numSet1)));
        System.out.println();

        List<Long> listUnion = new ArrayList<>(numList0);
        Set<Long> setUnion = new HashSet<>(numSet0);

        System.out.println("Union");
        System.out.printf("List: %s%n", measureRuntimeOf(() -> {
            for (Long next : numList1) {
                if (!listUnion.contains(next)) {
                    listUnion.add(next);
                }
            }
        }));
        System.out.printf("Set: %s%n", measureRuntimeOf(() -> setUnion.addAll(numSet1)));
        System.out.println();

        List<Long> listDiff = new ArrayList<>(numList0);
        Set<Long> setDiff = new HashSet<>(numSet0);

        System.out.println("Difference");
        System.out.printf("List: %s%n", measureRuntimeOf(() -> listDiff.removeAll(numList1)));
        System.out.printf("Set: %s%n", measureRuntimeOf(() -> setDiff.removeAll(numSet1)));
        System.out.println();
    }
}
