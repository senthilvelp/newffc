package com.pz.ffc.newffc.function;

import java.util.function.BiFunction;

public class ChildOutfunction implements BiFunction<String, Integer, Integer>{

	private final int childOutStartPosition = 47;
	
	@Override
	public Integer apply(String t, Integer u) {
		if (t == null) {
			throw new NullPointerException("the input is null");
		}
		
		final CounterFunction counterFunction = new CounterFunction();
		final Integer childOutCount = counterFunction.apply(t, childOutStartPosition);
		
		return childOutCount;

	}

}
