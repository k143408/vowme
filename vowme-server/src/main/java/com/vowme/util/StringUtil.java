package com.vowme.util;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class StringUtil {
	
	public static boolean isEmptyOrNull(String comments) {
		return !(comments == null || comments.length() == 0);
	}
	
	
	public static Map<String,Long> toMap(String parameters) {
		
		return Arrays.asList(parameters.split(",")).stream()
				.map(elem -> elem.split(":"))
		        .filter(elem -> elem.length==2)
		        .collect(Collectors.toMap(e -> e[0], e -> Long.parseLong(e[1])));
	}
}
