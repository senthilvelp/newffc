package com.pz.ffc.newffc.function;

import java.util.function.Function;

public class TotalInFunction implements Function<String, String>{

	private final int totalInStartPosition = 27;
	
	
	@Override
	public String apply(String t) {
		if ( t == null) {
			throw new NullPointerException("The input value is null");
		}
		
		final CounterFunction counterFunction = new CounterFunction();
		final String incount = counterFunction.apply(t, totalInStartPosition);
		
		return incount;
	}

}
