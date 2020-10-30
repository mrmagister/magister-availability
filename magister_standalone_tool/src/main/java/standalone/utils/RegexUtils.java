package standalone.utils;

import standalone.model.RegexType;

public class RegexUtils {
	
	private RegexUtils() {}
	
	/**
	 * 	This method gets a string representation of a regex and returns the enum regexType
	 * @param regex
	 * @return RegexType
	 */
	public static RegexType getRegexType(String regex) {
		
switch (regex) {
		
		case "(SELECT\\s[\\w\\*\\)\\(\\,\\s]+\\sFROM\\s[\\w]+)| (UPDATE\\s[\\w]+\\sSET\\s[\\w\\,\\'\\=]+)| (INSERT\\sINTO\\s[\\d\\w]+[\\s\\w\\d\\)\\(\\,]*\\sVALUES\\s\\([\\d\\w\\'\\,\\)]+)| (DELETE\\sFROM\\s[\\d\\w\\'\\=]+)":
			return RegexType.MYSQL_QUERY;
		
		case "^(?:[\\w]\\:|\\\\)(\\\\[a-z_\\-\\s0-9\\.]+)+\\.(txt|gif|pdf|doc|docx|xls|xlsx)$":
			return RegexType.FILEPATH_EXTENSION;

		case "<(\"[^\"]*\"|'[^']*'|[^'\">])*>" + "\\s*\\w[^<]*" + "<(\"[^\"]*\"|'[^']*'|[^'\">])*>" + "|" + "<(\"[^\"]*\"|'[^']*'|[^'\">])*>" + "\\s*\"[^\"<]*\"\\s*" + "<(\"[^\"]*\"|'[^']*'|[^'\">])*>":
			return RegexType.XML_HTML_BODY;
		
		case "^/|(/[a-zA-Z0-9_-]+)+$":
			return RegexType.LINUX_FILE_PATH;
		
		case ".+@.+\\.[a-z]+": 
			return RegexType.EMAIL;
			
		case "^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$":
			return RegexType.MAC_ADDRESS;
			
		case "^(?:[A-F0-9]{1,4}:){7}[A-F0-9]{1,4}$": //"(?ix)\\A(?:(?:(?:[A-F0-9]{1,4}:){6}|(?=(?:[A-F0-9]{0,4}:){2,6}(?:[0-9]{1,3}\\.){3}[0-9]{1,3}\\z)(([0-9A-F]{1,4}:){1,5}|:)((:[0-9A-F]{1,4}){1,5}:|:)|::(?:[A-F0-9]{1,4}:){5})\n(?:(?:25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\\.){3}(?:25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])|(?:[A-F0-9]{1,4}:){7}[A-F0-9]{1,4}|(?=(?:[A-F0-9]{0,4}:){0,7}[A-F0-9]{0,4}\\z)(([0-9A-F]{1,4}:){1,7}|:)((:[0-9A-F]{1,4}){1,7}|:)|(?:[A-F0-9]{1,4}:){7}:|:(:[A-F0-9]{1,4}){7})/[A-F0-9]{0,4}\\z":
			return RegexType.IPV6;
		
		case "<(\"[^\"]*\"|'[^']*'|[^'\">])*>":
			return RegexType.TAG;
		
		case "^[\\w,\\s-]+\\.[A-Za-z0-9]{1,4}$":
			return RegexType.FILE_NAME;
			
		case "^[\\w,\\s-]+\\.[A-Za-z0-9]{2}$":
			return RegexType.SOURCE_FILE;
		
		case "^\\s*test_function\\((['\"])((?:(?!\\1).)*)\\1 *, *(['\"])((?:(?!\\3).)*)\\3\\);?\\s*$":
			return RegexType.METHOD_CALL;
			
		case "^[a-z]+(\\.[a-z][a-z0-9]*)*$":
			return RegexType.PACKAGE_NAME;
		
		case "([A-Z|a-z]:\\[^*|\"<>?\n]*)|(\\\\.*?\\.*)":
			return RegexType.FILE_PATH;
		
		case "[a|A][p|P][p|P][s|S][e|E][c|C][r|R][e|E][t|T].*[’|”]([0-9a-zA-Z]{32,45})[’|”]": // Generic AppSecret
			return RegexType.GENERIC_APPSECRET;
		
		//case ".*[p|P][a|A][s|S][s|S][w|W][o|O][r|R][d|D].*[:=](.*)": // Generic Password  ".*[*.!@#$%^&(){}[]:\";'<>,.?/~`_+-=|\\].*":
		case "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$":
			return RegexType.GENERIC_PASSWORD;

		case "[''\"](?!.*[\\s])(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#$&*])?.{16,}[''\"]":
			return RegexType.GENERIC_PASSWORD2;
			
		//case "pass(word)?\\W*[:=,]\\W*.+$":
		case "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$":
			return RegexType.GENERIC_PASSWORD3;
			
		case "//[\\\\s:]+:[\\\\s:]+@": // Password in URL
			return RegexType.PASSWORD_IN_URL;
		
		case "(?i)db_(user(name)?|pass(word)?|name)\\W*[:=,]\\W*.+$":
			return RegexType.GENERIC_DB_CREDENTIAL; 
			
		case "(Password |pass |passwd |session password |login password |password) \\s*[:=]( \\s*[^\\r\\n]+)": // Other passwords
			return RegexType.OTHER_PASSWORDS;
			
		case "(API tokens |api tokens |tokens |Tokens)\\s*[:=](\\s*[^\\r \\n][a-f0-9]{16})":	// Tokens
			return RegexType.TOKENS;
			
		case "[a|A][c|C][c|C][s|S][e|E][s|S][s|S][t|T][o|O][k|K][e|E][n|N].*[:=](.*)": // Generic Access Token
			return RegexType.GENERIC_ACCESS_TOKENS;
					
		case "(client secret |access secret |customer secret |app secret)\\s*[:=](\\s*[ˆ\\r\\n]+)": // Other secrets
			return RegexType.OTHER_SECRETS;
			
		case "(xox[p|b|o|a]-[0-9]{12}-[0-9]{12}-[0-9]{12}-[a-z0-9]{32})": // Slack Token
			return RegexType.SLACK_TOKEN;
			
		case "-----BEGIN RSA PRIVATE KEY-----": // RSA private key
			return RegexType.RSA_PRIVATE_KEY;
			
		case "-----BEGIN OPENSSH PRIVATE KEY-----": // SSH (OPENSSH) private key 
			return RegexType.SSH_OPENSSH_PRIVATE_KEY;
			
		case "-----BEGIN DSA PRIVATE KEY-----": // SSH (DSA) private key
			return RegexType.SSH_DSA_PRIVATE_KEY;
			
		case "-----BEGIN EC PRIVATE KEY-----": // SSH (EC) private key
			return RegexType.SSH_EC_PRIVATE_KEY;
			
		case "-----BEGIN PGP PRIVATE KEY BLOCK-----": // PGP private key block
			return RegexType.PGP_PRIVATE_KEY_BLOCK;
			
		case "[f|F][a|A][c|C][e|E][b|B][o|O][o|O][k|K].{0,30}['\"\\s][0-9a-f]{32}['\"\\s]": // Facebook Oauth 
			return RegexType.FACEBOOK_OAUTH;
			
		case "[0-9a-zA-Z]{35,44}": // Twitter Oauth
			return RegexType.TWITTER_OAUTH;
			
		case "['\"\\s][0-9a-zA-Z]{35,40}['\"\\s]": // GitHub Oauth
			return RegexType.GITHUB_OAUTH;
			
		case "[a-zA-Z0-9-_]{24}": // Google Oauth
			return RegexType.GOOGLE_OAUTH;			
			
		case "AKIA[0-9A-Z]{16}": // AWS API Key
			return RegexType.AWS_API_KEY;
			
		case "[h|H][e|E][r|R][o|O][k|K][u|U].{0,30}[0-9A-F]{8}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{12}": // Heroku API Key
			return RegexType.HEROKU_API_KEY;
			
		case "[s|S][e|E][c|C][r|R][e|E][t|T].{0,30}['\"\\s][0-9a-zA-Z]{32,45}['\"\\s]": // Generic Secret
			return RegexType.GENERIC_SECRET;
			
		case "[a|A][p|P][i|I][_]?[k|K][e|E][y|Y].{0,30}['\"\\s][0-9a-zA-Z]{32,45}['\"\\s]": // Generic API Key
			return RegexType.GENERIC_API_KEY;
			
		case "https://hooks.slack.com/services/T[a-zA-Z0-9_]{8}/B[a-zA-Z0-9_]{8}/[a-zA-Z0-9_]{24}": // Slack Webhook
			return RegexType.SLACK_WEBHOOK;
			
		case "SK[a-z0-9]{32}": // Twilio API Key
			return RegexType.TWILIO_API_Key;
			
		case "\\d{4}-[01]\\d-[0-3]\\d":
			return RegexType.DATE;
			
		case "[a-zA-Z]{3,10}://[^/\\s:@]{3,20}:[^/\\s:@]{3,20}@.{1,100}[\"'\\s]":
			return RegexType.PASSWORD_IN_URL; 
			
		case "(?=.*[a-zA-Z])(?=.*[\\d~!@#$%^&*()_+{}\\[\\]?<>|]).{6,50}":
			return RegexType.GENERIC_PASSWORD1;
			
		//case "^((25[0-5]|(2[0-4]|1[0-9]|[1-9]|)[0-9])(\\.(?!$)|$)){4}$":
		case "([01]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])\\.([01]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])\\.([01]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])\\.([01]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])":
			return RegexType.IP;
		
		case "/^[a-f0-9]{32}$/i"://md5_hash
			return RegexType.MD5_HASH;
			
		default:
			return RegexType.none;
			
	}
		
}

}
