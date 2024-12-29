package com.pz.ffc.newffc.function;

import java.util.function.Function;

public class GroupOutFunction implements Function<String, String>{

	private final int groupOutStartPosition = 79;
	
	@Override
	public String apply(String t) {
		if (t == null) {
			throw new NullPointerException("the input is null");
		}
		
		final CounterFunction counterFunction = new CounterFunction();
		final String groupOutCount = counterFunction.apply(t, groupOutStartPosition);
		
		return groupOutCount;

	}

}
