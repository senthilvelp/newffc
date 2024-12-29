package com.pz.ffc.newffc.function;

import java.util.function.BiFunction;

public class CounterFunction implements BiFunction<String, Integer, String>{
	
	private final int length = 4;

	@Override
	public String apply(String t, Integer u) {
		
		if ( t == null) {
			throw new NullPointerException("The input value is null");
		}
		final int startPosition = u.intValue();
		final String strCounter = t.substring(startPosition, startPosition + length);
		return strCounter;
	}

}
