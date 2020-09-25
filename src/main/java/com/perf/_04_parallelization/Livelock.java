package com.perf._04_parallelization;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@SuppressWarnings("ALL")
public class Livelock {

    private static Lock lock1 = new ReentrantLock(true);
    private static Lock lock2 = new ReentrantLock(true);

    public static void main(String[] args) {
        new Thread(Livelock::live1, "T1").start();
        new Thread(Livelock::live2, "T2").start();
    }

    public static void live1() {
        while (true) {
            if (lock1.tryLock()) {
                System.out.println("lock1 acquired, trying to acquire lock2.");

                sleep(50);

                if (lock2.tryLock()) {
                    System.out.println("lock2 acquired.");
                    System.out.println("executing first operation.");
                    lock2.unlock();
                    lock1.unlock();
                    break;
                } else {
                    System.out.println("cannot acquire lock2, releasing lock1.");
                    lock1.unlock();
                }
            }
        }
    }

    public static void live2() {
        while (true) {
            if (lock2.tryLock()) {
                System.out.println("lock2 acquired, trying to acquire lock2.");

                sleep(50);

                if (lock1.tryLock()) {
                    System.out.println("lock1 acquired.");
                    System.out.println("executing second operation.");
                    lock1.unlock();
                    lock2.unlock();
                    break;
                } else {
                    System.out.println("cannot acquire lock1, releasing lock1.");
                    lock2.unlock();
                }
            }
        }
    }

    private static boolean tryLock(Lock lock, int timeoutMs) {
        try {
            return lock.tryLock(timeoutMs, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted");
        }
    }

    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
