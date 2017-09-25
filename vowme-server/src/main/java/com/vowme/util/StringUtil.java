package com.vowme.util;

public class StringUtil {
	
	public static boolean isEmptyOrNull(String comments) {
		return !(comments == null || comments.length() == 0);
	}
	
}
