package com.clz.finder;

public class ResDef {
	public static final String TYPE_STRING = "string";
	public static final String TYPE_COLOR = "color";
	
	public String path;
	public String name;
	public String type;
	int lineCount = 0;
	@Override
	public String toString() {
		return "type = " + type + "path = " + path + " lineCount = " + lineCount +" name = " + name;
	}
	@Override
	public boolean equals(Object arg0) {
		if (!(arg0 instanceof ResDef)) {
			return false;
		}
		
		ResDef def = (ResDef)arg0;
		
		return def.path.equals(path) && def.name.equals(name);
	}
}
