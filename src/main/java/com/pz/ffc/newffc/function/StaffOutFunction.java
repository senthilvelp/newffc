package com.pz.ffc.newffc.function;

import java.util.function.Function;

public class StaffOutFunction implements Function<String, String>{

	private final int staffOutStartPosition = 71;
	
	@Override
	public String apply(String t) {
		if (t == null) {
			throw new NullPointerException("the input is null");
		}
		
		final CounterFunction counterFunction = new CounterFunction();
		final String staffOutCount = counterFunction.apply(t, staffOutStartPosition);
		
		return staffOutCount;

	}

}
