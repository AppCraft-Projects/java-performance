package com.perf._01_complexity;

import java.util.Arrays;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class LogN {
    public static void main(String[] args) {

        Integer[] numbers = {1, 2, 3, 4, 5, 6};

        Arrays.binarySearch(numbers, 5); // O(log n)
    }
}
