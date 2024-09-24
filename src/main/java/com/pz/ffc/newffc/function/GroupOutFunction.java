package com.pz.ffc.newffc.function;

import java.util.function.BiFunction;

public class GroupOutFunction implements BiFunction<String, Integer, Integer>{

	private final int groupOutStartPosition = 55;
	
	@Override
	public Integer apply(String t, Integer u) {
		if (t == null) {
			throw new NullPointerException("the input is null");
		}
		
		final CounterFunction counterFunction = new CounterFunction();
		final Integer groupOutCount = counterFunction.apply(t, groupOutStartPosition);
		
		return groupOutCount;

	}

}
