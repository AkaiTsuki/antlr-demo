package io.akaitsuki.antlr;

import io.akaitsuki.antlr.java.UppercaseMethodListener;
import io.akaitsuki.grammars.arrayinit.ArrayInitLexer;
import io.akaitsuki.grammars.arrayinit.ArrayInitParser;
import io.akaitsuki.grammars.java.Java8Lexer;
import io.akaitsuki.grammars.java.Java8Parser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class Main {
    public static void main(String[] args) {
        testArrayInitParser();
    }

    private static void testArrayInitParser() {
        String c1 = "{1,2,3}";
        String c2 = "{1, {2,3}, 5}";
        String c3 = "{1, {2.3}, 5}";

        ArrayInitLexer lexer = new ArrayInitLexer(CharStreams.fromString(c2));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ArrayInitParser parser = new ArrayInitParser(tokens);
        ParseTree tree = parser.init();
        System.out.println(tree.toStringTree(parser));
    }

    private static void testJava8Parser() {
        String javaClassContent = "public class SampleClass { void DoSomething(){} }";
        Java8Lexer lexer = new Java8Lexer(CharStreams.fromString(javaClassContent));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        Java8Parser parser = new Java8Parser(tokens);
        ParseTree tree = parser.compilationUnit();
        ParseTreeWalker walker = new ParseTreeWalker();
        UppercaseMethodListener listener= new UppercaseMethodListener();
        walker.walk(listener, tree);

        System.out.println(listener.getErrors());
    }
}
