import java.io.*;
import java.util.Objects;

public class MiniSysYTester {
    public static void main(String[] args) throws Exception {
        File testDir = new File("test");
        ProcessBuilder processBuilder = new ProcessBuilder()
                .command("java", "-classpath", ".:lib/*:out", "com.cpunisher.hayasai.Main")
                .redirectErrorStream(true);

        if (testDir.exists() && testDir.isDirectory()) {
            File[] testFiles = testDir.listFiles((dir, name) -> name.startsWith("lab") && name.endsWith(".sy"));
            for (File testCase : Objects.requireNonNull(testFiles)) {
                String fileName = testCase.getName();
                Process process = processBuilder.start();

                // 跑测试
                FileInputStream testFileInput = new FileInputStream(testCase);
                OutputStream outputStream = process.getOutputStream();
                testFileInput.transferTo(outputStream);
                outputStream.close();

                int exitCode = process.waitFor();

                // 获取答案
                String answer = null;
                File answerFile = new File("test/" + fileName.substring(0, fileName.lastIndexOf(".sy")) + ".ll");
                if (answerFile.exists()) {
                    answer = readFromStream(new FileInputStream(answerFile));
                }

                InputStream inputStream = process.getInputStream();
                String out = readFromStream(inputStream);
                if ((answer == null && exitCode != 0) || out.equals(answer)) {
                    System.out.println(fileName + " pass.");
                } else {
                    System.out.println(fileName + " fail with your output: ");
                    System.out.println(out);
                }
            }
        } else {
            System.out.println("Can't find testcases.");
        }
    }

    private static String readFromStream(InputStream stream) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append('\n');
        }
        return stringBuilder.toString().trim();
    }
}
