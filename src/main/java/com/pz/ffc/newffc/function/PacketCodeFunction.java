package com.pz.ffc.newffc.function;

import java.util.function.Function;

public class PacketCodeFunction implements Function<String, String> {
	
	private final int packetCodePosition = 13;
	private final int packetCodeLength = 2;

	@Override
	public String apply(String t) {
		if ( t == null) {
			throw new NullPointerException("The input value is null");
		}
		
		final String packetCode = t.substring(packetCodePosition, packetCodePosition + packetCodeLength);
		return packetCode;
	}

}
