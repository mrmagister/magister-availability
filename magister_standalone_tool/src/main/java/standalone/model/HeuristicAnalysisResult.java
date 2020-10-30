package standalone.model;

import java.util.ArrayList;

public class HeuristicAnalysisResult {
	
	private HeuristicType heuristic;
	private String content;
	private Type type;
	private int line;
	
	
	public HeuristicAnalysisResult(HeuristicType heuristic, String content, Type type, int line) {
		
		this.heuristic = heuristic;
		this.content = content;
		this.type = type;
		this.line = line;
		
	}
	
	public HeuristicAnalysisResult(HeuristicType heuristic, ContentHolder content) {
		
		this.heuristic = heuristic;
		this.content = content.getContent();
		this.type = content.getType();
		this.line = content.getLine();
		
	}
	
	public HeuristicType getHeuristic() {
		return heuristic;
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
	
}
