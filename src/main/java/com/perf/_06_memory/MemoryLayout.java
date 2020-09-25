package com.perf._06_memory;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

/**
 * Source: https://www.baeldung.com/java-memory-layout
 */
public class MemoryLayout {

    public static void main(String[] args) {
        printDetails();
        printClassDetails();
        printPaddingDetails();
        printFieldPacking();
        printLocking();
        printGcInfo();
    }

    static void printDetails() {
        System.out.println(VM.current().details());
        //# Objects are 8 bytes aligned.
        // reference, boolean, byte, short, char, int, float, long, double
        //# Field sizes by type: 4, 1, 1, 2, 2, 4, 4, 8, 8 [bytes]
        //# Array element sizes: 4, 1, 1, 2, 2, 4, 4, 8, 8 [bytes]
    }

    static void printClassDetails() {
        System.out.println(ClassLayout.parseClass(Hello.class).toPrintable());
        // OFFSET  SIZE               TYPE DESCRIPTION                               VALUE
        // 0      12                  (object header)                                N/A
        // 12     4                   java.lang.String Hello.world                   N/A
        // Instance size: 16 bytes
        // Space losses: 0 bytes internal + 0 bytes external = 0 bytes total
    }

    static void printPaddingDetails() {
        System.out.println(ClassLayout.parseClass(PaddedLong.class).toPrintable());
        // OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
        // 0       12     (object header)                                N/A
        // 12      4      (alignment/padding gap)
        // 16      8      long PaddedLong.someLong                       N/A
        // Instance size: 24 bytes
        // Space losses: 4 bytes internal + 0 bytes external = 4 bytes total
    }

    static void printFieldPacking() {
        System.out.println(ClassLayout.parseClass(User.class).toPrintable());
        // OFFSET  SIZE      TYPE DESCRIPTION                               VALUE
        // 0       12        (object header)                                N/A
        // 12      4         int User.id                                    N/A
        // 16      2         short User.age                                 N/A
        // 18      2         char User.initial                              N/A
        // 20      1         boolean User.likesProgramming                  N/A
        // 21      1         boolean User.anonymous                         N/A
        // 22      2         (loss due to the next object alignment)
        // Instance size: 24 bytes
        // Space losses: 0 bytes internal + 2 bytes external = 2 bytes total
    }

    static void printLocking() {
        System.out.println(ClassLayout.parseInstance(new Hello()).toPrintable());
        // OFFSET  SIZE     TYPE DESCRIPTION                VALUE
        // 0       4        (object header)                 05 00 00 00 (00000101 00000000 00000000 00000000) (5)
        // 4       4        (object header)                 00 00 00 00 (00000000 00000000 00000000 00000000) (0)
        // 8       4        (object header)                 d6 6c 17 00 (11010110 01101100 00010111 00000000) (1535190)
        // 12       4       java.lang.String Hello.world    null
        // Instance size: 16 bytes
        // Space losses: 0 bytes internal + 0 bytes external = 0 bytes total
        Hello hello = new Hello();
        synchronized (hello) {
            System.out.println(ClassLayout.parseInstance(hello).toPrintable());
        }
        // OFFSET  SIZE     TYPE DESCRIPTION                VALUE
        // 0       4        (object header)                 05 a8 08 03 (00000101 10101000 00001000 00000011) (50898949)
        // 4       4        (object header)                 1f 02 00 00 (00011111 00000010 00000000 00000000) (543)
        // 8       4        (object header)                 d6 6c 17 00 (11010110 01101100 00010111 00000000) (1535190)
        // 12      4        java.lang.String Hello.world    null
        // Instance size: 16 bytes
        // Space losses: 0 bytes internal + 0 bytes external = 0 bytes total
    }

    static volatile Object consumer;

    static void printGcInfo() {

        Object instance = new Object();
        long lastAddr = VM.current().addressOf(instance);
        ClassLayout layout = ClassLayout.parseInstance(instance);

        for (int i = 0; i < 10_000; i++) {
            long currentAddr = VM.current().addressOf(instance);
            if (currentAddr != lastAddr) {
                System.out.println(layout.toPrintable());
            }

            for (int j = 0; j < 10_000; j++) {
                consumer = new Object();
            }

            lastAddr = currentAddr;
        }
        // 0d 00 00 00 (00001101 00000000 00000000 00000000) (13)
        //              ^^^^^^^^
        // 15 00 00 00 (00010101 00000000 00000000 00000000) (21)
        //              ^^^^^^^^
        // 1d 00 00 00 (00011101 00000000 00000000 00000000) (29)
        //              ^^^^^^^^
        // 25 00 00 00 (00100101 00000000 00000000 00000000) (37)
        //              ^^^^^^^^
        // 2d 00 00 00 (00101101 00000000 00000000 00000000) (45)
        //              ^^^^^^^^
        // 35 00 00 00 (00110101 00000000 00000000 00000000) (53)
        //              ^^^^^^^^
    }

    static class Hello {
        String world;
    }

    static class PaddedLong {
        private long someLong;
    }

    static class User {
        private short age;
        private char initial;
        private boolean likesProgramming;
        private int id;
        private boolean anonymous;
    }
}
