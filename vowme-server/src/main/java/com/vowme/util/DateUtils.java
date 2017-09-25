package com.vowme.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The Class DateUtils.
 */
public class DateUtils {
	
	/** The Constant logger. */
	static final Logger logger = LoggerFactory.getLogger(DateUtils.class);
	
	/**
	 * Convert days into seconds.
	 *
	 * @param days the days
	 * @return the long
	 */
	public static long convertDaysIntoSeconds(int days){
		long seconds = (days * 24 * 60 * 60);
		return seconds;
	}
	
	/**
	 * Convert millis into seconds.
	 *
	 * @param millis the millis
	 * @return the long
	 */
	public static long convertMillisIntoSeconds(long millis){
		return (millis / 1000L);
	}

	/**
	 * Gets the current time.
	 *
	 * @return the current time
	 */
	public static Long getCurrentTime(){
		return (System.currentTimeMillis() / 1000L);
	}
	
	/**
	 * Convert date into seconds.
	 *
	 * @param dateParam the date param
	 * @return the long
	 */
	public static Long convertDateIntoSeconds(String dateParam){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try{
			date = sdf.parse(dateParam);	
		}catch(ParseException pe){
			logger.error("Error :"+pe);
		}
		return convertMillisIntoSeconds(date.getTime());
	}
	
	public static String convertLongIntoString(Long dateParam) {
		return (dateParam != null && dateParam.longValue() != 0 )? formatFromDate(new Date(dateParam*1000)) : ""; 
	}

	private static String formatFromDate(Date date) {
		return DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(date);
	}

}
