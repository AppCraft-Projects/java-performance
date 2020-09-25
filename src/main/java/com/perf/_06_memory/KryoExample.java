package com.perf._06_memory;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class KryoExample {

    public static void main(String[] args) throws FileNotFoundException {
        Kryo kryo = new Kryo();
        kryo.register(SomeClass.class);

        SomeClass object = new SomeClass();
        object.value = "Hello Kryo!";

        Output output = new Output(new FileOutputStream("file.bin"));
        kryo.writeObject(output, object);
        output.close();

        Input input = new Input(new FileInputStream("file.bin"));
        SomeClass result = kryo.readObject(input, SomeClass.class);
        input.close();

        System.out.println(result);
    }

    static public class SomeClass {
        String value;

        @Override
        public String toString() {
            return "SomeClass{" +
                    "value='" + value + '\'' +
                    '}';
        }
    }
}
