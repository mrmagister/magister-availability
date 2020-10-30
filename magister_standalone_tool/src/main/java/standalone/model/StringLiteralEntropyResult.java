package standalone.model;

public class StringLiteralEntropyResult {

	private StringLiteralRepresentation stringLiteral;
	private double entropy;
	
	public StringLiteralEntropyResult (StringLiteralRepresentation stringLiteral, double entropy) {
		
		this.stringLiteral = stringLiteral;
		this.entropy = entropy;
		
	}

	public StringLiteralRepresentation getStringLiteral() {
		return stringLiteral;
	}

	public double getEntropy() {
		return entropy;
	}

		
}
