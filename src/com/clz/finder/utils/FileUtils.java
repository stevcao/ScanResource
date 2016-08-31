package com.clz.finder.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtils {
	/**
	 * 读取文本文件中的内容
	 */
	public static String readFile(InputStream is){
		try {
			InputStreamReader inputReader = new InputStreamReader(is);
			BufferedReader bufReader = new BufferedReader(inputReader);
			String line="";
			String Result="";
			while((line = bufReader.readLine()) != null)
				Result += line;
			return Result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String readFile(File file) {
		FileInputStream is = null;
		try {
			is = new FileInputStream(file);
			return readFile(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}
	
	public static void write(File file , String str) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(file, false);
			writer.write(str);
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public static void append(File file , String str) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(file, false);
			writer.append("\r\n");
			writer.append(str);
			writer.append("\r\n");
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}
