package standalone.model;

public class ContentHolder {

	private String content; 
	private Type type; 
	private int line;
	private String initValue;
	
	public ContentHolder(String content, Type type, int line, String initValue) {
		
		this.content = content;
		this.type = type;
		this.line = line;
		this.initValue = initValue;
		
	}

	public String getContent() {
		return content;
	}

	public Type getType() {
		return type;
	}

	public int getLine() {
		return line;
	}
	
	public String getInitValue() {
		return initValue;
	}
	
	
	
	
}
