package io.akaitsuki.antlr;

import io.akaitsuki.grammar.Java8Lexer;
import io.akaitsuki.grammar.Java8Parser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import static com.sun.org.apache.xerces.internal.util.PropertyState.is;

public class Main {
    public static void main(String[] args) {
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
