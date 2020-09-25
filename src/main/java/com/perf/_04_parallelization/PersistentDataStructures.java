package com.perf._04_parallelization;

import kotlin.Pair;
import kotlinx.collections.immutable.PersistentList;
import kotlinx.collections.immutable.PersistentMap;
import kotlinx.collections.immutable.PersistentSet;

import static kotlinx.collections.immutable.ExtensionsKt.*;

@SuppressWarnings("unchecked")
public class PersistentDataStructures {

    public static void main(String[] args) {
        persistentMap();
        persistentSet();
        persistentList();
    }

    static void persistentMap() {
        final PersistentMap<Integer, String> map = persistentMapOf(new Pair<>(1, "foo"));

        // new structure is created
        final PersistentMap<Integer, String> derived = map.put(2, "xul");

        System.out.println(map);
        System.out.println(derived);
    }

    static void persistentSet() {
        final PersistentSet<Integer> set = persistentSetOf(1, 2, 3);

        // new structure is created
        final PersistentSet<Integer> derived = set.add(4);

        System.out.println(set);
        System.out.println(derived);
    }

    static void persistentList() {
        final PersistentList<Integer> list = persistentListOf(1, 2, 3);

        // new structure is created
        final PersistentList<Integer> derived = list.add(3);

        System.out.println(list);
        System.out.println(derived);
    }
}
