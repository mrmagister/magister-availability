package standalone.model.constants;

public class Constants {

	private Constants() {};
	
	/**
	 * List of keywords for matching variable names and string literals.
	 * The keywords were taken from the following website: https://github.com/kootenpv/gittyleaks/blob/master/gittyleaks/gittyleaks.py
	 */
	public static String[] keywords = {
			
			"api", 
			"key", 
			"username", 
			"user", 
			"test",
			"uname", 
			"pw", 
			"password",
            "pass", 
            "email", 
            "mail", 
            "credentials", 
            "credential", 
            "login",
            "token", 
            "secret",
            "annotation",
            "herobrine",
            "aws", 
            "com.",
            "metadata",
            "json",
            ".com",
            "github",
            "/home/",
			
	};
	
	/**
	 * List of regular expressions for matching hardcoded secrets
	 * The regexes were taken from two places (a paper and an opensource repo)
	 * 	paper --> Secrets in Source Code: Reducing False Positives Using Machine Learning
	 * 	truffleHog --> https://github.com/dxa4481/truffleHog
	 * 	githound --> https://github.com/ezekg/git-hound
	 */
	public static String[] regexList = {
	
			"(SELECT\\s[\\w\\*\\)\\(\\,\\s]+\\sFROM\\s[\\w]+)| (UPDATE\\s[\\w]+\\sSET\\s[\\w\\,\\'\\=]+)| (INSERT\\sINTO\\s[\\d\\w]+[\\s\\w\\d\\)\\(\\,]*\\sVALUES\\s\\([\\d\\w\\'\\,\\)]+)| (DELETE\\sFROM\\s[\\d\\w\\'\\=]+)",//MySQL_QUERY
			"<(\"[^\"]*\"|'[^']*'|[^'\">])*>" + "\\s*\\w[^<]*" + "<(\"[^\"]*\"|'[^']*'|[^'\">])*>" + "|" + "<(\"[^\"]*\"|'[^']*'|[^'\">])*>" + "\\s*\"[^\"<]*\"\\s*" + "<(\"[^\"]*\"|'[^']*'|[^'\">])*>",//XML_HTML_BODY
			"^/|(/[a-zA-Z0-9_-]+)+$", //linux_file_path
			"^(?:[\\w]\\:|\\\\)(\\\\[a-z_\\-\\s0-9\\.]+)+\\.(txt|gif|pdf|doc|docx|xls|xlsx)$",//filepath+extension
			"^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$",//mac address
			"^(?:[A-F0-9]{1,4}:){7}[A-F0-9]{1,4}$",//IPv6  //"(?ix)\\A(?:(?:(?:[A-F0-9]{1,4}:){6}|(?=(?:[A-F0-9]{0,4}:){2,6}(?:[0-9]{1,3}\\.){3}[0-9]{1,3}\\z)(([0-9A-F]{1,4}:){1,5}|:)((:[0-9A-F]{1,4}){1,5}:|:)|::(?:[A-F0-9]{1,4}:){5})\n(?:(?:25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\\.){3}(?:25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])|(?:[A-F0-9]{1,4}:){7}[A-F0-9]{1,4}|(?=(?:[A-F0-9]{0,4}:){0,7}[A-F0-9]{0,4}\\z)(([0-9A-F]{1,4}:){1,7}|:)((:[0-9A-F]{1,4}){1,7}|:)|(?:[A-F0-9]{1,4}:){7}:|:(:[A-F0-9]{1,4}){7})/[A-F0-9]{0,4}\\z",//ipv6 
			".+@.+\\.[a-z]+",//email
			"<(\"[^\"]*\"|'[^']*'|[^'\">])*>",//tag
			"^[\\w,\\s-]+\\.[A-Za-z0-9]{2}$", //source file
			"^[\\w,\\s-]+\\.[A-Za-z0-9]{1,4}$", //file name
			"^[a-z]+(\\.[a-z][a-z0-9]*)*$",//package name
			"^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$",//"(?i)pass(word)?\\W*[:=,]\\W*.+$", //GenericPassword3
			"(?i)db_(user(name)?|pass(word)?|name)\\W*[:=,]\\W*.+$", //GenericDB Credential 
			"[''\"](?!.*[\\s])(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#$&*])?.{16,}[''\"]", // GenericPassword2
			"(?=.*[a-zA-Z])(?=.*[\\d~!@#$%^&*()_+{}\\[\\]?<>|]).{6,50}",// GenericPassword1 -- password match 6 to 50 characters, at least 1 aplha character, and at least 1 numeric or special char
			"[a|A][p|P][p|P][s|S][e|E][c|C][r|R][e|E][t|T].*[’|”]([0-9a-zA-Z]{32,45})[’|”]", // Generic AppSecret
			"^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",//".*[p|P][a|A][s|S][s|S][w|W][o|O][r|R][d|D].*[:=](.*)", // Generic Password 
			"//[\\\\s:]+:[\\\\s:]+@", // Password in URL 
			"(Password |pass |passwd |session password |login password |password) \\s*[:=]( \\s*[^\\r\\n]+)", // Other passwords
			"(API tokens |api tokens |tokens |Tokens)\\s*[:=](\\s*[^\\r \\n][a-f0-9]{16})",	// Tokens
			"[a|A][c|C][c|C][s|S][e|E][s|S][s|S][t|T][o|O][k|K][e|E][n|N].*[:=](.*)", // Generic Access Token
			"(client secret |access secret |customer secret |app secret)\\s*[:=](\\s*[ˆ\\r\\n]+)", // Other secrets
			"(xox[p|b|o|a]-[0-9]{12}-[0-9]{12}-[0-9]{12}-[a-z0-9]{32})", // Slack Token
			"-----BEGIN RSA PRIVATE KEY-----", // RSA private key  
			"-----BEGIN OPENSSH PRIVATE KEY-----", // SSH (OPENSSH) private key 
			"-----BEGIN DSA PRIVATE KEY-----", // SSH (DSA) private key 
			"-----BEGIN EC PRIVATE KEY-----", // SSH (EC) private key
			"-----BEGIN PGP PRIVATE KEY BLOCK-----", // PGP private key block
			"[f|F][a|A][c|C][e|E][b|B][o|O][o|O][k|K].{0,30}['\"\\s][0-9a-f]{32}['\"\\s]", // Facebook Oauth 
			"[0-9a-zA-Z]{35,44}", // Twitter Oauth
			"['\"\\s][0-9a-zA-Z]{35,40}['\"\\s]", // GitHub Oauth
			"[a-zA-Z0-9-_]{24}", // Google Oauth
			"AKIA[0-9A-Z]{16}", // AWS API Key 
			"^\\s*test_function\\((['\"])((?:(?!\\1).)*)\\1 *, *(['\"])((?:(?!\\3).)*)\\3\\);?\\s*$",//method call
			"[h|H][e|E][r|R][o|O][k|K][u|U].{0,30}[0-9A-F]{8}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{12}", // Heroku API Key
			"[s|S][e|E][c|C][r|R][e|E][t|T].{0,30}['\"\\s][0-9a-zA-Z]{32,45}['\"\\s]", // Generic Secret
			"[a|A][p|P][i|I][_]?[k|K][e|E][y|Y].{0,30}['\"\\s][0-9a-zA-Z]{32,45}['\"\\s]", // Generic API Key 
			"https://hooks.slack.com/services/T[a-zA-Z0-9_]{8}/B[a-zA-Z0-9_]{8}/[a-zA-Z0-9_]{24}", // Slack Webhook
			"SK[a-z0-9]{32}", // Twilio API Key
			"[a-zA-Z]{3,10}://[^/\\s:@]{3,20}:[^/\\s:@]{3,20}@.{1,100}[\"'\\s]", // Password in URL
			"([01]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])\\.([01]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])\\.([01]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])\\.([01]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])",//"^((25[0-5]|(2[0-4]|1[0-9]|[1-9]|)[0-9])(\\.(?!$)|$)){4}$", //ipadress
			"\\d{4}-[01]\\d-[0-3]\\d", //date
			"([A-Z|a-z]:\\[^*|\"<>?\n]*)|(\\\\.*?\\.*)", //file path
			"/^[a-f0-9]{32}$/i",//md5_hash
	};

	
	
}
