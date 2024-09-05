package com.pz.ffc.newffc.function;

import java.util.function.BiFunction;

public class StaffOutFunction implements BiFunction<String, Integer, Integer>{

	private final int staffOutStartPosition = 63;
	
	@Override
	public Integer apply(String t, Integer u) {
		if (t == null) {
			throw new NullPointerException("the input is null");
		}
		
		final CounterFunction counterFunction = new CounterFunction();
		final Integer staffOutCount = counterFunction.apply(t, staffOutStartPosition);
		
		return staffOutCount;

	}

}
