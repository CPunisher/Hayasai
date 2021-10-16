package com.cpunisher.hayasai.util;

import java.io.*;

public class IOUtils {

    public static String readFromStream(InputStream stream) throws IOException {
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
