package com.pz.ffc.newffc.function;

import java.util.function.BiFunction;

public class CounterFunction implements BiFunction<String, Integer, Integer>{
	
	private final int length = 4;

	@Override
	public Integer apply(String t, Integer u) {
		
		if ( t == null) {
			throw new NullPointerException("The input value is null");
		}
		
		final String strCounter = t.substring(u.intValue(), length);
		return Integer.valueOf(strCounter);
	}

}
