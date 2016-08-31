package com.clz.finder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.clz.finder.utils.FileUtils;

public class ResourceFinder {
	
	//string
	public static Pattern patternString;
	public static Pattern patternXmlString;
	public static Pattern patternStringDef;
	
	//color
	public static Pattern patternColor;
	public static Pattern patternXmlColor;
	public static Pattern patternColorDef;
	
	//dimen
	public static Pattern patternDimen;
	public static Pattern patternXmlDimen;
	public static Pattern patternDimenDef;
	
	//drawable
	public static Pattern patternDrawable;
	public static Pattern patternXmlDrawable;
	
	public static List<String> findedStringReses = new ArrayList<String>();//代码中找到的string res
	public static List<String> findedColorReses = new ArrayList<String>();//代码中找到的color res
	public static List<String> findedDimenReses = new ArrayList<String>();//代码中找到的dimen res
	public static List<String> findedDrawableReses = new ArrayList<String>();//代码中找到的dimen res
	
	public static List<ResDef> stringDefReses = new ArrayList<ResDef>();//定义的string res
	public static List<ResDef> colorDefReses = new ArrayList<ResDef>();//定义的color res
	public static List<ResDef> dimenDefReses = new ArrayList<ResDef>();//定义的dimen res
	public static List<ResDef> drawableDefReses = new ArrayList<ResDef>();//定义的drawable res
	
	static {
		String str = "(R.string.([a-z|A-z][a-z|A-Z|_|\\d]+))";
		patternString = Pattern.compile(str);
		
		str = "(@string/([a-z|A-z][a-z|A-Z|_|\\d]+))";
		patternXmlString = Pattern.compile(str);
		
		String stringDefPattern = "<string[\\s]+name[\\s]*=[\\s]*\"([a-z|A-z][a-z|A-Z|_|\\d]+)\">(.+)</string>";
		patternStringDef = Pattern.compile(stringDefPattern);
		
		
		str = "(R.color.([a-z|A-z][a-z|A-Z|_|\\d]+))";
		patternColor = Pattern.compile(str);
		
		str = "(@color/([a-z|A-z][a-z|A-Z|_|\\d]+))";
		patternXmlColor = Pattern.compile(str);
		
		str = "<color[\\s]+name[\\s]*=[\\s]*\"([a-z|A-z][a-z|A-Z|_|\\d]+)\">(.+)</color>";
		patternColorDef = Pattern.compile(str);
		
		
		str = "(R.dimen.([a-z|A-z][a-z|A-Z|_|\\d]+))";
		patternDimen = Pattern.compile(str);
		
		str = "(@dimen/([a-z|A-z][a-z|A-Z|_|\\d]+))";
		patternXmlDimen = Pattern.compile(str);
		
		str = "<dimen[\\s]+name[\\s]*=[\\s]*\"([a-z|A-z][a-z|A-Z|_|\\d]+)\">(.+)</dimen>";
		patternDimenDef = Pattern.compile(str);
		
		str = "(R.drawable.([a-z|A-z][a-z|A-Z|_|\\d]+))";
		patternDrawable = Pattern.compile(str);
		
		str = "(@drawable/([a-z|A-z][a-z|A-Z|_|\\d]+))";
		patternXmlDrawable = Pattern.compile(str);
		
//		str = "<dimen[\\s]+name[\\s]*=[\\s]*\"([a-z|A-z][a-z|A-Z|_|\\d]+)\">(.+)</dimen>";
//		patternDimenDef = Pattern.compile(str);
		
//		test();
	}
	
