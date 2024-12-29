package com.pz.ffc.newffc.function;

import java.util.function.Function;

public class StaffInFunction implements Function<String, String>{

	private final int staffInStartPosition = 67;
	
	@Override
	public String apply(String t) {
		if (t == null) {
			throw new NullPointerException("the input is null");
		}
		
		final CounterFunction counterFunction = new CounterFunction();
		final String staffIncount = counterFunction.apply(t, staffInStartPosition);
		
		return staffIncount;

	}

}
