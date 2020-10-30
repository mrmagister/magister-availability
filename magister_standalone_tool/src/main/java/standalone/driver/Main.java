package standalone.driver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import standalone.model.DatasetElement;
import standalone.model.ContentHolder;
import standalone.model.CrossCheckResult;
import standalone.model.DataHolder;
import standalone.model.FileRepresentation;
import standalone.model.HeuristicAnalysisResult;
import standalone.model.RegexAnalysisResult;
import standalone.model.RegexType;
import standalone.model.StringLiteralEntropyResult;
import standalone.model.StringLiteralRepresentation;
import standalone.parser.DeclarationsLines;
import standalone.parser.StringLiteralsLines;
import standalone.utils.FileUtils;
import standalone.utils.StringUtils;



public class Main {

	/**
	 * Method for checking if the string matches of the defined password templates
	 * Source of regex for generic template matching: 
	 * "Secrets in Source Code: Reducing False Positives Using Machine Learning"
	 * @param testingString
	 * @return true or false
	 */
	
	private static Process mProcess;
	
	
	
	
	public static void main(String[] args) {
		
		//Run step 1 -- open target project
		String dirPath = "--------------------------------------------/messenger-app-android";
		String outputFilePath = dirPath + "/output.txt";
		String outputFilePathTEST = dirPath + "/output_forgoldset.txt"; 
		
		File file = new File(dirPath);
		/*
		System.gc();
		System.out.println("Checkpoint 1");
		ArrayList<StringLiteralRepresentation> stringLiteralsLines = getStringLiterals(file);
		
		ArrayList<String> entropyThreshold = getStringLiteralsOfEntropy(stringLiteralsLines);
		ArrayList<String> regexFilter = getStringLiteralsMatchingRegex(entropyThreshold);
		FileUtils.writingLineByLine(outputFilePath, regexFilter);
		FileUtils.writingLineByLine(outputFilePathTEST, regexFilter);
		*/
		//Run step 2 -- retrieve target strings
		System.out.println("Checkpoint 2");
		String step2PythonScript = "--------------------------------------------/step2_string_driver.py";
		runPythonScriptToExtractLiterals(step2PythonScript, outputFilePath);
		
		//Run step 3 -- parse target strings
		System.out.println("Checkpoint 3");
		ArrayList<String> dataContents = readFile(outputFilePath);
		ArrayList<DatasetElement> elements = parseFile(dataContents);
		String outputStep3Path = dirPath + "/output-file.txt";
		writeFile(outputStep3Path, elements);
		
		//Run step 4 -- file operator
		System.out.println("Checkpoint 4");
		String step4PythonScript = "--------------------------------------------/step4_file_operator.py";
		runPythonScriptToExtractLiterals(step4PythonScript, outputStep3Path);
		
		//Run step 5 -- dnn operations
		System.out.println("Checkpoint 5");
		String step5PythonScript = "--------------------------------------------/ml_system_driver.py";
		String outputStep4Path = dirPath + "/output-file_extra.txt";
		runPythonScriptToExtractLiterals(step5PythonScript, outputStep4Path);
		return;
	}
	
	public static ArrayList<String> getStringLiteralsMatchingRegex(ArrayList<String> candidates) {
		
		ArrayList<String> returnArray = new ArrayList<String>();
		
		for (int i = 0; i < candidates.size(); i++) {
			
			if(!StringUtils.doesLineHaveRegex(candidates.get(i)) && (!StringUtils.compareIfTargetStringHasSubstringFromArray(candidates.get(i)))) {
				
				if(RegexChecker.regexSearchForSecret(candidates.get(i))) {
					returnArray.add(candidates.get(i));
				}
				
			}
			
		}
		return returnArray;
		
	}
	
	public static ArrayList<String> readFile(String filePath) {
		
		BufferedReader reader;
		ArrayList<String> returnArray = new ArrayList<String>();
		
		try {
		
			reader = new BufferedReader(new FileReader(filePath));
			String line = reader.readLine();
			while (line != null) {
				
				System.out.println("in file: " + line);
				line = reader.readLine();
				returnArray.add(line);
			}
			
			reader.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

		} finally {
		
			return returnArray;
		
		}
		
	}
	
