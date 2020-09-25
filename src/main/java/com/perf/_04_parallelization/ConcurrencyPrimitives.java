package com.perf._04_parallelization;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@SuppressWarnings({"TryWithIdenticalCatches", "UnusedAssignment"})
public class ConcurrencyPrimitives {

    private static final Map<String, String> rwMap = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
//        atomic();
//        countDownLatch();
//        lockIncrement();
//        complexLock();
//        readWriteLock();
//        condition();
    }

    static void atomic() {
        final AtomicInteger ai = new AtomicInteger(1);

        // tries to set with expected and new value
        boolean success = ai.compareAndSet(1, 2);

        // adds delta to current value
        ai.addAndGet(2);
        ai.getAndAdd(2);

        // inc/dec
        ai.incrementAndGet();
        ai.decrementAndGet();

        // sets value
        ai.getAndSet(5);
        ai.set(5);
    }

    static void countDownLatch() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);

        ExecutorService executor = Executors.newFixedThreadPool(6);

        // create task
        final Callable<Void> task = () -> {
            latch.countDown();
            Thread.sleep(1000);
            System.out.println("Task finished");
            return null;
        };

        System.out.println("Starting tasks");

        final List<Future<Void>> futures = executor.invokeAll(Arrays.asList(task, task, task, task, task, task));

        latch.await(1, TimeUnit.SECONDS);
        System.out.println("Awaited...");

        futures.forEach(future -> {
            try {
                future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        System.out.println("Finished");
        executor.shutdownNow();
    }

    static void lockIncrement() {
        // mutex
        // reentrant == can (re)claim without blocking itself
        final ReentrantLock lock = new ReentrantLock(true);

        int count = 0;

        // increment
        lock.lock(); // blocks if lock is held
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }

    static void complexLock() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        ReentrantLock lock = new ReentrantLock();

        executor.submit(() -> {
            lock.lock();
            try {
                Thread.sleep(1);
            } catch (InterruptedException ignored) {
            } finally {
                lock.unlock();
            }
        });

        executor.submit(() -> {
            System.out.println("Locked: " + lock.isLocked());
            System.out.println("Held by me: " + lock.isHeldByCurrentThread());
            boolean locked = lock.tryLock();
            System.out.println("Lock acquired: " + locked);
        });
        executor.shutdownNow();
    }

    static void readWriteLock() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        ReadWriteLock lock = new ReentrantReadWriteLock();

        CountDownLatch latch = new CountDownLatch(2);
        CountDownLatch finished = new CountDownLatch(1);

        // locks reads as well while writing
        executor.submit(() -> {
            System.out.println("Write locking");
            lock.writeLock().lock();
            try {
                latch.await(1, TimeUnit.SECONDS);
                rwMap.put("foo", "bar");
                System.out.println("Write completed");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.writeLock().unlock();
                finished.countDown();
            }
        });

        Runnable readTask = () -> {
            latch.countDown();
            System.out.println("Trying to read lock...");
            lock.readLock().lock();
            System.out.println("Read lock acquired.");
            try {
                System.out.println(rwMap.get("foo"));
            } finally {
                System.out.println("Releasing read lock...");
                lock.readLock().unlock();
            }
        };

        // wait until write lock is released
        // then executed in parallel
        executor.submit(readTask);
        executor.submit(readTask);

        // necessary to prevent premature termination
        finished.await(1, TimeUnit.SECONDS);
        executor.shutdownNow();
    }


    static void condition() {
        Queue<Integer> queue = new LinkedList<>();
        ReentrantLock lock = new ReentrantLock();
        Condition cnd = lock.newCondition();
        final int size = 5;

        new Producer(lock, cnd, queue, size).start();
        new Consumer(lock, cnd, queue).start();
    }

    static class Producer extends Thread {

        ReentrantLock lock;
        Condition cnd;
        Queue<Integer> queue;
        int size;

        public Producer(ReentrantLock lock, Condition cnd, Queue<Integer> queue, int size) {
            this.lock = lock;
            this.cnd = cnd;
            this.queue = queue;
            this.size = size;
        }

        public void run() {
            for (int i = 0; i < 10; i++) {
                lock.lock();
                while (queue.size() == size) {
                    try {
                        cnd.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                queue.add(i);
                System.out.printf("Produced : %d%n", i);
                cnd.signal();
                lock.unlock();
            }
        }

    }

    static class Consumer extends Thread {

        ReentrantLock lock;
        Condition cnd;
        Queue<Integer> queue;


        public Consumer(ReentrantLock lock, Condition cnd, Queue<Integer> queue) {
            this.lock = lock;
            this.cnd = cnd;
            this.queue = queue;
        }

        public void run() {
            for (int i = 0; i < 10; i++) {
                lock.lock();
                while (queue.size() < 1) {
                    try {
                        cnd.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.printf("Consumed : %d%n", queue.remove());
                cnd.signal();
                lock.unlock();
            }
        }
    }
}
