package com.perf._01_complexity;

import java.util.Arrays;

public class N {
    public static void main(String[] args) {

        Arrays.asList(1, 2, 3, 4, 5, 6).forEach(System.out::println); // O(n)
    }
}
