package standalone.model;

public class CrossCheckResult {

	private HeuristicAnalysisResult heuristicAnalysisResult;
	private RegexAnalysisResult regexAnalysisResult;

	public CrossCheckResult(HeuristicAnalysisResult heuristicAnalysisResult, RegexAnalysisResult regexAnalysisResult) {

		this.heuristicAnalysisResult = heuristicAnalysisResult;
		this.regexAnalysisResult = regexAnalysisResult;

	}

	public HeuristicAnalysisResult getHeuristicAnalysisResult() {
		return heuristicAnalysisResult;
	}

	public RegexAnalysisResult getRegexAnalysisResult() {
		return regexAnalysisResult;
	}

}
