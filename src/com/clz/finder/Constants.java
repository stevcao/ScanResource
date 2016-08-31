package com.clz.finder;

import java.io.File;

public class Constants {
	
	public static final String logRoot = "E:\\finder" + File.separator;
	
	public static final String resStringDefLog = logRoot + "def_string.txt";
	public static final String resColorDefLog = logRoot + "def_color.txt";
	public static final String resDimenDefLog = logRoot + "def_dimen.txt";
	public static final String resDrawableDefLog = logRoot + "def_drawable.txt";
	
	public static final String resStringFoundLog = logRoot + "found_string.txt";
	public static final String resColorFoundLog = logRoot + "found_color.txt";
	public static final String resDimenFoundLog = logRoot + "found_dimen.txt";
	public static final String resDrawableFoundLog = logRoot + "found_drawable.txt";
	
	public static final String resStringNotUsedLog = logRoot + "notUsed_string.txt";
	public static final String resColorNotUsedLog = logRoot + "notUsed_color.txt";
	public static final String resDimenNotUsedLog = logRoot + "notUsed_dimen.txt";
	public static final String resDrawableNotUsedLog = logRoot + "notUsed_drawable.txt";
	
	
	static {
		File dir = new File(logRoot);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}
	
}
