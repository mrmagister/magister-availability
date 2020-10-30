package standalone.model;

import java.util.ArrayList;

public class FileRepresentation {
	
	private String name;
	private ArrayList<String> contents;
	
	
	public FileRepresentation(String name, ArrayList<String> contents) {
		
		this.name = name;
		this.contents = contents;
		
	}


	public String getName() {
		return name;
	}


	public ArrayList<String> getContents() {
		return contents;
	}	

}
