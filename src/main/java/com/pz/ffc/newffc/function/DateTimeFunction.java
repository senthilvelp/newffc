package com.pz.ffc.newffc.function;

import java.time.LocalDateTime;
import java.util.function.Function;

public class DateTimeFunction implements Function<String, LocalDateTime>{

	private final int startHourPosition = 17;
	private final int startHourLength = 2;
	private final int startMinutePosition = 19;
	private final int startMinuteLength = 2;
	private final int startDayPosition = 21;
	private final int startDayLength = 2;
	private final int startMonthPosition = 23;
	private final int startMonthLength = 2;
	private final int startYearPosition = 25;
	private final int startYearLength = 2;
	
	@Override
	public LocalDateTime apply(String t) {
		
		if ( t == null) {
			throw new NullPointerException("The input value is null");
		}
		
		final String strHour = t.substring(startHourPosition, startHourLength);
		final String strMinute = t.substring(startMinutePosition, startMinuteLength);
		final String strDay = t.substring(startDayPosition, startDayLength);
		final String strMonth = t.substring(startMonthPosition, startMonthLength);
		final String strYear = t.substring(startYearPosition, startYearLength);
		
		final int intHour = Integer.valueOf(strHour).intValue();
		final int intMinute = Integer.valueOf(strMinute).intValue();
		final int intDay = Integer.valueOf(strDay).intValue();
		final int intMonth = Integer.valueOf(strMonth).intValue();
		final int intYear = Integer.valueOf(strYear).intValue();
		
		final LocalDateTime ldt = LocalDateTime.of(intYear, intMonth, intDay, intHour, intMinute);
		
		return ldt;
	}
	
}
