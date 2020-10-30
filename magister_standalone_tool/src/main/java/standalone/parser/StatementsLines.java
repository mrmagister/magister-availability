package standalone.parser;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.stmt.Statement;
import com.google.common.base.Strings;

import standalone.model.DirExplorer;

import java.io.File;
import java.io.IOException;

public class StatementsLines {

    public static void statementsByLine(File projectDir) {
        new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
            System.out.println(path);
            System.out.println(Strings.repeat("=", path.length()));
            try {
                StaticJavaParser.parse(file)
                        .findAll(Statement.class)
                        .forEach(statement -> System.out.println(" [Lines " + statement.getBegin().get().line
                                + " - " + statement.getEnd().get().line + " ] " + statement));
                System.out.println(); // empty line
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).explore(projectDir);
    }
    
    //method used for testing
    public static void main(String[] args) {
        File projectDir = new File("-=--------------------------------");
        statementsByLine(projectDir);
    }
    
}

