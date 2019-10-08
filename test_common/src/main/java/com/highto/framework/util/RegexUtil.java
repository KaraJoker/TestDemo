package com.highto.framework.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

	public static Map<String, Pattern> patternCache = new HashMap<String, Pattern>();

	public static String subString(String sourceString, String regex) {
		Pattern pattern = getPattern(regex);
		Matcher matcher = pattern.matcher(sourceString);
		boolean found = matcher.find();
		if (found) {
			return matcher.group(1);
		} else {
			return null;
		}
	}

	public static boolean hasSubString(String sourceString, String regex) {
		Pattern pattern = getPattern(regex);
		Matcher matcher = pattern.matcher(sourceString);
		return matcher.find();
	}

	public static boolean isMatch(String sourceString, String regex) {
		Pattern pattern = getPattern(regex);
		Matcher matcher = pattern.matcher(sourceString);
		return matcher.matches();
	}

	private static Pattern getPattern(String regex) {
		Pattern pattern = patternCache.get(regex);
		if (pattern == null) {
			pattern = Pattern.compile(regex, Pattern.DOTALL);
			patternCache.put(regex, pattern);
		}
		return pattern;
	}

	public static String[] subStrings(String sourceString, String regex, int groupCount) {
		Pattern pattern = getPattern(regex);
		Matcher matcher = pattern.matcher(sourceString);
		matcher.find();
		String[] subStrings = new String[groupCount];
		for (int i = 0; i < groupCount; i++) {
			subStrings[i] = matcher.group(i + 1);
		}
		return subStrings;
	}

	public static String[] mutiMatchSubString(String sourceString, String regex, int matchCount) {
		Pattern pattern = getPattern(regex);
		Matcher matcher = pattern.matcher(sourceString);
		String[] mutiSubString = new String[matchCount];
		for (int i = 0; i < matchCount; i++) {
			matcher.find();
			mutiSubString[i] = matcher.group(1);
		}
		return mutiSubString;
	}

	public static String[] bestEffortMutiMatchSubString(String sourceString, String regex) {
		Pattern pattern = getPattern(regex);
		Matcher matcher = pattern.matcher(sourceString);
		List<String> strList = new ArrayList<String>();
		while (matcher.find()) {
			strList.add(matcher.group(1));
		}
		String[] result = new String[strList.size()];
		for (int i = 0; i < strList.size(); i++) {
			result[i] = strList.get(i);
		}
		return result;
	}

	public static String[][] mutiMatchSubStrings(String sourceString, String regex, int groupCount, int matchCount) {
		Pattern pattern = getPattern(regex);
		Matcher matcher = pattern.matcher(sourceString);
		String[][] mutiMatchSubStrings = new String[matchCount][groupCount];
		for (int i = 0; i < matchCount; i++) {
			matcher.find();
			String[] subStrings = new String[groupCount];
			for (int j = 0; j < groupCount; j++) {
				subStrings[j] = matcher.group(j + 1);
			}
			mutiMatchSubStrings[i] = subStrings;
		}
		return mutiMatchSubStrings;
	}

	public static String[][] bestEffortMutiMatchSubStrings(String sourceString, int groupCount, String regex) {
		Pattern pattern = getPattern(regex);
		Matcher matcher = pattern.matcher(sourceString);
		List<String[]> mutiMatchSubStrings = new ArrayList<String[]>();
		while (matcher.find()) {
			String[] subStrings = new String[groupCount];
			for (int j = 0; j < groupCount; j++) {
				subStrings[j] = matcher.group(j + 1);
			}
			mutiMatchSubStrings.add(subStrings);
		}
		String[][] result = new String[mutiMatchSubStrings.size()][groupCount];
		for (int i = 0; i < mutiMatchSubStrings.size(); i++) {
			result[i] = mutiMatchSubStrings.get(i);
		}
		return result;
	}
}
