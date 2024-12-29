package com.pz.ffc.newffc.function;

import java.util.function.BiFunction;
import java.util.function.Function;

public class GroupInFunction implements Function<String, String>{

	private final int groupInStartPosition = 75;
	
	@Override
	public String apply(String t) {
		if (t == null) {
			throw new NullPointerException("the input is null");
		}
		
		final CounterFunction counterFunction = new CounterFunction();
		final String groupInCount = counterFunction.apply(t, groupInStartPosition);
		
		return groupInCount;

	}

}
