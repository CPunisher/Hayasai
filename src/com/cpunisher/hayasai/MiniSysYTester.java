package com.cpunisher.hayasai;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class MiniSysYTester {
    private static final ProcessBuilder compilerBuilder = new ProcessBuilder()
            .command("java", "-classpath", ".:lib/*:out", "com.cpunisher.hayasai.Main");

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            // Test all
            int tot = 0, success = 0;
            Set<String> failSet = new HashSet<>();
            File testDir = new File("test");
            for (File subDir : Objects.requireNonNull(testDir.listFiles())) {
                for (File testcase : Objects.requireNonNull(subDir.listFiles())) {
                    String filename = testcase.getPath();
                    if (filename.endsWith(".sy")) {
                        boolean res = singleFileTest(filename);
                        if (res) success++;
                        else failSet.add(filename);
                        tot++;
                    }
                }
            }
            System.out.println("Test end. Pass: " + success + "/" + tot);
            System.out.println("Fail testcases: ");
            failSet.forEach(System.out::println);
        } else {
            // Single test
            String filename = args[0];
            singleFileTest(filename);
        }
    }

    private static boolean singleFileTest(String filename) throws IOException, InterruptedException {
        File sy = new File(filename);
        FileInputStream fileInputStream = new FileInputStream(sy);

        Process compiler = compilerBuilder.start();
        OutputStream compilerOutput = compiler.getOutputStream();

        fileInputStream.transferTo(compilerOutput);
        fileInputStream.close();
        compilerOutput.close();

        int compilerCode = compiler.waitFor();
        if (compilerCode != 0) {
            System.out.println("[" + filename + "] compile failed.");
            return false;
        }
        System.out.println("[" + filename + "] compile successfully.");
        return true;
    }
}
