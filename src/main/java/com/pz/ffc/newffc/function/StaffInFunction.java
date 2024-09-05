package com.pz.ffc.newffc.function;

import java.util.function.BiFunction;

public class StaffInFunction implements BiFunction<String, Integer, Integer>{

	private final int staffInStartPosition = 59;
	
	@Override
	public Integer apply(String t, Integer u) {
		if (t == null) {
			throw new NullPointerException("the input is null");
		}
		
		final CounterFunction counterFunction = new CounterFunction();
		final Integer staffIncount = counterFunction.apply(t, staffInStartPosition);
		
		return staffIncount;

	}

}
