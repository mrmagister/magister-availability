package standalone.utils;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class StringUtils {

	private StringUtils() {}
	
	//meta-characters used to identify regexes
	private static String[] regexArray = {
			
			"^","$","[…]", "[^…]", "[;,]",
			"\\A", "\\z", "\\Z", "[abc]",
			"re*", "re+", "re?", 
			"re{ n}", "re{ n,}", 
			"re{ n, m}", "a| b", ".*http://.*",
			"(re)", "(?: re)", "?$/", "[def]",
			"(?> re)", "\\w", "\\W", "a-z&&",
			"\\s", "\\S", "\\d", "\\D",
			"\\A", "\\Z", "\\z", "\\G", "(.+)",
			"\\b", "\\B", "\\f", //"\\n", "\\t",
			"\\Q", "\\E", "*?", "(?", ".*?", "0-9", "[0-9]",
			"]?", "+)+", "a|a", "[a-z]", "[A-Za-z]", "[a-zA-Z]", "[a-z0-9_-]", "[a-zA-Z0-9]", "A-Za-z0-9","[A-Za-z0-9]", "[a-zA-Z_0-9]", "[a-zA-Z0-9_]", 
			"^[^","[^0-9]", "].*", "[^", "\\Q...\\E", "[A-Fa-f0-9]", "[A-Za-z0-9_]",
			"[\\^$.|?*+(){}", "/^\\d+$/", "/^\\d*\\.\\d+$/", 
			
	};
	
	private static String[] regexFilter = { 
	
			"x-notice: 5402 \\\\", "Content-Disposition", "filename=\\\\", "name=\\\\",	"\\\\foo\\\\", "<body bgcolor=\\\\",
			"onclick=\\\\", "textarea id=\\\\", "\\name\\: \\hello\\, \\value\\: \\world\\",
			"style=\\\\", "ticksPerWheel may not be greater than", "documentation locally. Once installed, you can access it by clicking",
			"\"\\\\ ,\\\\n \\\\surname\\\\ :\"", "{\\\\testcase\\\\ : \\\\Streaming string message\\\\}", "xmlns=\\\\http", "C:\\\\windows\\\\system32\\\\logo.exe", 
			"Gosh Didnt KnowIt", "\\\\columnLong\\\\", "\\\\Foo\\\\", "\\\\Bar\\\\", "\\\\bar\\\\",	"\\\\weight\\\\: 20.100000381469727,\\\\n",	"\\\\date\\\\: \\\\",
			"\\\\dogs\\\\: [],\\\\n", "<user name=\\\\Eximel\\\\/>", "{\\\\name\\\\: \\\\Gson\\\\}", "<user name=\\\\SimpleXML\\\\/>", "{\\\\taco\\\\:\\\\delicious\\\\}",
			"Path parameter name must match", "URL query string", "boundary=\\\\", "\\\\success\\\\", "changing layerType. hardware?", "[\\\\a\\\\, \\\\b\\\\, 123]\", \"{\\\\a\\\\: \\\\a\\\\, \\\\b\\\\: null, \\\\c\\\\: 1234}",
			"{a: \\\\hi\\\\, b: {},", "id=\\\\downloadlink\\\\", "formatted=\\\\false\\\\", "distributed under the License is distributed on an", "a^25", 
			"\\\\hello\\\\,\\\\world\\\\", "\\\\Hello World\\\\", "\\\\field1\\\\", "{\\\\a\\\\:1,\\\\b\\\\:2,\\\\c\\\\:3}", "\\\\f560fccb-4020-43c1-8a27-92507ef625bd\\\\",
			"\\\\status\\\\", "\\\\description\\\\", "\\\\search_terms\\\\", "\\\\start_date\\\\", "\\\\DE\\\\\\\\n", "\\\\sources\\\\", "\\\\body\\\\", "\\\\storage_consumption\\\\",
			"\\\\field", "throw iter.reportError", "\\\\firstName\\\\", "\\\\barf\\\\", "$150.00", "\\\\foo2\\\\", "\\\\foo2\\\\", "\\\\white\\\\", "\\aaaaargh\\\\",
			"\\\\email\\\\", "\\\\status\\\\", "\\\\world\\\\", "\\\\world\\\\", "\\\\glassfish\\\\" , "\\\\array\\\\", "\\\\set\\\\",
			"\\\\stringField\\\\", "\\\\bla\\\\", "\\\\indexNames\\\\", "\\\\bigint\\\\", "\\\\foobar\\\\", "\\\\font\\\\", "\\\\deleteMe\\\\",
			"\\\\font-size:10\\\\", "\\\\SDK Manager.exe\\\\", "C:\\\\\\\\", "Not(?)", "nside additional matcher", "[MockitoHint]", "\\\\a\\\\, \\\\b\\\\, \\\\c\\\\",
			"Are you there?", "\\\\str\\\\", "\\\\byte\\\\", "\\\\short\\\\", "\\\\int\\\\", "\\\\long\\\\", "\\\\float\\\\", "\\\\double\\\\",
			"Could not initialize plugin", "\\\\different arg\\\\", "Or(?)", "And(?)"
	};
	
	private static String[] unnecessarySubstrings = {
			
			"\\n", "\\t" , "if(", "if (" , ") {", "new ", "error ", "exception", " ", "->", "<!--", "<!DOCTYPE>", 
			"<abbr>", "<acronym>", "<address>", "<applet>", "<area>", "<article>", "<aside>", "<audio>", "<b>", "<base>", 
			"<basefont>", "<bdi>", "<bdo>", "<big>", "<blockquote>", "<body>", "<br>", "<button>", "<canvas>", "<caption>", 
			"<center>", "<cite>", "<code>", "<col>", "<colgroup>", "<data>", "<datalist>", "<dd>", "<del>", "<details>", 
			"<dfn>", "<dialog>", "<dir>", "<div>", "<dl>", "<dt>", "<em>", "<embed>", "<fieldset>", "<figcaption>", "<figure>", 
			"<font>", "<footer>", "<form>", "<frame>", "<frameset>", "<h1>", "<h2>", "<h3>", "<h4>", "<h5>", "<h6>", "<head>", 
			"<hr>", "<html>", "<i>", "<iframe>", "<img>", "<input>", "<ins>", "<kbd>", "<label>", "<legend>", "<li>", "<link>", 
			"<main>", "<map>", "<mark>", "<meta>", "<meter>", "<nav>", "<noframes>", "<noscript>", "<object>", "<ol>", "<optgroup>", 
			"<option>", "<output>", "<p>", "<param>", "<picture>", "<pre>", "<progress>", "<q>", "<rp>", "<rt>", "<ruby>", "<s>", "<samp>", 
			"<script>", "<section>", "<select>", "<small>", "<source>", "<span>", "<strike>", "<strong>", "<style>", "<sub>", "<summary>", 
			"<sub>", "<svg>", "<table>", "<tbody>", "<td>", "<template>", "<textarea>", "<tfoot>", "<th>", "<thead>", "<title>", "<tr>", 
			"<track>", "<tt>", "<u>", "<ul>", "<var>", "<video>", "<hello>", "<wbr>", "<!DOCTYPE>", "</abbr>", "</acronym>", "</address>", 
			"</applet>", "</area>", "</article>", "</aside>", "</audio>", "</b>", "</base>", "</basefont>", "</bdi>", "</bdo>", "</big>", 
			"</blockquote>", "</body>", "</br>", "</button>", "</canvas>", "</caption>", "</center>", "</cite>", "</code>", "</col>", 
			"</colgroup>", "</data>", "</datalist>", "</dd>", "</del>", "</details>", "</dfn>", "</dialog>", "</dir>", "</div>", "</dl>", 
			"</dt>", "</em>", "</embed>", "</fieldset>", "</figcaption>", "</figure>", "</font>", "</footer>", "</form>", "</frame>", 
			"</frameset>", "</h1>", "</h2>", "</h3>", "</h4>", "</h5>", "</h6>", "</head>", "</hr>", "</html>", "</i>", "</iframe>", 
			"</img>", "</input>", "</ins>", "</kbd>", "</label>", "</legend>", "</li>", "</link>", "</main>", "</map>", "</mark>", 
			"</meta>", "</meter>", "</nav>", "</noframes>", "</noscript>", "</object>", "</ol>", "</optgroup>", "</option>", 
			"</output>", "</p>", "</param>", "</picture>", "</pre>", "</progress>", "</q>", "</rp>", "</rt>", "</ruby>", "</s>", 
			"</samp>", "</script>", "</section>", "</select>", "</small>", "</source>", "</span>", "</strike>", "</strong>", "</style>", 
			"</sub>", "</summary>", "</sub>", "</svg>", "</table>", "</tbody>", "</td>", "</template>", "</textarea>", "</tfoot>", 
			"</th>", "</thead>", "</title>", "</tr>", "</track>", "</tt>", "</u>", "</ul>", "</var>", "</video>", "</hello>", "</wbr>",
			"Iterators.", "Ints.", "Functions.", "base64.", "\u221e", ".keySet"
			
	};
	
	
	/**
	 * This method checks if a given line has a regex.
	 * @param line
	 * @return true (if yes) or false (if no)
	 */
	public static boolean doesLineHaveRegex(String targetString) {
		
		boolean doesItHaveRegex = false;
		
		if (targetString != null && !targetString.equals("")) {
			
			doesItHaveRegex = Arrays.stream(regexArray).parallel().anyMatch(targetString::contains);//check line against the meta characters
			
			if(targetString.contains("type=") || targetString.contains("label=") | targetString.contains("length=") || targetString.contains("pos=")) {
				doesItHaveRegex = false;
			}
			
			if((targetString.contains("$") && targetString.length() == 1) || targetString.equals("\\$")) {
				doesItHaveRegex = false;
			}
			
			if((targetString.contains("^") && targetString.length() == 1)) {
				doesItHaveRegex = false;
			}
			
			if(compareTargetStringWithRegexFilter(targetString)){//check against false positives filter
				doesItHaveRegex = false;
			}
			
			if(!checkIfRegexIsValid(targetString)) {
				
				doesItHaveRegex = false;
			}
			
			if(doesItHaveRegex) {
			
				return doesItHaveRegex;
			
			}
		}

		if (doesItHaveRegex) {
			
			return true;
		
		} else {
		
			return false;
		
		}
	}
	
	
	public static boolean checkIfRegexIsValid(String pattern) {
		
		String userInputPattern = pattern;
        
		try {
        
			Pattern.compile(userInputPattern);
        
		} catch (PatternSyntaxException exception) {
        
			return false;
        }
        
		return true;
	}
	
	public static boolean compareTargetStringWithRegexFilter(String target) {
	
		if(!target.isEmpty() && target != null) {
			
			for (int i = 0; i < regexFilter.length; i++) {
				
				if(target.toLowerCase().contains(regexFilter[i].toLowerCase())) {
					return true;
				}
			}
		}
		
		return false;
		
	}
	
	
	public static boolean compareIfOneStringContainsTheOtherCaseInsensitive(String str1, String str2) {
		
		if(str1.length() == 0 || str2.length() == 0) {
			return false;
		}
		
		if(str1.length() >= str2.length()) {
				
			if(str1.toLowerCase().contains(str2.toLowerCase().toLowerCase())){
				return true;
			} else {
				return false;
			}
				
		} else {
				
			if(str2.toLowerCase().contains(str1.toLowerCase())){
				return true;
			} else {
				return false;
			}
				
		}

	}
	
	public static boolean compareIfTargetStringHasSubstringFromArray(String target) {
		
		if(!target.isEmpty() && target != null) {
		
			for (int i = 0; i < unnecessarySubstrings.length; i++) {
				
				if(target.toLowerCase().contains(unnecessarySubstrings[i].toLowerCase())) {
					
					return true;
					
				}
				
			}
		}
		
		return false; 
		
		
	}
	
}
