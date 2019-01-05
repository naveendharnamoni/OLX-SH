package com.ts.olx.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtility {
	
	public static Date toDate(String dateString){
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
