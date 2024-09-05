package com.pz.ffc.newffc.function;

import java.util.function.BiFunction;

public class AdultOutFunction implements BiFunction<String, Integer, Integer>{

	private final int adultOutStartPosition = 39;
	
	@Override
	public Integer apply(String t, Integer u) {
		if (t == null) {
			throw new NullPointerException("the input is null");
		}
		
		final CounterFunction counterFunction = new CounterFunction();
		final Integer adultOutCount = counterFunction.apply(t, adultOutStartPosition);
		
		return adultOutCount;

	}

}
