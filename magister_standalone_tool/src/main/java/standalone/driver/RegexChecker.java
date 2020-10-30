package standalone.driver;

import java.util.ArrayList;
import java.util.regex.Pattern;

import standalone.model.RegexAnalysisResult;
import standalone.utils.RegexUtils;
import standalone.model.RegexType;
import standalone.model.StringLiteralEntropyResult;
import standalone.model.constants.Constants;

public class RegexChecker {

	private RegexChecker() {}
	
		
	
	/**
	 * This method receives a str and compares it against the regex that commonly hardcoded-secrets
	 * @param str
	 * @return true | false
	 */
	public static boolean regexSearchForSecret (String str) {
		
		for (int i = 0; i < Constants.regexList.length; i++) {
			
			if(Pattern.matches(Constants.regexList[i], str)) {
				return true;	
			}
		}
		
		return false;		
		
	}
	
	public static ArrayList<RegexType> generateArrayListRegexTypeFromString(String string) {
		
		ArrayList<RegexType> returnArray = new ArrayList<RegexType>();
		
		for (int i = 0; i < Constants.regexList.length; i++) {
		
			RegexType regexType = generateRegexAnalysisResult2(Constants.regexList[i], string);
			
			if(regexType != RegexType.none) {
			
				returnArray.add(regexType);
			
			}
		
		}
		
		if(returnArray.size() == 0) {
			returnArray.add(RegexType.none);
		}
		
		return returnArray;
		
	}
	
	 
	public static ArrayList<RegexAnalysisResult> generateRegexAnalysisReport(ArrayList<StringLiteralEntropyResult> stringLiteralEntropyResult) {
		
		ArrayList<RegexAnalysisResult> regexResults = new ArrayList<RegexAnalysisResult>();
		
		for (int i = 0; i < Constants.regexList.length; i++) {
			
			for (int j = 0; j < stringLiteralEntropyResult.size(); j++) {
				
				try {
				
					
					RegexAnalysisResult regexResult = generateRegexAnalysisResult(Constants.regexList[i], stringLiteralEntropyResult.get(j));
					if(regexResult.getRegex() != RegexType.none) {
					 	regexResults.add(regexResult);
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
		
		return regexResults;
		
	}
	
	public static RegexType generateRegexTypeFromString(String string) {
		
		
		for (int i = 0; i < Constants.regexList.length; i++) {
			
			try {
				
				RegexType regexType = generateRegexAnalysisResult2(Constants.regexList[i], string);
				if(regexType != RegexType.none) {
					return regexType;
				}
					
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}			
		}
		
		return RegexType.none;
		
	}
	
	public static RegexType generateRegexAnalysisResult2(String regex, String analyzedString) {
		
		if(Pattern.matches(regex, analyzedString)) {
			
			try {
				return RegexUtils.getRegexType(regex);	
			} catch (Exception e) {
				e.printStackTrace();//test comment
			}
				
		}
		
		return RegexType.none;
	}
	
	public static RegexAnalysisResult generateRegexAnalysisResult(String regex, StringLiteralEntropyResult analyzedString) {
		
		if(Pattern.matches(regex, analyzedString.getStringLiteral().getContents())) {
			
			try {
				return new RegexAnalysisResult(RegexUtils.getRegexType(regex), analyzedString);	
			} catch (Exception e) {
				e.printStackTrace();//test comment
			}
				
		}
		
		return new RegexAnalysisResult(RegexType.none, analyzedString);
	}
	
}