	public static void writeFile(String filePath, ArrayList<DatasetElement> elements) {
		
		File fout = new File(filePath);
		FileOutputStream fos;
		BufferedWriter bw; 
		
		try {
			
			fos = new FileOutputStream(fout);
			bw = new BufferedWriter(new OutputStreamWriter(fos));
			
			for (DatasetElement elem: elements) {
				
				bw.write(elem.toString());
				//bw.write(elem.toString2());
				bw.newLine();
				
			}
			
			bw.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public static ArrayList<standalone.model.DatasetElement> parseFile(ArrayList<String> fileContents) {
		
		ArrayList<DatasetElement> elements = new ArrayList<DatasetElement>();
		
		
		for (String line: fileContents) {
			
			if(line != null) {
				
				if(!line.isEmpty()) {
				
					Boolean label = false;
					String tempString = line;
					
					try {
					
						tempString = line.substring(line.indexOf(":*") + 2);
					
					} catch (Exception e) {
						
					}
					
					if(tempString.contains("__true__")) {
						
						tempString = tempString.replace("__true__", "");
						label = true;
					} else {
						
						tempString = tempString.replace("__false__", "");
										
					}
					
					String[] arr = tempString.split(", ");
					for (String elem: arr) {
						
						elem = elem.replace(" ", "");
						System.out.println(elem);
						elements.add(new DatasetElement(elem, ShannonEntropy.getShannonEntropy(elem), label, HeuristicsChecker.generateArrayListHeuristicTypeFromString(elem), RegexChecker.generateArrayListRegexTypeFromString(elem)));
					}
				}
			}
		}
		
		
		return elements;
		
	}
	
	public static void runPythonScriptToExtractLiterals(String pythonScript, String targetOutputFile) {
		
		Process process;
	    
		try {
			
			process = Runtime.getRuntime().exec(new String[]{"python3", pythonScript, targetOutputFile});
	        mProcess = process;
	    
		} catch(Exception e) {
			
			System.out.println("Exception Raised" + e.toString());
		
		}
	       
		InputStream stdout = mProcess.getInputStream();
	    BufferedReader reader = new BufferedReader(new InputStreamReader(stdout, StandardCharsets.UTF_8));
	    
	    String line;
	    
	    try {
	    
	    	while((line = reader.readLine()) != null){
	    		System.out.println("stdout: "+ line);
	        }
	    
	    } catch(IOException e) {
	        System.out.println("Exception in reading output" + e.toString());
	    }
		
	}
	
	public static ArrayList<String> getStringLiteralsOfEntropy(ArrayList<StringLiteralRepresentation> candidates) {
		
		HashSet<String> returnValues = new HashSet<String>();
		for (int i = 0; i < candidates.size(); i++) {
			if(ShannonEntropy.getShannonEntropy(candidates.get(i).getContents()) >= 3.2) {
				returnValues.add(candidates.get(i).getContents());
			}
		}
		
		ArrayList<String> list = new ArrayList<String>(returnValues);
		return list;
	}
	
	public static ArrayList<String> checkRegex(ArrayList<String> candidates) {
		
		HashSet<String> returnValues = new HashSet<String>();
		for (int i = 0; i < candidates.size(); i++) {
			if(ShannonEntropy.getShannonEntropy(candidates.get(i)) >= 3.2) {
				returnValues.add(candidates.get(i));
			}
		}
		
		ArrayList<String> list = new ArrayList<String>(returnValues);
		return list;
	}
	
	
	public static void getPrecisionAndRecall(ArrayList<FileRepresentation> goldset, HashMap<Integer, CrossCheckResult> crossChecks) {
		
		double totalNumberOfCredentials = 0;
		
		for(int i = 0; i < goldset.size(); i ++) {
			
			for (int j = 0; j < goldset.get(i).getContents().size(); j++) {
				
				if(goldset.get(i).getContents().get(j).contains("//credential")) {
					totalNumberOfCredentials++;
				}
				
			}
			
			
		}
		
		System.out.println(totalNumberOfCredentials);
		
		double hit = 0;
		for(int i = 0; i < goldset.size(); i ++) {
			
			for (int j = 0; j < goldset.get(i).getContents().size(); j++) {
				
				for(Integer key: crossChecks.keySet()) {
					
					//if (crossChecks.get(key).getRegexAnalysisResult().getAnalyzedString().getStringLiteral().getFile().getAbsolutePath().contains(goldset.get(i).getName())) {
						if(goldset.get(i).getContents().get(j).contains(crossChecks.get(key).getRegexAnalysisResult().getAnalyzedString().getStringLiteral().getContents())) {
							hit++;
						}
					//}
					
				}
				
			}
			
		}
		
		double precision = hit/totalNumberOfCredentials;
		
		System.out.println("precision: " + precision);
		
		
	}
	
	
	public static HashMap<Integer, CrossCheckResult> crossCheckRegexesHeuristics(ArrayList<HeuristicAnalysisResult> heuristicsAnalysis, ArrayList<RegexAnalysisResult> regexResults) {
		
		double entropy = 3.20;
		
		HashMap<Integer, CrossCheckResult> crossChecks = new HashMap<Integer, CrossCheckResult>();//maybe differentiate to type?
		
		System.out.println(heuristicsAnalysis.size() + " " + regexResults.size());
		
		for (int i = 0; i < heuristicsAnalysis.size(); i++) {
			
			for (int j = 0; j < regexResults.size(); j++) {
				
				if(regexResults.get(j).getAnalyzedString().getStringLiteral().getLineNumber() == heuristicsAnalysis.get(i).getLine()) {
		
					if(
							!regexResults.get(j).getAnalyzedString().getStringLiteral().getContents().contains("\\n") 
							&& !regexResults.get(j).getAnalyzedString().getStringLiteral().getContents().contains("\\t") 
							&& !regexResults.get(j).getAnalyzedString().getStringLiteral().getContents().contains("if(")
							&& !regexResults.get(j).getAnalyzedString().getStringLiteral().getContents().contains("if (") 
							&& !regexResults.get(j).getAnalyzedString().getStringLiteral().getContents().contains(") {")
							&& !regexResults.get(j).getAnalyzedString().getStringLiteral().getContents().contains("new ")
							&& !regexResults.get(j).getAnalyzedString().getStringLiteral().getContents().contains("error ")
							&& !regexResults.get(j).getAnalyzedString().getStringLiteral().getContents().contains("exception")){
					
						if(regexResults.get(j).getAnalyzedString().getEntropy() >= entropy) {
							CrossCheckResult crossCheckResult = new CrossCheckResult(heuristicsAnalysis.get(i), regexResults.get(j));
							crossChecks.put(heuristicsAnalysis.get(i).getLine(), crossCheckResult);
						}
					}
				}
			}			
		}
		
		return crossChecks;
		
	}
	
	
	/***
	 * @param heuristicsAnalysis
	 * @param regexResults
	 * @return
	 */
	public static HashMap<String, DataHolder> generateMapOfDataHolder(ArrayList<HeuristicAnalysisResult> heuristicsAnalysis, ArrayList<RegexAnalysisResult> regexResults) {
		
		HashMap<String, DataHolder> mapOfDataHolder = new HashMap<String, DataHolder>();//maybe differentiate to type?
		System.out.println(heuristicsAnalysis.size() + " " + regexResults.size());

		for (int i = 0; i < heuristicsAnalysis.size(); i++) {
			
			for (int j = 0; j < regexResults.size(); j++) {
		
				if(regexResults.get(j).getAnalyzedString().getStringLiteral().getLineNumber() == heuristicsAnalysis.get(i).getLine()) {
					
					if(!mapOfDataHolder.containsKey(regexResults.get(j).getAnalyzedString().getStringLiteral().getContents())) {
						
						//check if string has substring that would indicate that it is not a variable
						if(!StringUtils.compareIfTargetStringHasSubstringFromArray(regexResults.get(j).getAnalyzedString().getStringLiteral().getContents())){
						
							//check if string is a regex
							if(!StringUtils.doesLineHaveRegex(regexResults.get(j).getAnalyzedString().getStringLiteral().getContents())) {
								
								DataHolder dHolder = new DataHolder(regexResults.get(j).getAnalyzedString().getStringLiteral().getContents(),
										regexResults.get(j).getAnalyzedString().getEntropy(),
										regexResults.get(j).getRegex(),
										heuristicsAnalysis.get(i).getHeuristic());
								mapOfDataHolder.put(regexResults.get(j).getAnalyzedString().getStringLiteral().getContents(), dHolder);
								
							}
						}
					}
				}
			}			
		}
		
		return mapOfDataHolder;
		
	}
	
	/***
	 * @param heuristicsAnalysis
	 * @param regexResults
	 * @return
	 */
	public static HashMap<String, DataHolder> generateMapOfDataHolder2(ArrayList<ContentHolder> variableDeclarations, ArrayList <StringLiteralRepresentation> stringLiteralsLines) {
		
		HashMap<String, DataHolder> mapOfDataHolder = new HashMap<String, DataHolder>();//maybe differentiate to type?
		
		for(int i = 0; i < variableDeclarations.size(); i++) {
			
			String key = variableDeclarations.get(i).getContent();
			DataHolder value = new DataHolder(key, ShannonEntropy.getShannonEntropy(key), RegexChecker.generateRegexTypeFromString(key), HeuristicsChecker.generateHeuristicTypeFromString(key));
			mapOfDataHolder.put(key, value);
			
		}
		
		for(int i = 0; i < stringLiteralsLines.size(); i++) {
			
			String key = stringLiteralsLines.get(i).getContents();
			DataHolder value = new DataHolder(key, ShannonEntropy.getShannonEntropy(key), RegexChecker.generateRegexTypeFromString(key), HeuristicsChecker.generateHeuristicTypeFromString(key));
			mapOfDataHolder.put(key, value);
		}
		

		return mapOfDataHolder;
		
	}
	
	
	public static void printCrossCheckResults(HashMap<Integer, CrossCheckResult> crossChecks) {
		
		crossChecks.entrySet().forEach(entry->{
			    System.out.println(entry.getKey() + " " + entry.getValue().getHeuristicAnalysisResult().getContent() +  "  " 
		+ entry.getValue().getRegexAnalysisResult().getAnalyzedString().getStringLiteral().getContents() + "  " 
			    		+ entry.getValue().getRegexAnalysisResult().getRegex() + "\t\t "
			    		+ entry.getValue().getRegexAnalysisResult().getAnalyzedString().getEntropy() + "\n" 
			    		+ entry.getValue().getRegexAnalysisResult().getAnalyzedString().getStringLiteral().getFile().getAbsolutePath());  
			    
			 });
	}
	
	public static void printDataHolder(HashMap<String, DataHolder> dataMap) {
		System.out.println(dataMap.size());
		dataMap.entrySet().forEach(entry->{
			    System.out.println(entry.getKey() + " " + entry.getValue().getValueOf());  	    
			 });
	}
	
	
	public static ArrayList<HeuristicAnalysisResult> getHeuristicsResults(ArrayList<ContentHolder> stringLiteralEntropyResult) {
		
		return HeuristicsChecker.generateHeuristicAnalysisReport(stringLiteralEntropyResult);
	}
	
	public static ArrayList<ContentHolder> getVariableDeclarations (File file) {
		
		try {
			
			return new DeclarationsLines().declarationsByLine(file);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static ArrayList<StringLiteralEntropyResult> getStringLiteralsEntropy (ArrayList<StringLiteralRepresentation> stringLiteralsLines) {
		
		ArrayList<StringLiteralEntropyResult> stringLiteralEntropyResult = new ArrayList<StringLiteralEntropyResult>();
		
		for (StringLiteralRepresentation stringLiteral: stringLiteralsLines) {
			
			double entropy = ShannonEntropy.getShannonEntropy(stringLiteral.getContents());
			stringLiteralEntropyResult.add(new StringLiteralEntropyResult(stringLiteral, entropy));
			System.out.printf("Shannon entropy of %40s: %.12f%n", "\"" + stringLiteral.getContents() + "\"", entropy);
		}
		
		return stringLiteralEntropyResult;
	}
	
	public static ArrayList<StringLiteralRepresentation> getStringLiterals(File file) {
		
		try {
				
			return new StringLiteralsLines().literalsByLine(file);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static ArrayList<RegexAnalysisResult> getStringLiteralRegexResults(ArrayList<StringLiteralEntropyResult> stringLiteralEntropyResult) {
		
		return RegexChecker.generateRegexAnalysisReport(stringLiteralEntropyResult);
	}
	
	/**
	 * entropy and regular expression filtering
	 * @param regexResults
	 */
	public static void filterLiterals(ArrayList<RegexAnalysisResult> regexResults) {
		
		double avgEntropy = 0.0;
		boolean flag = true;
		for (RegexAnalysisResult regexResult : regexResults) {
		
			if(flag) {
			
				avgEntropy = regexResult.getAnalyzedString().getEntropy();
				flag = false;
			
			} else {
				
				avgEntropy = ((regexResult.getAnalyzedString().getEntropy() + avgEntropy)/2);	
			
			}
			
			if(regexResult.getRegex() != RegexType.none || regexResult.getAnalyzedString().getEntropy() >= avgEntropy) {
					
				System.out.println("==============================================================================");
				System.out.println("file :: " + regexResult.getAnalyzedString().getStringLiteral().getFile().toString());
				System.out.println("marked :: " + regexResult.getAnalyzedString().getStringLiteral().getContents());
				System.out.println("entropy :: " + regexResult.getAnalyzedString().getEntropy());
				System.out.println("regex :: " + regexResult.getRegex());
				System.out.println("avg_entropy :: " + avgEntropy);
				
			}
			
		}
		
		
		
	}
	
	
	public static void printArrayDeclarations(ArrayList<ContentHolder> variableDeclarations) {
		
		for (int i = 0; i < variableDeclarations.size(); i++) {
			System.out.println("field declarations: " + variableDeclarations.get(i).getContent() + " : init : " + variableDeclarations.get(i).getInitValue()
        			+ " : line# : " + variableDeclarations.get(i).getLine());
		}
		
	}
	
	
	public static void printArray(ArrayList<RegexAnalysisResult> regexResults) {
		
		for (int j = 0; j < regexResults.size(); j++) {
			System.out.println("File: " + regexResults.get(j).getAnalyzedString().getStringLiteral().getFile().toString());
			System.out.println("String: " + regexResults.get(j).getAnalyzedString().getStringLiteral().getContents().toString());
			System.out.println("Line #: " + regexResults.get(j).getAnalyzedString().getStringLiteral().getLineNumber());
			System.out.println("Entropy: " + regexResults.get(j).getAnalyzedString().getEntropy());
			System.out.println("Regex: " + regexResults.get(j).getRegex());
		}
		
	}
	
}
