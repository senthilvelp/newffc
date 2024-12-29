package com.pz.ffc.newffc.function;

import java.util.function.Function;

public class TotalOutfunction implements Function<String, String>{

	private final int totalOutStartPosition = 31;
	
	@Override
	public String apply(String t) {
		if (t == null) {
			throw new NullPointerException("the input is null");
		}
		
		final CounterFunction counterFunction = new CounterFunction();
		final String outcount = counterFunction.apply(t, totalOutStartPosition);
		
		return outcount;

	}

}
