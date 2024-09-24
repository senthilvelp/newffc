package com.pz.ffc.newffc.function;

import java.util.function.BiFunction;

public class ChildInFunction implements BiFunction<String, Integer, Integer>{

	private final int childInStartPosition = 43;
	
	@Override
	public Integer apply(String t, Integer u) {
		if (t == null) {
			throw new NullPointerException("the input is null");
		}
		
		final CounterFunction counterFunction = new CounterFunction();
		final Integer childIncount = counterFunction.apply(t, childInStartPosition);
		
		return childIncount;

	}

}
