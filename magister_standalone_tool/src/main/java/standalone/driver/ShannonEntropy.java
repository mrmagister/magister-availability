package standalone.driver;

import java.util.HashMap;
import java.util.Map.Entry;

public class ShannonEntropy {
	
	private ShannonEntropy() {}
	
	private static double log2(double a) {
	    return Math.log(a) / Math.log(2);
	}
	
	/**
	 * source --> https://rosettacode.org/wiki/Entropy#Java
	 * This method receives a String and prints outputs the Shannon's entropy value
	 * @param str
	 * @return double
	 */
	public static double getShannonEntropy(String str) {
		
		int n = 0;
	    HashMap<Character, Integer> occ = new HashMap();
	 
	    for (int c_ = 0; c_ < str.length(); ++c_) {
	      char cx = str.charAt(c_);
	      if (occ.containsKey(cx)) {
	        occ.put(cx, occ.get(cx) + 1);
	      } else {
	        occ.put(cx, 1);
	      }
	      ++n;
	    }
	 
	    double e = 0.0;
	    	    
	    for (Entry<Character, Integer> entry : occ.entrySet()) {
	      
	    	char cx = entry.getKey();
	    	double p = (double) entry.getValue() / n;
	    	e += p * log2(p);
	    }
	    return -e;
		
	}

}
