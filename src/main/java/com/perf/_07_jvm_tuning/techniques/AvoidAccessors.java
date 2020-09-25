package com.perf._07_jvm_tuning.techniques;

import com.perf.Utils.RunTime;
import com.perf._07_jvm_tuning.NoOpPrintStream;

import java.io.PrintStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.perf.Utils.measureRuntimeOf;

public class AvoidAccessors {

    public static void main(String[] args) {
        final List<PlainUser> plainUsers = Stream.iterate(1, i -> i + 1)
                .map(i -> new PlainUser("Name" + i, 10 + i))
                .limit(1_000_000)
                .collect(Collectors.toList());
        final List<AccessorUser> accessorUsers = Stream.iterate(1, i -> i + 1)
                .map(i -> new AccessorUser("Name" + i, 10 + i))
                .limit(1_000_000)
                .collect(Collectors.toList());

        PrintStream oldOut = System.out;

        System.setOut(new NoOpPrintStream(oldOut));

        RunTime accessor = measureRuntimeOf(() -> {
            accessorUsers.forEach(user -> {
                System.out.printf("Name: %s, Age: %d", user.getName(), user.getAge());
            });
        });
        RunTime plain = measureRuntimeOf(() -> {
            plainUsers.forEach(user -> {
                System.out.printf("Name: %s, Age: %d", user.name, user.age);
            });
        });


        System.setOut(oldOut);

        System.out.println(accessor);
        System.out.println(plain);
    }

    static class PlainUser {
        private String name;
        private int age;

        public PlainUser(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

    static class AccessorUser {
        private String name;
        private int age;

        public AccessorUser(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }
}
