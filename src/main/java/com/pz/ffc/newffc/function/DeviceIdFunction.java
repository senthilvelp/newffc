package com.pz.ffc.newffc.function;

import java.util.function.Function;

import com.pz.ffc.newffc.exception.InvalidException;

public class DeviceIdFunction implements Function<String, String> {

	private final int deviceLength = 10;
	private final int deviceStartPosition = 3;
	@Override
	public String apply(String t) {
		if ( t == null) {
			throw new NullPointerException("The input value is null");
		}
		if (t.length() < deviceLength) {
			throw new InvalidException("the length of the input is less than the device length");
		}
		
		final String deviceid = t.substring(deviceStartPosition, deviceLength);
		return deviceid;
	}

}
