package com.pz.ffc.newffc.function;

import java.util.function.Function;

public class AdultInFunction implements Function<String, String>{

	private final int audultInStartPosition = 35;
	
	@Override
	public String apply(String t) {
		if (t == null) {
			throw new NullPointerException("the input is null");
		}
		
		final CounterFunction counterFunction = new CounterFunction();
		final String adultIncount = counterFunction.apply(t, audultInStartPosition);
		
		return adultIncount;

	}

}