	public static final void main(String[] args) {
		
		long startTime = System.currentTimeMillis();
//		String path[] = new String[]{"src"};
//		String project = "E:\\work\\SixGod\\demo\\Host";
		
		String project = "E:\\work\\QQProj\\Trunk__\\QQLite";
		String path[] = new String[]{"src", "qbiz", "gamecenter", "qqpim", "qqdingdong", "troop_member_card_plugin", "qqcomic",
				"qqhuangye", "qsmartdevice", "qqdataline", "qwallet", "qlink", "qqfav", "qqreader", "cooperation", "wtlogin", 
				"qav", "common", "qzone", "troop_plugin", "devicesdk", "opensdk", "dataline", "qqhotspot", "group_video_plugin",
				"qqindividuality", "qqreadinjoy", "qqsecmsg", "troop_manage_plugin", "weiyun", "fts"};
		
		System.out.println("================find Res def start ===================");
		findDef(project + File.separator + "res");
		StringBuffer stringDef = new StringBuffer();
		for (ResDef def : stringDefReses) {
			stringDef.append(def.toString()).append("\n");
		}
		FileUtils.write(new File(Constants.resStringDefLog), stringDef.toString());
		
		StringBuffer colorDef = new StringBuffer();
		for (ResDef def : colorDefReses) {
			colorDef.append(def.toString()).append("\n");
		}
		FileUtils.write(new File(Constants.resColorDefLog), colorDef.toString());
		
		
		StringBuffer dimenDef = new StringBuffer();
		for (ResDef def : dimenDefReses) {
			dimenDef.append(def.toString()).append("\n");
		}
		FileUtils.write(new File(Constants.resDimenDefLog), dimenDef.toString());
		
		findDrawableDefs(project + File.separator + "res");
		StringBuffer drawableDef = new StringBuffer();
		for (ResDef def : drawableDefReses) {
			drawableDef.append(def.toString()).append("\n");
		}
		FileUtils.write(new File(Constants.resDrawableDefLog), drawableDef.toString());
		
		System.out.println("================find Res def end ===================");
		
		System.out.println("================find Res id start ===================");
		//find src 引用
		for (String src : path) {
			int preCountString = findedStringReses.size();
			int preCountColor = findedColorReses.size();
			int preCountDimen = findedDimenReses.size();
			int preCountDrawable = findedDrawableReses.size();
			System.out.println("start find " + src);
			findSrc(project + File.separator + src);
			System.out.println("founded String: " + (findedStringReses.size() - preCountString));
			System.out.println("founded Color: " + (findedColorReses.size() - preCountColor));
			System.out.println("founded Dimen: " + (findedDimenReses.size() - preCountDimen));
			System.out.println("founded Drawable: " + (findedDrawableReses.size() - preCountDrawable));
		}
		
		//find res 引用
		System.out.println("start find res");
		int preCountString = findedStringReses.size();
		int preCountColor = findedColorReses.size();
		int preCountDimen = findedDimenReses.size();
		int preCountDrawable = findedDrawableReses.size();
		findRes(project + File.separator + "res");
		System.out.println("founded String: " + (findedStringReses.size() - preCountString));
		System.out.println("founded Color: " + (findedColorReses.size() - preCountColor));
		System.out.println("founded Dimen: " + (findedDimenReses.size() - preCountDimen));
		System.out.println("founded Drawable: " + (findedDrawableReses.size() - preCountDrawable));
		
		//find Manifest中的引用
		System.out.println("start find manifest");
		preCountString = findedStringReses.size();
		preCountColor = findedColorReses.size();
		preCountDimen = findedDimenReses.size();
		preCountDrawable = findedDrawableReses.size();
		findResId(new File(project + File.separator + "AndroidManifest.xml"), patternXmlString, findedStringReses);
		findResId(new File(project + File.separator + "AndroidManifest.xml"), patternXmlColor, findedColorReses);
		findResId(new File(project + File.separator + "AndroidManifest.xml"), patternXmlDimen, findedDimenReses);
		findResId(new File(project + File.separator + "AndroidManifest.xml"), patternXmlDrawable, findedDrawableReses);
		System.out.println("founded String: " + (findedStringReses.size() - preCountString));
		System.out.println("founded Color: " + (findedColorReses.size() - preCountColor));
		System.out.println("founded Dimen: " + (findedDimenReses.size() - preCountDimen));
		System.out.println("founded Drawable: " + (findedDrawableReses.size() - preCountDrawable));
		
		System.out.println("================find Res id end ===================");
		
		System.out.println("================find Res not used ===================");
		StringBuffer notUsedString = new StringBuffer();
		for (ResDef def : stringDefReses) {
			if (!findedStringReses.contains(def.name)) {
				notUsedString.append(def.toString()).append("\n");
			}
		}
		FileUtils.write(new File(Constants.resStringNotUsedLog), notUsedString.toString());
		
		StringBuffer notUsedColor = new StringBuffer();
		for (ResDef def : colorDefReses) {
			if (!findedColorReses.contains(def.name)) {
				notUsedColor.append(def.toString()).append("\n");
			}
		}
		FileUtils.write(new File(Constants.resColorNotUsedLog), notUsedColor.toString());
		
		StringBuffer notUsedDimen = new StringBuffer();
		for (ResDef def : dimenDefReses) {
			if (!findedDimenReses.contains(def.name)) {
				notUsedDimen.append(def.toString()).append("\n");
			}
		}
		FileUtils.write(new File(Constants.resDimenNotUsedLog), notUsedDimen.toString());
		
		StringBuffer notUsedDrawable = new StringBuffer();
		for (ResDef def : drawableDefReses) {
			if (!findedDrawableReses.contains(def.name)) {
				notUsedDrawable.append(def.toString()).append("\n");
			}
		}
		FileUtils.write(new File(Constants.resDrawableNotUsedLog), notUsedDrawable.toString());
		
		System.out.println("================find Res not used end===================");
		
		long dTime = System.currentTimeMillis() - startTime;
		
		System.out.println("success! time = " + (dTime / 1000 ) + "s");
		
	}
	
