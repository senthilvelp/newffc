package com.pz.ffc.newffc.function;

import java.util.function.Function;

public class FemaleInFunction implements Function<String, String>{

	private final int femaleOutStartPosition = 63;
	
	@Override
	public String apply(String t) {
		if (t == null) {
			throw new NullPointerException("the input is null");
		}
		
		final CounterFunction counterFunction = new CounterFunction();
		final String adultIncount = counterFunction.apply(t, femaleOutStartPosition);
		
		return adultIncount;

	}

}
