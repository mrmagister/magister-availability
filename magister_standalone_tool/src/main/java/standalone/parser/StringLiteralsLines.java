package standalone.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.google.common.base.Strings;

import standalone.model.DirExplorer;
import standalone.model.StringLiteralRepresentation;


public class StringLiteralsLines {

	private ArrayList<StringLiteralRepresentation> stringLiterals;
	
	public ArrayList<StringLiteralRepresentation> getStringLiterals() {		
		return stringLiterals;
	}
	
	public StringLiteralsLines() {
		this.stringLiterals = new ArrayList<StringLiteralRepresentation>();
	}

	public ArrayList<StringLiteralRepresentation> literalsByLine(File projectDir) {
    
		new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
            System.out.println(path);
            System.out.println(Strings.repeat("=", path.length()));
            try {
                StaticJavaParser.parse(file)
                        .findAll(StringLiteralExpr.class)
                        .forEach(expr -> stringLiterals.add(new StringLiteralRepresentation(expr.getBegin().get().line, expr.toString(), file)));
                System.out.println(); // empty line
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).explore(projectDir);
        return getStringLiterals();
    }

	
}
