package com.pz.ffc.newffc.function;

import java.util.function.BiFunction;

public class TotalOutfunction implements BiFunction<String, Integer, Integer>{

	private final int totalOutStartPosition = 31;
	
	@Override
	public Integer apply(String t, Integer u) {
		if (t == null) {
			throw new NullPointerException("the input is null");
		}
		
		final CounterFunction counterFunction = new CounterFunction();
		final Integer outcount = counterFunction.apply(t, totalOutStartPosition);
		
		return outcount;

	}

}
