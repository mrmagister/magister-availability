package standalone.model;

import java.io.File;

public class StringLiteralRepresentation {

	private int lineNumber;
	private String contents;
	private File file;
	
	public StringLiteralRepresentation (int lineNumber, String contents, File file) {
		
		this.lineNumber = lineNumber;
		this.contents = contents;
		this.file = file;
		
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public String getContents() {
		return contents;
	}

	public File getFile() {
		return file;
	}
	
}
