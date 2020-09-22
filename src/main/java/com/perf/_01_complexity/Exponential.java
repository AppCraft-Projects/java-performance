package com.perf._01_complexity;

public class Exponential {
    public static void main(String[] args) {

        fib(Integer.parseInt(args[0]));

    }

    public static int fib(int n) {
        if (n <= 1) {
            return n;
        } else {
            return fib(n - 1) + fib(n - 2);
        }
    }
}
