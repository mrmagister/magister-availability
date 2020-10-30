package standalone.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.body.FieldDeclaration;

import standalone.model.ContentHolder;
import standalone.model.DirExplorer;
import standalone.model.Type;

public class DeclarationsLines {

	private ArrayList<ContentHolder> fieldDeclarations = new ArrayList<ContentHolder>();
	
	public ArrayList<ContentHolder> getFieldDeclarations() {		
		return fieldDeclarations;
	}
	
	public DeclarationsLines() {
		this.fieldDeclarations = new ArrayList<ContentHolder>();
	}

	/**
	 * Code for find variable declarations in the source code
	 * @param projectDir
	 * @return ArrayList<ContentHolder>
	 */
	public ArrayList<ContentHolder> declarationsByLine(File projectDir) {
		
		new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
			
            try {    
            	
            	StaticJavaParser.parse(file).findAll(FieldDeclaration.class).forEach(field -> { 
            		field.getVariables().forEach(variable -> {
            			
            			String tempInit = "";
                        if(variable.getInitializer().isPresent()) {
                        	tempInit = variable.getInitializer().toString();
                        	
                        	if(tempInit.contains("Optional[")) {
                        		
                        		tempInit = tempInit.substring(("Optional[").length(), tempInit.length() - 1);
                        		
                        	}
                        	
                        }
                        System.out.println("@ @ " + file.toString());
                        fieldDeclarations.add(new ContentHolder(variable.getName().asString(), Type.declaration, variable.getBegin().get().line, tempInit));
                    });
                });
                      
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).explore(projectDir);
        return getFieldDeclarations();
    }
 	
}
