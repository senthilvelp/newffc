package com.pz.ffc.newffc;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pz.ffc.newffc.model.Company;
import com.pz.ffc.newffc.model.Device;
import com.pz.ffc.newffc.model.GroupConfig;

public class Globals {
	public static String FILE_PATH;
	public static Map<String, Device> deviceMap = new LinkedHashMap<>();
	public static Map<Long, GroupConfig> groupConfigMap = new LinkedHashMap<>();
	public static Map<String, Company> companyMap = new LinkedHashMap<>();
	public static int agingDays;
	public static int schedulerFrequency;
	public static int deviceOffMinutes;
	public static int bataAgingDays;
}
