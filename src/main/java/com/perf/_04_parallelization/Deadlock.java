package com.perf._04_parallelization;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@SuppressWarnings("SameParameterValue")
public class Deadlock {

    private static Lock lock1 = new ReentrantLock(true);
    private static Lock lock2 = new ReentrantLock(true);

    public static void main(String[] args) {
        new Thread(Deadlock::dead1, "T1").start();
        new Thread(Deadlock::dead2, "T2").start();
    }

    public static void dead1() {
        lock1.lock();
        System.out.println("lock1 acquired, waiting to acquire lock2.");
        sleep(50);

        lock2.lock();
        System.out.println("lock2 acquired");

        System.out.println("executing first operation.");

        lock2.unlock();
        lock1.unlock();
    }

    public static void dead2() {
        lock2.lock();
        System.out.println("lock2 acquired, waiting to acquire lock1.");
        sleep(50);

        lock1.lock();
        System.out.println("lock1 acquired");

        System.out.println("executing second operation.");

        lock1.unlock();
        lock2.unlock();
    }

    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
