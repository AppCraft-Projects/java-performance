package com.perf._07_jvm_tuning;

import java.io.OutputStream;
import java.io.PrintStream;

public class NoOpPrintStream extends PrintStream {

    public NoOpPrintStream(OutputStream out) {
        super(out, true);
    }

    @Override
    public void print(String s) {
    }
}
