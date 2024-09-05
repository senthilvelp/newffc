package com.pz.ffc.newffc.function;

import java.util.function.BiFunction;

public class GroupInFunction implements BiFunction<String, Integer, Integer>{

	private final int groupInStartPosition = 51;
	
	@Override
	public Integer apply(String t, Integer u) {
		if (t == null) {
			throw new NullPointerException("the input is null");
		}
		
		final CounterFunction counterFunction = new CounterFunction();
		final Integer groupInCount = counterFunction.apply(t, groupInStartPosition);
		
		return groupInCount;

	}

}
