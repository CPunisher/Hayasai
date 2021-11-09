import java.io.*;

public class MiniSysYTester {
    private static final ProcessBuilder compilerBuilder = new ProcessBuilder()
            .command("java", "-classpath", ".:lib/*:out", "com.cpunisher.hayasai.Main")
            .redirectErrorStream(true);
    private static final ProcessBuilder lliBuilder = new ProcessBuilder()
            .command("lli")
            .redirectErrorStream(true);

    public static void main(String[] args) throws Exception {
        File sy = new File(args[0]);
        FileInputStream fileInputStream = new FileInputStream(sy);

        Process compiler = compilerBuilder.start();
        InputStream compilerInput = compiler.getInputStream();
        OutputStream compilerOutput = compiler.getOutputStream();

        Process interpreter = lliBuilder.start();
        InputStream lliInput = interpreter.getInputStream();
        OutputStream lliOutput = interpreter.getOutputStream();

        fileInputStream.transferTo(compilerOutput);
        fileInputStream.close();
        compilerOutput.close();

        compilerInput.mark(1024);
        compilerInput.transferTo(System.out);
        compilerInput.reset();
        compilerInput.transferTo(lliOutput);
        compilerInput.close();
        lliOutput.close();

        int compilerCode = compiler.waitFor();
        if (compilerCode != 0) {
            System.out.println("Compile failed.");
            return;
        }
        System.out.println("Compile successfully.");

        lliInput.transferTo(System.out);
        lliInput.close();
        int lliCode = interpreter.waitFor();
        System.out.println("lli return with " + lliCode);
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
