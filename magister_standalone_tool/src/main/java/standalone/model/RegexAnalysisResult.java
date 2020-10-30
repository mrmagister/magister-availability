package standalone.model;

public class RegexAnalysisResult {

	private RegexType regex;
	private StringLiteralEntropyResult analyzedString;
	
	public RegexAnalysisResult(RegexType regex, StringLiteralEntropyResult analyzedString) {
		
		this.regex = regex;
		this.analyzedString = analyzedString;
		
	}

	public RegexType getRegex() {
		return regex;
	}

	public StringLiteralEntropyResult getAnalyzedString() {
		return analyzedString;
	}
	
	
	
}