	public static final void findDef(String res) {
		File resRoot = new File(res);
		String[] resDirs = resRoot.list();
		File resDir;
		File xmlFile;
		for (String resDirStr : resDirs) {
			if (!resDirStr.startsWith("value")) {
				continue;
			}
			resDir = new File(resRoot + File.separator + resDirStr);
			String[] fileList = resDir.list();
			if (fileList == null) continue;
			for (String fileStr : fileList) {
				xmlFile = new File(resDir.getAbsolutePath() + File.separator + fileStr);
//				System.out.println(xmlFile);
				findResDef(xmlFile);
			}
		}
	}
	
	public static void findDrawableDefs(String res) {
		File resRoot = new File(res);
		String[] resDirs = resRoot.list();
		File drawableDir;
		for (String resDirStr : resDirs) {
			if (resDirStr.startsWith("drawable")) {
				drawableDir = new File(resRoot + File.separator + resDirStr);
				String[] drawables = drawableDir.list();
				if (drawables != null) {
					for (String drawable : drawables) {
						try {
							ResDef def = new ResDef();
							def.path = drawableDir + File.separator + drawable;
							int index = drawable.indexOf(".");
							def.name = drawable.substring(0, index);
							def.type = "drawable";
							drawableDefReses.add(def);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	/**
	 * 从java代码中找R.XXX.XXX
	 * @param src
	 */
	public static final void findSrc(String src) {
		File root = new File(src);
		if (!root.isDirectory()) {
			return;
		}
		String[] subDirs = root.list();
		if (subDirs.length > 0) {
			File subDir = null;
			for (String sub : subDirs) {
				subDir = new File(src + File.separator + sub);
				if (subDir.isFile()) {
					findResId(subDir, patternString, findedStringReses);
					findResId(subDir, patternColor, findedColorReses);
					findResId(subDir, patternDimen, findedDimenReses);
					findResId(subDir, patternDrawable, findedDrawableReses);
				} else {
					findSrc(subDir.getAbsolutePath());
				}
			}
		}
	}
	
	public static final void findRes(String res) {
		File resRoot = new File(res);
		String[] resDirs = resRoot.list();
		File resDir;
		File xmlFile;
		for (String resDirStr : resDirs) {
//			if (resDirStr.startsWith("value")) {
//				continue;
//			}
			resDir = new File(resRoot + File.separator + resDirStr);
			String[] fileList = resDir.list();
			if (fileList == null) continue;
			for (String fileStr : fileList) {
				xmlFile = new File(resDir.getAbsolutePath() + File.separator + fileStr);
				if (xmlFile.getAbsolutePath().endsWith(".xml")) {
					findResId(xmlFile, patternXmlString, findedStringReses);
					findResId(xmlFile, patternXmlColor, findedColorReses);
					findResId(xmlFile, patternXmlDimen, findedDimenReses);
					findResId(xmlFile, patternXmlDrawable, findedDrawableReses);
				}
			}
		}
	}
	
	public static final void findResId(File file, Pattern p, List<String> foundList) {
		FileReader in = null;
		BufferedReader bufIn = null;
		boolean isPrint = false;
//		if (file.getAbsolutePath().endsWith("qq_yellowpage_home_lifeservice_layout.xml")) {
//			isPrint = true;
//		}
		if (isPrint) {
			System.out.println("filePath = " + file.getAbsolutePath());
		}
		try {
			in = new FileReader(file);
			bufIn = new BufferedReader(in);
			String line = null;
			Matcher m = null;
			while ((line = bufIn.readLine()) != null) {
				m = p.matcher(line);
				while(m.find()) {
					if (isPrint) {
						System.out.println(line);
					}
					if (m.groupCount() == 2) {
						String result = m.group(2);
						if (!foundList.contains(result)) {
							foundList.add(result);
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} 
			if (bufIn != null) {
				try {
					bufIn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public static final void findResDef(File file) {
		FileReader in = null;
		BufferedReader bufIn = null;
		try {
			in = new FileReader(file);
			bufIn = new BufferedReader(in);
			String line = null;
			Matcher m = null;
			int lineCount = 0;
			while ((line = bufIn.readLine()) != null) {
				lineCount++;
				m = patternStringDef.matcher(line);
				boolean find = false;
				while(m.find()) {
					find = true;
					if (m.groupCount() == 2) {
						String result = m.group(1);
						ResDef def = new ResDef();
						def.path = file.getAbsolutePath();
						def.name = result;
						def.type = "string";
						def.lineCount = lineCount;
						if (!stringDefReses.contains(def)) {
							stringDefReses.add(def);
						}
					}
				}
				if (!find) {//String没找到，匹配一下color看看
					m = patternColorDef.matcher(line);
					while(m.find()) {
						find = true;
						if (m.groupCount() == 2) {
							String result = m.group(1);
							ResDef def = new ResDef();
							def.path = file.getAbsolutePath();
							def.name = result;
							def.type = "color";
							def.lineCount = lineCount;
							if (!colorDefReses.contains(def)) {
								colorDefReses.add(def);
							}
						}
					}
				}
				if (!find) {
					m = patternDimenDef.matcher(line);
					while(m.find()) {
						find = true;
						if (m.groupCount() == 2) {
							String result = m.group(1);
							ResDef def = new ResDef();
							def.path = file.getAbsolutePath();
							def.name = result;
							def.type = "dimen";
							def.lineCount = lineCount;
							if (!dimenDefReses.contains(def)) {
								dimenDefReses.add(def);
							}
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} 
			if (bufIn != null) {
				try {
					bufIn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	/**
	 * 测试正则表达式
	 */
	public static final void test() {
		Matcher m = null;
		String str1 = "setText(R.string.cancel_1)";
		String str2 = "int r = R.string.o2k;";
		String str3 = "int r = R.string.ok ";
		String str4 = ", R.string.haHa, R.string.Te2t)";
		String xml = "android:text=\"@string/uninstall\" />";
		String def = "<string  name = \"app_name\">Host</string>";
		String def2 = "<string name=\"app_name\">Host</string>";
		m = patternString.matcher(str1);
		if (m.find()) {
			for(int i = 0; i <= m.groupCount(); i++) {
				System.out.println(m.group(i));
			}
		}
		
		m = patternString.matcher(str2);
		if (m.find()) {
			for(int i = 0; i <= m.groupCount(); i++) {
				System.out.println(m.group(i));
			}
		}
		
		m = patternString.matcher(str3);
		if (m.find() && m.groupCount() == 1) {
			for(int i = 0; i <= m.groupCount(); i++) {
				System.out.println(m.group(i));
			}
		}
		
		m = patternString.matcher(str4);
		while (m.find()) {
			for(int i = 0; i <= m.groupCount(); i++) {
				System.out.println(m.group(i));
			}
		}
		
		m = patternXmlString.matcher(xml);
		while (m.find()) {
			for(int i = 0; i <= m.groupCount(); i++) {
				System.out.println(m.group(i));
			}
		}
		
		m = patternStringDef.matcher(def2);
		while (m.find()) {
			for(int i = 0; i <= m.groupCount(); i++) {
				System.out.println(m.group(i));
			}
		}
		
		String lable = "                android:label=\"@string/weiyun_backup_title\"";
		m = patternXmlString.matcher(lable);
		while (m.find()) {
			for(int i = 0; i <= m.groupCount(); i++) {
				System.out.println(m.group(i));
			}
		}
		
	}
	
}
