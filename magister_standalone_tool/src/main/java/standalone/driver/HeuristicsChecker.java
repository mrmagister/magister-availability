package standalone.driver;

import java.util.ArrayList;
import java.util.regex.Pattern;

import standalone.model.ContentHolder;
import standalone.model.HeuristicAnalysisResult;
import standalone.model.HeuristicType;
import standalone.model.Type;
import standalone.model.constants.Constants;
import standalone.utils.StringUtils;
import standalone.utils.HeuristicUtils;

public class HeuristicsChecker {

	private HeuristicsChecker() {}
	
	/**
	 * This method receives a str and compares it against the keywords of commonly coded credentials
	 * @param str
	 * @return true | false
	 */
	public static boolean heuristicSearchForSecret (String str) {
		
		for (int i = 0; i < Constants.keywords.length; i++) {
			
			if(Constants.keywords[i].length() >= str.length()) {
				
				if(Constants.keywords[i].toLowerCase().contains(str.toLowerCase())){
					return true;	
				}
				
			} else {
				
				if(str.toLowerCase().contains(Constants.keywords[i].toLowerCase())){
					return true;	
				}
			}
		}
		
		return false;		
		
	}
	
	public static ArrayList<HeuristicAnalysisResult> generateHeuristicAnalysisReport(ArrayList<ContentHolder> contents) {
		
		ArrayList<HeuristicAnalysisResult> heuristicResults = new ArrayList<HeuristicAnalysisResult>();
		
		for (int i = 0; i < Constants.keywords.length; i++) {
			
			for (int j = 0; j < contents.size(); j++) {
				
				try {
					
					HeuristicAnalysisResult heuristicResult = generateHeuristicAnalysisResult(Constants.keywords[i], contents.get(j));
					if(heuristicResult.getHeuristic() != HeuristicType.none) {
						heuristicResults.add(heuristicResult);
					}
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
//				if(RegexChecker.regexSearchForSecret(ss)) {
//					System.out.println("Match - " + ss);
//					
//				}
			}
		}
		
		return heuristicResults;
		
	}
	
	
	public static HeuristicType generateHeuristicTypeFromString(String string) {
		
		for (int i = 0; i < Constants.keywords.length; i++) {
				
			try {
					
				if(StringUtils.compareIfOneStringContainsTheOtherCaseInsensitive(Constants.keywords[i], string)) {
					
					return HeuristicUtils.getHeuristicType(Constants.keywords[i]);
					
				}
					
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		return HeuristicType.none;
		
	}
	
	public static HeuristicType generateHeuristicAnalysisResult2(String keyword, String analyzedString) {
		
		if (StringUtils.compareIfOneStringContainsTheOtherCaseInsensitive(keyword, analyzedString)) {
			
			return HeuristicUtils.getHeuristicType(keyword);
			
		}
	
		return HeuristicType.none;
		
	}
	
	public static ArrayList<HeuristicType> generateArrayListHeuristicTypeFromString(String string) {
		
		ArrayList<HeuristicType> returnArray = new ArrayList<HeuristicType>();
		
		for (int i = 0; i < Constants.keywords.length; i++) {
		
			HeuristicType heuristicType =  generateHeuristicAnalysisResult2(Constants.keywords[i], string);
			
			if(heuristicType != HeuristicType.none) {
				returnArray.add(heuristicType);
			}
			
		}
		
		if(returnArray.size() == 0) {
			returnArray.add(HeuristicType.none);
		}
		
		return returnArray;
		
	}
	
	
	public static HeuristicAnalysisResult generateHeuristicAnalysisResult(String keyword, ContentHolder contents) {
		
		boolean declaration = false;
		boolean literal = false;
		Type type = Type.declaration;
		
		declaration = StringUtils.compareIfOneStringContainsTheOtherCaseInsensitive(keyword, contents.getContent());
		literal = StringUtils.compareIfOneStringContainsTheOtherCaseInsensitive(keyword, contents.getInitValue());
		
		if(declaration && literal) {
			type = Type.both;
		} else if (literal){
			type = Type.literal;
		}
		
		return new HeuristicAnalysisResult(HeuristicUtils.getHeuristicType(keyword), new ContentHolder(contents.getContent(), type, contents.getLine(), contents.getInitValue()));
		
	}
	
}
