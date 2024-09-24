package com.pz.ffc.newffc.function;

import java.util.function.BiFunction;

public class AdultInFunction implements BiFunction<String, Integer, Integer>{

	private final int audultInStartPosition = 35;
	
	@Override
	public Integer apply(String t, Integer u) {
		if (t == null) {
			throw new NullPointerException("the input is null");
		}
		
		final CounterFunction counterFunction = new CounterFunction();
		final Integer adultIncount = counterFunction.apply(t, audultInStartPosition);
		
		return adultIncount;

	}

}
