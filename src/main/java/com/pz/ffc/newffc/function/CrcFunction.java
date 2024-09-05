package com.pz.ffc.newffc.function;

import java.util.function.Function;

public class CrcFunction implements Function<String, String>{
	
	private final int crcStartPosition = 67;
	private final int crcLength = 2;

	@Override
	public String apply(String t) {
		
		if ( t == null) {
			throw new NullPointerException("The input value is null");
		}
		
		final String crc = t.substring(crcStartPosition, crcLength);
		
		return crc;
	}

}
