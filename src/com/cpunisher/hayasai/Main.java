package com.cpunisher.hayasai;

import com.cpunisher.hayasai.util.IOUtils;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String input = IOUtils.readFromStream(System.in);
        HayasaiFrontend frontend = new HayasaiFrontend();
        frontend.visitAst(input);
        frontend.check();
        frontend.passAll();
        frontend.generateCode();
    }
}
