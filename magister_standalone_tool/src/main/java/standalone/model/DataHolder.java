package standalone.model;

public class DataHolder {

	public String contents;
	public double entropy;
	public RegexType regexType;
	public HeuristicType heuristicType;
	public boolean isCredential = false;
	
	public DataHolder(String contents, double entropy, RegexType regexType, HeuristicType heuristicType) {
		
		// TODO Auto-generated constructor stub
		this.contents = contents;
		this.entropy = entropy;
		this.regexType = regexType;
		this.heuristicType = heuristicType;
		
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public double getEntropy() {
		return entropy;
	}

	public void setEntropy(double entropy) {
		this.entropy = entropy;
	}

	public RegexType getRegexType() {
		return regexType;
	}

	public void setRegexType(RegexType regexType) {
		this.regexType = regexType;
	}

	public HeuristicType getHeuristicType() {
		return heuristicType;
	}

	public void setHeuristicType(HeuristicType heuristicType) {
		this.heuristicType = heuristicType;
	}
	
	public boolean getIsCredential() {
		return isCredential;
	}
	
	public void setIsCredential(boolean isCredential) {
		this.isCredential = isCredential;
	}
	
	public String getValueOf() {
		
		return getContents() + "," + getEntropy() + "," + getRegexType() + "," + getHeuristicType();
		
	}
}
