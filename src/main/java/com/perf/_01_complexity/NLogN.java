package com.perf._01_complexity;

public class NLogN {
    public static void main(String[] args) {

        // insertion sort
        Integer[] numbers = {6, 1, 3, 8, 3, 2};

        for (int i = 1; i < numbers.length; ++i) { // O(n^2)
            int key = numbers[i];
            int j = i - 1;
            while (j >= 0 && numbers[j] > key) {
                numbers[j + 1] = numbers[j];
                j = j - 1;
            }
            numbers[j + 1] = key;
        }
    }
}
