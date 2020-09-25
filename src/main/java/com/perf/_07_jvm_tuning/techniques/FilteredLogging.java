package com.perf._07_jvm_tuning.techniques;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.perf.Utils.printRuntimeOf;

public class FilteredLogging {

    private static final Logger logger = LoggerFactory.getLogger(FilteredLogging.class);

    public static void main(String[] args) {

        printRuntimeOf(() -> {
            for (int i = 0; i < 1_000; i++) {
                logger.trace(notSoFastMethod());
            }
        });
        printRuntimeOf(() -> {
            for (int i = 0; i < 1_000; i++) {
                if (logger.isTraceEnabled()) {
                    logger.trace(notSoFastMethod());
                }
            }
        });
    }

    static String notSoFastMethod() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "not really";
    }
}
