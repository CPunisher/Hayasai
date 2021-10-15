package com.cpunisher.hayasai;

import com.cpunisher.hayasai.frontend.MiniSysYLexer;
import com.cpunisher.hayasai.frontend.MiniSysYParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class Main {
    public static void main(String[] args) {
        String input = "int main() { return 123; }";

        CharStream charStream = CharStreams.fromString(input);
        MiniSysYLexer lexer = new MiniSysYLexer(charStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        MiniSysYParser parser = new MiniSysYParser(tokenStream);
        ParseTree tree = parser.compUnit();
        System.out.println(tree.toStringTree(parser));
    }
}
