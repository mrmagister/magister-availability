package standalone.utils;

import standalone.model.HeuristicType;

public class HeuristicUtils {

	private HeuristicUtils() {}
	
	/**
	 * 	This method gets a string representation of a regex and returns the enum regexType
	 * @param regex
	 * @return RegexType
	 */
	public static HeuristicType getHeuristicType(String heuristic) {
		
		switch (heuristic) {
		
			case "api":
				return HeuristicType.api;
			case "key":
				return HeuristicType.key;
			case "username":
				return HeuristicType.username;
			case "user":
				return HeuristicType.user;
			case "github":
				return HeuristicType.github;
			case "json":
				return HeuristicType.json;
			case "uname":
				return HeuristicType.uname;
			case "pw":
				return HeuristicType.pw;
			case "password":
				return HeuristicType.password;
			case "pass":
				return HeuristicType.pass;
			case "email":
				return HeuristicType.email;
			case "metadata":
				return HeuristicType.metadata;
			case "test":
				return HeuristicType.test;
			case "mail":
				return HeuristicType.mail;
			case "credentials":
				return HeuristicType.credentials;
			case "credential":
				return HeuristicType.credential;
			case "login":
				return HeuristicType.login;
			case "token":
				return HeuristicType.token;
			case "secret":
				return HeuristicType.secret;
			case "annotation":
				return HeuristicType.annotation;
			case "herobrine":
				return HeuristicType.herobrine;
			case "aws":
				return HeuristicType.aws;
			case "com.":
				return HeuristicType.com_package;
			case ".com":
				return HeuristicType.dot_com;
			case "/home/":
				return HeuristicType.home_folder;
			default:
				return HeuristicType.none;
			
		}
	}
}
