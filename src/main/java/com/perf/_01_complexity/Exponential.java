package com.perf._01_complexity;

public class Exponential {

    public static void main(String[] args) {
        fib(10);
    }

    public static int fib(int n) {
        if (n <= 1) {
            return n;
        } else {
            return fib(n - 1) + fib(n - 2);
        }
    }
}
