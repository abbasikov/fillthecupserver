package com.macys.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.math.NumberUtils;

public class AppUtils {
	
	public static final String SYSTEM_USER = "system";
	
	public static String randomUuid() {
		String random = UUID.randomUUID().toString();
		return random.substring(0, 13);
	}
	
	public static boolean isValidUSPhoneNumber(String number) {
		
		if(NumberUtils.isDigits(number)) {
			if(number.length() == 10) {
				return true;
			}
		}
		
		return false;
		
	}
	
	public static boolean isPhoneValid(String phoneNumber) {
		String expression = "^\\(\\d{3}\\)\\d{3}-\\d{4}$";  
		CharSequence inputStr = phoneNumber;  
		Pattern pattern = Pattern.compile(expression);  
		Matcher matcher = pattern.matcher(inputStr);  
		if(matcher.matches()){  
			return true;
		}  
		
		return false;
	}
	
	public static String getCreateOnByTimeZone(Date createdOn, String timezone, String dateFormat){
		try{
			TimeZone tz = TimeZone.getTimeZone(timezone);
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			sdf.setTimeZone(tz);
			return sdf.format(createdOn);	
		}
		catch(Exception exc){
			return createdOn.toString();
		}
		
	}
	
	public static String getDateISO8601(Date date){
		
		try{
			SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_ISO8601);
			return sdf.format(date);			
		}
		catch(Exception exc){
			return date.toString();
		}
		
	}
	
	

	public static String getRandomResetPassword() {
		return UUID.randomUUID().toString().substring(0,8);
	}
	
	
}
