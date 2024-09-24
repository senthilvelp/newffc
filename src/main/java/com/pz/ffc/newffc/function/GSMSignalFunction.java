package com.pz.ffc.newffc.function;

import java.util.function.Function;

public class GSMSignalFunction implements Function<String, Integer>{
	private final int gsmSignalPosition = 15;
	private final int gsmSignalLength = 2;

	@Override
	public Integer apply(String t) {
		if ( t == null) {
			throw new NullPointerException("The input value is null");
		}
		
		final String gsmSignal = t.substring(gsmSignalPosition, gsmSignalLength);
		return Integer.valueOf(gsmSignal);
	}
}
