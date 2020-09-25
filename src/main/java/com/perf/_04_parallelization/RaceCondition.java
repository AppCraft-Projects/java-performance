package com.perf._04_parallelization;

public class RaceCondition {

    public static void main(String[] args) {
        Counter counter = new Counter();
        Thread t1 = new Thread(counter, "Thread-1");
        Thread t2 = new Thread(counter, "Thread-2");
        Thread t3 = new Thread(counter, "Thread-3");
        t1.start();
        t2.start();
        t3.start();
    }

    static class Counter implements Runnable {

        private int counter = 0;

        public void increment() {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            counter++;
        }

        public void decrement() {
            counter--;
        }

        public int getValue() {
            return counter;
        }

        @Override
        public void run() {
            this.increment();
            System.out.printf("Value for Thread After increment %s %d %n",
                    Thread.currentThread().getName(), this.getValue());
            this.decrement();
            System.out.printf("Value for Thread After decrement %s %d %n",
                    Thread.currentThread().getName(), this.getValue());
        }
    }
}
