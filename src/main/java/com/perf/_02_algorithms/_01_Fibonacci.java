package com.perf._02_algorithms;

import java.util.HashMap;
import java.util.Map;

public class _01_Fibonacci {

    public static void main(String[] args) {
        System.out.printf("%d", fibDyn(100));
    }

    static int fib(int n) {
        if (n <= 1) {
            return n;
        } else {
            return fib(n - 1) + fib(n - 2);
        }
    }

    static Map<Integer, Long> lookup = new HashMap<>();

    static long fibDyn(int n) {
        if(lookup.containsKey(n)) {
            return lookup.get(n);
        } else {
            long result;
            if (n <= 1) {
                result = n;
            } else {
                result = fibDyn(n - 1) + fibDyn(n - 2);
            }
            lookup.put(n, result);
            return result;
        }
    }
}
