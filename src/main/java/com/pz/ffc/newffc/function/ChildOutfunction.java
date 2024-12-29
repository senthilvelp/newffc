package com.pz.ffc.newffc.function;

import java.util.function.Function;

public class ChildOutfunction implements Function<String, String>{

	private final int childOutStartPosition = 47;
	
	@Override
	public String apply(String t) {
		if (t == null) {
			throw new NullPointerException("the input is null");
		}
		
		final CounterFunction counterFunction = new CounterFunction();
		final String childOutCount = counterFunction.apply(t, childOutStartPosition);
		
		return childOutCount;

	}

}
