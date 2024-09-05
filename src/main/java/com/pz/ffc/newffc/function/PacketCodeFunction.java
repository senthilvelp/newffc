package com.pz.ffc.newffc.function;

import java.util.function.Function;

public class PacketCodeFunction implements Function<String, Integer> {
	
	private final int packetCodePosition = 13;
	private final int packetCodeLength = 2;

	@Override
	public Integer apply(String t) {
		if ( t == null) {
			throw new NullPointerException("The input value is null");
		}
		
		final String packetCode = t.substring(packetCodePosition, packetCodeLength);
		return Integer.valueOf(packetCode);
	}

}
