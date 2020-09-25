package com.perf;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;

public class Utils {

    private static final Random random = new Random();
    private static final DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance();
    private static final DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();

    static {
        symbols.setGroupingSeparator('.');
        formatter.setDecimalFormatSymbols(symbols);
    }

    public static LongStream generateRandomNumbers(int count) {
        return random.longs(count);
    }

    public static void printRuntimeOf(Runnable fn) {
        System.out.println(measureRuntimeOf(fn));
    }

    public static void printRuntimeOf(TimeUnit timeUnit, Runnable fn) {
        System.out.println(measureRuntimeOf(timeUnit, fn));
    }

    public static RunTime measureRuntimeOf(TimeUnit timeUnit, Runnable fn) {
        long start = System.nanoTime();
        fn.run();
        long end = System.nanoTime();

        return new RunTime(
                timeUnit.convert(end - start, TimeUnit.NANOSECONDS),
                timeUnit
        );
    }

    public static RunTime measureRuntimeOf(Runnable fn) {
        return measureRuntimeOf(TimeUnit.MILLISECONDS, fn);
    }

    public static class RunTime {
        public final Long time;
        public final TimeUnit timeUnit;

        public RunTime(Long time, TimeUnit timeUnit) {
            this.time = time;
            this.timeUnit = timeUnit;
        }

        @Override
        public String toString() {
            String tu = null;
            switch (timeUnit) {
                case NANOSECONDS:
                    tu = "ns";
                    break;
                case MICROSECONDS:
                    tu = "us";
                    break;
                case MILLISECONDS:
                    tu = "ms";
                    break;
                case SECONDS:
                    tu = "s";
                    break;
                case MINUTES:
                    tu = "m";
                    break;
                case HOURS:
                    tu = "h";
                    break;
                case DAYS:
                    tu = "d";
                    break;
            }
            return String.format("%s%s", formatter.format(time), tu);
        }
    }
}
