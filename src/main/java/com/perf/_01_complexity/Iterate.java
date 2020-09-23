package com.perf._01_complexity;

import java.util.List;

public class Iterate {

    public static void main(String[] args) {
    
    }

    public void iterate(List<String> names) {
        System.out.println("Iterating over names.");
        System.out.printf("Name count: %d%n", names.size());
        names.forEach(System.out::println);
    }
}
