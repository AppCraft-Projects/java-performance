package com.perf._05_streaming;

import java.io.*;
import java.util.Scanner;

public class IOStreaming {

    public static void main(String[] args) throws IOException {
//        processInMemory();
        streamFile();
    }

    static void processInMemory() throws IOException {
        Scanner in = new Scanner(new FileReader("src/main/resources/bigfile"));
        StringBuilder sb = new StringBuilder();
        System.out.println("Reading file contents...");
        while(in.hasNext()) {
            sb.append(in.next());
        }
        in.close();
        FileWriter fileWriter = new FileWriter("src/main/resources/newfile");
        PrintWriter printWriter = new PrintWriter(fileWriter);
        System.out.println("Writing new file...");
        printWriter.print(sb.toString());
    }

    static void streamFile() throws IOException {
        final InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("bigfile");
        File file = new File("src/main/resources/newfile");
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file, false);
        byte[] buf = new byte[8192];
        int length;
        System.out.println("Processing file...");
        while ((length = is.read(buf)) > 0) {
            fos.write(buf, 0, length);
        }
        System.out.println("Processing complete.");
    }
}
