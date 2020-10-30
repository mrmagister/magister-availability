package standalone.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileUtils {

	private FileUtils() {}
	
	public static ArrayList<String> readFileLineByLine(String filePath){
		
		ArrayList<String> returnArray = new ArrayList<String>();
		
		File file = new File(filePath);
		
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(
					file.getAbsolutePath()));
			String line = reader.readLine();
			while (line != null) {
				returnArray.add(line);
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return returnArray;
		
	}
	
	public static String readFileLineByLineToString(String pathToFile) throws Exception {
		
		FileInputStream inputStream = null;
		Scanner sc = null;
		
		StringBuilder sb = new StringBuilder();
		
		try {
		    
			inputStream = new FileInputStream(pathToFile);
		    sc = new Scanner(inputStream, "UTF-8");
		    while (sc.hasNextLine()) {
		        String line = sc.nextLine();
		        // System.out.println(line);
		        sb.append(line);
		    }
		
		    // note that Scanner suppresses exceptions
		    if (sc.ioException() != null) {
		        throw sc.ioException();
		    }
		
		} finally {
		    if (inputStream != null) {
		        inputStream.close();
		    }
		    if (sc != null) {
		        sc.close();
		    }
		    
		    return sb.toString();
		}
		
	}
	
	public static ArrayList<String> search(String pathToDir) {
        
	    File folder = new File(pathToDir);
	 
	    String[] files = folder.list();
	    ArrayList<String> fileList = new ArrayList<String>();
	    for (String file : files){
	    	System.out.println(file);
	    	if(file.contains(".java")) {
	    		fileList.add(pathToDir + "/" + file);
	    	}
	    }
	    
	    return fileList;
	 
    }
	
	public static void writingLineByLine(String filePath, ArrayList<String> targetLines) {
		
		try {

			FileWriter writer = new FileWriter(filePath); 
			
			for(String str: targetLines) {
				writer.write(str + "\n");
			
			}
	
			writer.close();
		
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
}
