package io.akaitsuki.antlr;

import io.akaitsuki.antlr.java.UppercaseMethodListener;
import io.akaitsuki.grammars.arrayinit.ArrayInitLexer;
import io.akaitsuki.grammars.arrayinit.ArrayInitParser;
import io.akaitsuki.grammars.java.Java8Lexer;
import io.akaitsuki.grammars.java.Java8Parser;
import io.akaitsuki.grammars.myquery.MyQueryLexer;
import io.akaitsuki.grammars.myquery.MyQueryParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class Main {
    public static void main(String[] args) {
        testMyQuery();
    }

    private static void testArrayInitParser() {
        String c1 = "{1,2,3}";
        String c2 = "{1, {2,3}, 5}";
        String c3 = "{1, {2.3}, 5}";

        ArrayInitLexer lexer = new ArrayInitLexer(CharStreams.fromString(c3));
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

    private static void testMyQuery() {
        String query = "{\n" +
                "    \"select\": [\"pid\", \"pv_sum\"],\n" +
                "    \"from\":\"ads_pid_effect\",\n" +
                "    \"where\": [\n" +
                "        {\n" +
                "            \"key\": \"media\",\n" +
                "            \"value\": \"360\",\n" +
                "            \"op\": \"=\"\n" +
                "        },{\n" +
                "            \"key\": \"pv\",\n" +
                "            \"value\": \"0\",\n" +
                "            \"op\" : \">=\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"groupBy\": [\"pid\"]\n" +
                "}";
        System.out.println(query);
        MyQueryLexer lexer = new MyQueryLexer(CharStreams.fromString(query));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MyQueryParser parser = new MyQueryParser(tokens);
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object o, int i, int i1, String s, RecognitionException e) {
                throw new RuntimeException(e);
            }
        });
        ParseTree tree = parser.query();
        System.out.println(tree.toStringTree(parser));
    }
}
