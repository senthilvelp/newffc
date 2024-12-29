package com.pz.ffc.newffc.function;

import java.util.function.BiFunction;
import java.util.function.Function;

public class AdultOutFunction implements Function<String, String>{

	private final int adultOutStartPosition = 39;
	
	@Override
	public String apply(String t) {
		if (t == null) {
			throw new NullPointerException("the input is null");
		}
		
		final CounterFunction counterFunction = new CounterFunction();
		final String adultOutCount = counterFunction.apply(t, adultOutStartPosition);
		
		return adultOutCount;

	}

}
