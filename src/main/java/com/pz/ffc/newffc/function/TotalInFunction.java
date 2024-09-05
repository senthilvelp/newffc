package com.pz.ffc.newffc.function;

import java.util.function.Function;

public class TotalInFunction implements Function<String, Integer>{

	private final int totalInStartPosition = 27;
	
	
	@Override
	public Integer apply(String t) {
		if ( t == null) {
			throw new NullPointerException("The input value is null");
		}
		
		final CounterFunction counterFunction = new CounterFunction();
		final Integer incount = counterFunction.apply(t, totalInStartPosition);
		
		return incount;
	}

}
