package standalone.model;

import java.util.ArrayList;

public class DatasetElement {

	public String string; 
	public Double entropy;
	public Boolean label;
	public ArrayList<HeuristicType> heuristicArray;
	public ArrayList<RegexType> regexArray;

	
	public DatasetElement(String string, Double entropy, Boolean label, ArrayList<HeuristicType> heuristicArray, ArrayList<RegexType> regexArray) {
		// TODO Auto-generated constructor stub
		this.string = string;
		this.entropy = entropy;
		this.label = label;
		this.heuristicArray = heuristicArray;
		this.regexArray = regexArray;
	}

	@Override
	public String toString() {
				
		String temp = this.string + "\t" + this.entropy + "\t";
		
		for (int i = 0; i < this.heuristicArray.size(); i++) {
			
			if(i == 0) {
			
				temp += "[" + this.heuristicArray.get(i);
				
			} if (i > 0 && i < this.heuristicArray.size()){
			
				temp += "|" + this.heuristicArray.get(i);
			
			} 
				
		}
		
		if(this.heuristicArray.size() == 1 && this.heuristicArray.get(0) == HeuristicType.none) {
			temp += "]\t" + 0 + "\t";
		} else {
			temp += "]\t" + this.regexArray.size() + "\t ";
		}
		
		for (int i = 0; i < this.regexArray.size(); i++) {
			
			if(i == 0) {
			
				temp += "[" + this.regexArray.get(i);
				
			} if (i > 0 && i < this.regexArray.size()){
			
				temp += "|" + this.regexArray.get(i);
			
			} 
				
		}
		
		if(this.regexArray.size() == 1 && this.regexArray.get(0) == RegexType.none) {
			temp += "]\t" + 0 + "\t" + this.label;
		} else {
			temp += "]\t" + this.regexArray.size() + "\t" + this.label;
		}
		
		return temp;
		
	}
	

	public String toString2() {
				
		String temp = this.string + "\t" + this.entropy + "\t";
		
		for (int i = 0; i < this.heuristicArray.size(); i++) {
			
			if(i == 0) {
			
				temp += "[" + this.heuristicArray.get(i);
				
			} if (i > 0 && i < this.heuristicArray.size()){
			
				temp += "|" + this.heuristicArray.get(i);
			
			} 
				
		}
		
		if(this.heuristicArray.size() == 1 && this.heuristicArray.get(0) == HeuristicType.none) {
			temp += "]\t" + 0 + "\t";
		} else {
			temp += "]\t" + this.regexArray.size() + "\t";
		}
		
		for (int i = 0; i < this.regexArray.size(); i++) {
			
			if(i == 0) {
			
				temp += "[" + this.regexArray.get(i);
				
			} else if (i < this.regexArray.size()){
			
				temp += "|" + this.regexArray.get(i);
			
			} if (i == this.regexArray.size()){
			
				temp += "]\t";
			
			}
			
		}
		
		if(this.regexArray.size() == 1 && this.regexArray.get(0) == RegexType.none) {
			
			temp += 0;
		
		} else {
		
			temp += this.regexArray.size();
		
		}
		
		return temp;
		
	}
	
}
